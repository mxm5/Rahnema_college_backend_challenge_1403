public class ExitCMD extends CommandStereoType {
    public ExitCMD(String[] cmd) {
        super(cmd);
    }

    @Override
    public Result runCommand() {
        System.exit(0);
        return null;
    }

    @Override
    protected void runValidateChecksAndSetParams() {

    }


}
