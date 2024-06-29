

public class SetCMD extends CommandStereoType {
    public SetCMD(String[] commandChain) {
        super(commandChain);
    }

    @Override
    protected Result runCommand() {
        return null;
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
    }

    private void setValue() {
        
    }

    private void setKey() {
        
    }
}
