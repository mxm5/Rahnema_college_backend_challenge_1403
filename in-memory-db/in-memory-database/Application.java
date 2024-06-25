public class Application {


    public static void main(String[] args) throws Exception {

        BlockingInputOutputCommandsHandler IO = new ConsoleIOHandlerBlocking();

        while (true) {
            try {
                processCommand(IO.readCommand());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

    }

    private static void processCommand(String[] strings) {

        CommandStereoType commandStereoType = validateCommandAndDecideTheProcess(strings);

        commandStereoType.runCommandSteps();

    }


    private static CommandStereoType validateCommandAndDecideTheProcess(String[] userCommands) {

        CommandStereoType commandByPreProcess = CommandFactory.getCommandByPreProcess(userCommands);
        commandByPreProcess.runCommandSteps();

        return null;
    }

}