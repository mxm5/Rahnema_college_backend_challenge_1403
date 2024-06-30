package entities;

public class SyntaxError extends CommandStereoType {
    public SyntaxError(String[] commandChain) {
        super(commandChain);
    }

    @Override
    protected Result runCommand() {

        return new Result("you are typing incorrectly please write a defined command or type help to see how to use the software but help is not implemented yet ! HeHe just invite me time is short to implement help command ");
    }

    @Override
    protected void runValidateChecksAndSetParams() {

    }
}
