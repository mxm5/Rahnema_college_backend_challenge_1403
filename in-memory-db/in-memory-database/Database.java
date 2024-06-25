public class Database {
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
}
