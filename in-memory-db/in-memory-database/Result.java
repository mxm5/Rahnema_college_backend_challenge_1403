import java.util.ArrayList;
import java.util.List;

public class Result {
    final String msg;


    public List<String> getFoundResults() {
        return foundResults;
    }

    public void setFoundResults(List<String> foundResults) {
        this.foundResults = foundResults;
    }

    List<String> foundResults = new ArrayList<>();

    public Result(String msg) {
        this.msg = msg;
        System.out.println(msg);
    }

    public String getMsg() {
        return msg;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Item getValue() {
        return value;
    }

    public void setValue(Item value) {
        this.value = value;
    }

    String key;

    Item value;
}
