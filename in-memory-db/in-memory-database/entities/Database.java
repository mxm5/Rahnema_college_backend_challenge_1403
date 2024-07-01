package entities;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Database {


    private final Map<String, Item> storage = new LinkedHashMap<>();
    private final String name;
    private final Integer maxItemsSize;
    private final EvictionPolicy evictionPolicy;


    public String getName() {
        return name;
    }

    public Integer getMaxItemsSize() {
        return maxItemsSize;
    }

    public EvictionPolicy getEvictionPolicy() {
        return evictionPolicy;
    }

    public Database(String name, Integer maxItemsSize, EvictionPolicy evictionPolicy) {
        this.name = name;
        this.maxItemsSize = maxItemsSize;
        this.evictionPolicy = evictionPolicy;
    }


    private int currentItemSize = 0;

    public boolean del(String key) {

        Item remove = storage.remove(key);
        boolean result = !Objects.isNull(remove);
        if (result) {
            new Result("deleted the key : " + key + " and value : " + remove.value);
            --currentItemSize;
        }
        return result;

    }

    public Result get(String key) {
        Item item = storage.get(key);
        if (Objects.isNull(item) || checkForIsExpired(item)) {
            return new Result("item could not be found " + '\n' + "null");
        }
        Result result = new Result("item found for given key " + '\n' + item.value);
        result.setKey(key);
        result.setValue(item);
        if (EvictionPolicy.lru.equals(evictionPolicy)) {
            updateRankInLRUGradingToMostRecentUsed(key);
        }
        return result;
    }

    private boolean checkForIsExpired(Item item) {
        if (!Objects.isNull(item.expirationDate)) {
            Date currentDate = new Date();
            boolean checkResult = item.expirationDate.before(currentDate);
            if (!checkResult) --currentItemSize;
            return checkResult;
        } else {
            return false;
        }
    }

    public Result searchPaginatedByRegex(String regex, int pageNumber, int limit) {

        Set<String> allKeys = storage.keySet();
        if (allKeys.isEmpty()) {
            Result result = new Result("no keys found");
            result.setFoundResults(new ArrayList<>());
            return result;
        }
        // there is no indexed api to direct access
        // in order to get to the page of the one hundred we should take the

        int theMaximumMatchingResultsCount = pageNumber * limit;

        int startingMatchingResultsCount = theMaximumMatchingResultsCount - limit;

        int foundResultCounter = 0;

        Pattern geivenPattern = Pattern.compile(regex);

        List<String> results = new ArrayList<>();

        for (String givenKeyString : allKeys) {
            Matcher matcher = geivenPattern.matcher(givenKeyString);
            while (matcher.find()) {
                foundResultCounter++;
                if (foundResultCounter >= startingMatchingResultsCount && foundResultCounter <= theMaximumMatchingResultsCount) {
                    Result result = get(givenKeyString);
                    results.add(result.getKey());
                }
            }
        }
        Result result = new Result("the searching operation finished" + '\n' + results.size() + " result found : " + results);
        result.setFoundResults(results);
        return result;

    }

    public Result set(String key, String value, Integer timeToLive) {
        Item correspondingItem;
        if (timeToLive == null) {
            correspondingItem = new Item(null, value);
        } else {
            Date expirationDate = calculateExpirationDate(timeToLive);
            correspondingItem = new Item(expirationDate, value);
        }

        if (checkAvailableSpot()) {
            storage.put(key, correspondingItem);
            currentItemSize++;
            if (EvictionPolicy.lru.equals(evictionPolicy)) updateRankInLRUGradingToMostRecentUsed(key);
            return new Result("successfully added the item { key : " + key + " , value : " + value + " }");
        }
        return new Result("item could not be added to the storage " + '\n' + "null");
    }

    private static Date calculateExpirationDate(Integer timeToLive) {
        Duration duration = Duration.of(timeToLive, ChronoUnit.SECONDS);
        Date currentTime = new Date();
        Instant instant = currentTime.toInstant();
        ZoneId systemDefaultZoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, systemDefaultZoneId);
        LocalDateTime localDateTimePlusTimeToLiveSeconds = localDateTime.plus(duration);
        ZonedDateTime expirationZonedDateTime = ZonedDateTime.of(localDateTimePlusTimeToLiveSeconds, systemDefaultZoneId);
        Date expirationDate = Date.from(expirationZonedDateTime.toInstant());
        return expirationDate;
    }

    private boolean checkAvailableSpot() {
        if (currentItemSize == maxItemsSize) {
            new Result("maximum size : " + getMaxItemsSize() + " reached ");
            if (!EvictionPolicy.noeviction.equals(evictionPolicy)) {
                return makeSpotByPolicyOrRejectNewMember();
            } else {
                new Result("no eviction policy remove available elements before adding new spot");
                return false;
            }
        }
        return true;
    }

    private boolean makeSpotByPolicyOrRejectNewMember() {
        new Result("trying to create new spot");
        if (EvictionPolicy.random.equals(evictionPolicy)) {
            new Result("eviction policy is random! trying to remove a key randomly ");
            String randomkeyCandidateToBeEvicted = getARandomKey();
            new Result("selected key candidate was: " + randomkeyCandidateToBeEvicted);
            if (randomkeyCandidateToBeEvicted != null) {
                return del(randomkeyCandidateToBeEvicted);
            }
            return false;
        } else if (EvictionPolicy.lru.equals(evictionPolicy)) {
            new Result("eviction policy is least recently used! trying to find most lowest rank in LRU system");
            String lruKeyCandidateToBeEvicted = findLeastResentUsedKeyInLRUGrading();
            new Result("selected key candidate was: " + lruKeyCandidateToBeEvicted);
            if (lruKeyCandidateToBeEvicted != null) {
                return del(lruKeyCandidateToBeEvicted);
            }
            return false;
        } else {
            return false;
        }

    }


    // the simplest lru ranking system is the doubly linked list
    // all the items in the tail are things used rarely because newly
    // used things are being added in the start of the list
    // its like a queue every thing that is used recently comes to start
    // of the queue
    LinkedList<String> keysRankingSequence = new LinkedList<>();

    // when something is in the tail it means it was not used for a while and is least used item
    private String findLeastResentUsedKeyInLRUGrading() {
        Object leastUsed = keysRankingSequence.removeLast();
        if (leastUsed != null) {
            return ((String) leastUsed);
        }
        return null;
    }

    // when something is being red or just inserted it comes to the start of the queue
    // if the element is in the middle of the queue it should be removed
    // then it same element should be inserted in the start of the queue
    private void updateRankInLRUGradingToMostRecentUsed(String key) {
        keysRankingSequence.remove(key);
        keysRankingSequence.addFirst(key);
    }


    private String getARandomKey() {
        Random random = new Random();
        int randomIndex = random.nextInt(maxItemsSize + 1);
        LinkedHashMap storage1 = (LinkedHashMap) storage;
        SequencedSet sequencedSet = storage1.sequencedKeySet();
        Iterator iterator = sequencedSet.iterator();
        String keyToBeEvicted = null;
        int counter = 0;
        while (iterator.hasNext()) {

            Object next = iterator.next();
            if (counter == randomIndex) {
                keyToBeEvicted = ((String) next);
            }
            counter++;
        }
        return keyToBeEvicted;
    }

    @Override
    public String toString() {
        return "Database:{" +
                "name='" + name + '\'' +
                ", maxItemsSize=" + maxItemsSize +
                ", evictionPolicy=" + evictionPolicy +
                '}';
    }
}
