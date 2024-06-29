import java.io.PushbackReader;

public class Application {

    public static String banner = "         _.-``__ ''-._                                             \n" +
            "      _.-``    `.  `_.  ''-._           \n" +
            "  .-`` .-```.  ```\\/    _.,_ ''-._                                   \n" +
            " (    '      ,       .-`  | `,    )     \n" +
            " |`-._`-...-` __...-.``-._|'` _.-'|     \n" +
            " |    `-._   `._    /     _.-'    |     \n" +
            "  `-._    `-._  `-./  _.-'    _.-'                                   \n" +
            " |`-._`-._    `-.__.-'    _.-'_.-'|                                  \n" +
            " |    `-._`-._        _.-'_.-'    |           FAKE REDIS        \n" +
            "  `-._    `-._`-.__.-'_.-'    _.-'                                   \n" +
            " |`-._`-._    `-.__.-'    _.-'_.-'|           thank you for using      \n" +
            " |    `-._`-._        _.-'_.-'    |                                  \n" +
            "  `-._    `-._`-.__.-'_.-'    _.-'                                   \n" +
            "      `-._    `-.__.-'    _.-'                                       \n" +
            "          `-._        _.-'                                           \n" +
            "              `-.__.-'                           ";

    public static String userGuide = "using the default database with the random eviction policy "+ '\n'+"interact with the default database or create another custom database";

    public static void main(String[] args) throws Exception {

        BlockingInputOutputCommandsHandler IO = new ConsoleIOHandlerBlocking();
        printBanner();
        runCliCommandExcecutionLoop(IO);

    }


    private static void printBanner() {
        System.out.println(banner);
        System.out.println(userGuide);
    }

    private static void runCliCommandExcecutionLoop(BlockingInputOutputCommandsHandler IO) {
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
        return CommandFactory.getCommandByPreProcess(userCommands);
    }

}