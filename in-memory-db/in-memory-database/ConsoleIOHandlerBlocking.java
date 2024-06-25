import java.util.Scanner;

public class ConsoleIOHandlerBlocking implements BlockingInputOutputCommandsHandler {

    public static Scanner scanner = new Scanner(System.in);

    @Override
    public String[] readCommand() throws Exception {
        String usersInput = scanner.nextLine();
        String cleanedUserInput = usersInput.strip();
        String[] userCommandsChainsArray = cleanedUserInput.split(" ");
        return userCommandsChainsArray;
    }
}
