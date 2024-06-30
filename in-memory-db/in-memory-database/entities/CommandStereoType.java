package entities;

import java.util.ArrayList;
import java.util.List;

public abstract class CommandStereoType {


//    {
//        // initialization block
//        // create a database with the
//        this.availableDatabases = new ArrayList<>();
//        if (this.availableDatabases.) {
//            Database defaultDataBase = new Database(
//                    "0", 100, EvictionPolicy.random
//            );
//        }
//
//        this.selectedDatabase = defaultDataBase;
//        this.availableDatabases.add(this.selectedDatabase);
//    }


    final protected String[] commandChain;
    private Database selectedDatabase ;
    private List<Database> availableDatabases;

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

    public Database getSelectedDatabase() {
        return selectedDatabase;
    }

    public void setSelectedDatabase(Database selectedDatabase) {
        this.selectedDatabase = selectedDatabase;
    }

    public List<Database> getAvailableDatabases() {
        return availableDatabases;
    }

    public void setAvailableDatabases(List<Database> availableDatabases) {
        this.availableDatabases = availableDatabases;
    }
}
