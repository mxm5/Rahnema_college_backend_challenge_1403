package entities;

import java.util.Scanner;

public class ConsoleIOHandlerBlocking {

    public static Scanner scanner = new Scanner(System.in);


    public String[] readCommand() throws Exception {
        String usersInput = scanner.nextLine();
        String cleanedUserInput = usersInput.strip();
        cleanedUserInput = cleanedUserInput.trim();
        String[] userCommandsChainsArray = cleanedUserInput.split(" ");
        return userCommandsChainsArray;
    }
}
