package service;

import entities.CommandStereoType;
import entities.Result;

public class DelCMD extends CommandStereoType {
    public DelCMD(String[] commandChain) {
        super(commandChain);

    }

    String key;

    @Override
    protected Result runCommand() {
        boolean res = getSelectedDatabase().del(key);
        return new Result("the data was removing result was " + res);
    }

    @Override
    protected void runValidateChecksAndSetParams() {
        checkForLengthLessThan(2);
        checkForLengthMoreThan(2);
        setKey();
    }

    private void setKey() {
        try {
            this.key = commandChain[1];
        } catch (Exception e) {
            throw new RuntimeException("setting key in deletion operation failed");
        }
    }
}
