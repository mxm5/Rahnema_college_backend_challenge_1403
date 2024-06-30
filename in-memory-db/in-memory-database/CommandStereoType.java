import java.util.ArrayList;
import java.util.List;

public abstract class CommandStereoType {


    {
        // initialization block
        // create a database with the
        Database defaultDataBase = new Database(
                "0",100,EvictionPolicy.random
        );
    }



    final protected String[] commandChain;
    protected Database selectedDatabase /*= makeSystemDefaultDatabase*/;
    final protected List<Database> availableDatabases = new ArrayList<>();

    public CommandStereoType(String[] commandChain) {
        this.commandChain = commandChain;
    }


    protected void checkForLengthMoreThan(int maximumLength) {
        if (commandChain.length > maximumLength) {
            throw new RuntimeException("command has many parameters ");
        }
    }

    protected void checkForLengthLessThan(int minimumLength) {
        if (commandChain.length < minimumLength) {
            throw new RuntimeException("syntax error use command requires more parameters ");
        }
    }

    protected abstract Result runCommand();

    public void runCommandSteps() {
        runValidateChecksAndSetParams();
        runCommand();
    }

    protected abstract void runValidateChecksAndSetParams();
}
