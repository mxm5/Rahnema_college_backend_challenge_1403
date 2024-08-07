package service;

import entities.CommandStereoType;
import entities.Result;

public class ListCMD extends CommandStereoType {
    public ListCMD(String[] commandChain) {
        super(commandChain);
    }

    @Override
    public Result runCommand() {
        return new Result("available data bases are : "+getAvailableDatabases());
    }

    @Override
    protected void runValidateChecksAndSetParams() {
        checkForLengthMoreThan(1);
    }
}
