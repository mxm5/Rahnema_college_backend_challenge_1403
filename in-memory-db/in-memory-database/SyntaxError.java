public class SyntaxError extends CommandStereoType {
    public SyntaxError(String[] commandChain) {
        super(commandChain);
    }

    @Override
    protected Result runCommand() {
        return null;
    }

    @Override
    protected void runValidateChecksAndSetParams() {

    }
}
