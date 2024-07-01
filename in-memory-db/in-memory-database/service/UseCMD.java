package service;

import entities.CommandStereoType;
import entities.Database;
import entities.EvictionPolicy;
import entities.Result;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class UseCMD extends CommandStereoType {
    public UseCMD(String[] commandChain) {
        super(commandChain);
    }

    String databaseName;
    Integer databaseMaximumSize;
    EvictionPolicy databaseCacheEvictionPolicy = EvictionPolicy.random;

    @Override
    public Result runCommand() {
        Optional<Database> database = Optional.empty();
        if (!getAvailableDatabases().isEmpty()) {
            database = searchDatabasesByName(databaseName);
        }
        if (database.isEmpty()) {
            Database createdDataBase = createDatabaseByParameters();
            getAvailableDatabases().add(createdDataBase);
            setSelectedDatabase(createdDataBase);
        } else {
            setSelectedDatabase(database.get());
        }
        return new Result("successfully selected given database :" + getSelectedDatabase().getName());
    }

    private void selectDatabase(Database createdDataBase) {
        setSelectedDatabase(createdDataBase);
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
            if (commandChain.length > 2) {
                databaseMaximumSize = Integer.parseInt(commandChain[2]);
            }
        } catch (NumberFormatException numberFormatException) {
            throw new RuntimeException(" database max size must be a number");
        }
        if (databaseMaximumSize == null) {
            databaseMaximumSize = 10;
        }
    }

    private void setDatabaseName() {
        databaseName = commandChain[1];
    }


    private Optional<Database> searchDatabasesByName(String name) {
        return getAvailableDatabases().stream().filter(database -> Objects.equals(database.getName(), name)).findFirst();
    }
}
