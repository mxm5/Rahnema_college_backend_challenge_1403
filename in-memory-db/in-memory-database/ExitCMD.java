public class ExitCMD extends CommandStereoType {
    public ExitCMD(String[] cmd) {
        super(cmd);
    }

    @Override
    public Result runCommand() {
        Result res = new Result("exitting the system");
        System.exit(0);
        return res;
    }

    @Override
    protected void runValidateChecksAndSetParams() {

    }


}
