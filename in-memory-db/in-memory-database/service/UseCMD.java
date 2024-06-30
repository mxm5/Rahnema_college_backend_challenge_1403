package service;

import entities.CommandStereoType;
import entities.Database;
import entities.EvictionPolicy;
import entities.Result;

import java.util.Objects;
import java.util.Optional;

public class UseCMD extends CommandStereoType {
    public UseCMD(String[] commandChain) {
        super(commandChain);
    }

    String databaseName;
    Integer databaseMaximumSize;
    EvictionPolicy databaseCacheEvictionPolicy = EvictionPolicy.random;

    @Override
    public Result runCommand() {
        Optional<Database> database = searchDatabasesByName(databaseName);
        if (database.isEmpty()) {
            Database createdDataBase = createDatabaseByParameters();
            selectDatabase(createdDataBase);
        } else selectDatabase(database.get());
        return new Result("successfully selected given database :" + selectedDatabase.getName());
    }

    private void selectDatabase(Database createdDataBase) {
        selectedDatabase = createdDataBase;
    }

    private Database createDatabaseByParameters() {
        return new Database(databaseName, databaseMaximumSize, databaseCacheEvictionPolicy);
    }


    @Override
    protected void runValidateChecksAndSetParams() {
        checkForLengthLessThan(2);
        checkForLengthMoreThan(4);
        setDatabaseName();
        setDatabaseMaximumSize();
        setDatabaseCacheEvictionPolicy();
    }

    private void setDatabaseCacheEvictionPolicy() {
        if (commandChain.length == 4) {
            String evictionPolicy = commandChain[3];
            switch (evictionPolicy) {
                case "lru" -> databaseCacheEvictionPolicy = EvictionPolicy.lru;
                case "noeviction" -> databaseCacheEvictionPolicy = EvictionPolicy.noeviction;
                default -> databaseCacheEvictionPolicy = EvictionPolicy.random;
            }

        }
    }

    private void setDatabaseMaximumSize() {
        try {
            databaseMaximumSize = Integer.parseInt(commandChain[2]);
        } catch (Exception e) {
            throw new RuntimeException(" database max size must be a number");
        }
    }

    private void setDatabaseName() {
        databaseName = commandChain[1];
    }


    private Optional<Database> searchDatabasesByName(String name) {
        return availableDatabases.stream().filter(database -> Objects.equals(database.getName(), name)).findFirst();
    }
}
