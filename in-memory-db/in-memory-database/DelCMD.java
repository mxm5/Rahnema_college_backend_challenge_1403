import jdk.jshell.spi.ExecutionControl;

public class DelCMD extends CommandStereoType {
    public DelCMD(String[] commandChain) {
        super(commandChain);
    }

    @Override
    protected Result runCommand()  {
//        throw new ExecutionControl.NotImplementedException("to be introduced");
        return null;
    }

    @Override
    protected void runValidateChecksAndSetParams() {

    }
}
