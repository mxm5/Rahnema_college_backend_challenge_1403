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


    public boolean del(String key) {
        Item remove = storage.remove(key);
        return !Objects.isNull(remove);

    }

    public Result get(String key) {
        Item item = storage.get(key);
        if (Objects.isNull(item) || checkForIsExpired(item)) {
            return new Result("item could not be found " + '\n' + "null");
        }
        return new Result("item found for given key " + '\n' + item.value);
    }

    private boolean checkForIsExpired(Item item) {
        Date currentDate = new Date();
        return item.expirationDate.after(currentDate);
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
            if (matcher.find()) {
                foundResultCounter++;
                if (foundResultCounter >= startingMatchingResultsCount && foundResultCounter <= theMaximumMatchingResultsCount) {
                    results.add(givenKeyString);
                }
            }
        }


        return new Result("the searching operation finished"+'\n'+results.size()+" result found : "+results);

    }
}
