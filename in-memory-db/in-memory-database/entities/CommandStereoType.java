package entities;

import java.util.ArrayList;
import java.util.List;

public abstract class CommandStereoType {


    final protected String[] commandChain;
    private static Database selectedDatabase;
    private static List<Database> availableDatabases;

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

        if (selectedDatabase == null) {
            initializeDatabases();
        }

        return selectedDatabase;
    }

    private static void initializeDatabases() {
        if (selectedDatabase == null && availableDatabases != null && availableDatabases.size() > 0) {
            selectedDatabase = availableDatabases.getFirst();
        } else if (availableDatabases == null && selectedDatabase == null) {
            availableDatabases = new ArrayList<>();
            selectedDatabase = new Database("0", 10, EvictionPolicy.random);
            availableDatabases.add(selectedDatabase);
        }
    }

    public void setSelectedDatabase(Database selectedDatabase) {
        this.selectedDatabase = selectedDatabase;
    }

    public List<Database> getAvailableDatabases() {

        if (availableDatabases == null) {
            initializeDatabases();
        }

        return availableDatabases;
    }

    public void setAvailableDatabases(List<Database> availableDatabases) {
        this.availableDatabases = availableDatabases;
    }
}
