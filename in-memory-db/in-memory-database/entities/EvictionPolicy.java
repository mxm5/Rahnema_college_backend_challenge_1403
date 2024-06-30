package entities;

public enum EvictionPolicy {
    random("random"), noeviction("noeviction"), lru("lru");

    final String value;

    public String getValue() {
        return value;
    }

    EvictionPolicy(String policyValue) {
        value = policyValue;
    }
}
