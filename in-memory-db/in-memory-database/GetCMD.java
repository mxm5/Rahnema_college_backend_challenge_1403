public class GetCMD extends CommandStereoType {
    public GetCMD(String[] commandChain) {
        super(commandChain);
    }

    private String key;

    @Override
    protected Result runCommand() {
        return selectedDatabase.get(key);
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
            throw new RuntimeException("failed getting the key ");
        }
    }
}
