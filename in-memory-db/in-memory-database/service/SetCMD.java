package service;

import entities.CommandStereoType;
import entities.Result;

public class SetCMD extends CommandStereoType {
    private String value;
    private String key;
    private Integer timeToLive;


    public SetCMD(String[] commandChain) {
        super(commandChain);
    }

    @Override
    protected Result runCommand() {
        return getSelectedDatabase().set(key, value,timeToLive);
    }

    @Override
    protected void runValidateChecksAndSetParams() {
        checkForLengthLessThan(3);
        checkForLengthMoreThan(4);
        setKey();
        setValue();
        setTimeToLive();
    }

    private void setTimeToLive() {
        try {
            if (commandChain.length == 4) {
                timeToLive = Integer.parseInt(commandChain[3]);
            }
        } catch (Exception e) {
            throw new RuntimeException("time to live is optional but it should be a positive integer number");
        }
    }

    private void setValue() {
        value = commandChain[2];
    }

    private void setKey() {
        key = commandChain[1];
    }
}
