package entities;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Database {


    private final Map<String, Item> storage = new HashMap<>();
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
        if (result) --currentItemSize;
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
        return result;
    }

    private boolean checkForIsExpired(Item item) {
        Date currentDate = new Date();
        boolean checkResult = item.expirationDate.before(currentDate);
        if (!checkResult) --currentItemSize;
        return checkResult;
    }

    public Result searchPaginatedByRegex(String regex, int pageNumber, int limit) {

        Set<String> allKeys = storage.keySet();

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

    public Result set(String key, String value) {

        if (checkAvailableSpot()) {
            storage.put(key, new Item(new Date(), value));
            currentItemSize++;
            if (EvictionPolicy.lru.equals(evictionPolicy)) updateRankInLRUGradingToMostRecentUsed(key);
        }
        return new Result("successfully added the item { key : " + key + " , value : " + value + " }");
    }

    private boolean checkAvailableSpot() {
        if (currentItemSize == maxItemsSize) {
            return makeSpotByPolicyOrRejectNewMember();
        }
        return true;
    }

    private boolean makeSpotByPolicyOrRejectNewMember() {
        if (EvictionPolicy.random.equals(evictionPolicy)) {
            String randomkeyCandidateToBeEvicted = getARandomKey();
            if (randomkeyCandidateToBeEvicted != null) {
                return del(randomkeyCandidateToBeEvicted);
            }
            return false;
        } else if (EvictionPolicy.lru.equals(evictionPolicy)) {

            String lruKeyCandidateToBeEvicted = findLeastResentUsedKeyInLRUGrading();
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
            counter++;
            Object next = iterator.next();
            if (counter == randomIndex) {
                keyToBeEvicted = ((String) next);
            }
        }
        return keyToBeEvicted;
    }
}
