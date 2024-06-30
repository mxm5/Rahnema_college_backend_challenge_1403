package entities;

import java.util.Date;

public class Item {
    final Date expirationDate;

    final String value;

    public String getValue() {
        return value;
    }

    public Date getExpirationDate() {

        return expirationDate;
    }

    public Item(Date expirationDate, String value) {
        this.expirationDate = expirationDate;
        this.value = value;
    }
}
