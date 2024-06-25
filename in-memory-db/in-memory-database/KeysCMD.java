public class KeysCMD extends CommandStereoType {
    public KeysCMD(String[] commandChain) {
        super(commandChain);
    }

    String regex;

    @Override
    public Result runCommand() {
        return null;
    }

    @Override
    protected void runValidateChecksAndSetParams() {
        checkForLengthMoreThan(6);
        checkForLengthLessThan(2);
    }
}
