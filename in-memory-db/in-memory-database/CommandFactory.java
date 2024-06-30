import entities.CommandStereoType;
import entities.SyntaxError;
import service.*;

public class CommandFactory {


    public static CommandStereoType getCommandByPreProcess(String[] commandChain) {
        String starterCommand = commandChain[0].toLowerCase();
        switch (starterCommand) {
            case "exit" -> {
                return new ExitCMD(commandChain);
            }
            case "list" -> {
                return new ListCMD(commandChain);
            }
            case "use" -> {
                return new UseCMD(commandChain);
            }
            case "keys" -> {
                return new KeysCMD(commandChain);
            }
            case "del" -> {
                return new DelCMD(commandChain);
            }
            case "get" -> {
                return new GetCMD(commandChain);
            }
            case "set" -> {
                return new SetCMD(commandChain);
            }
            default -> {
                return new SyntaxError(commandChain);
            }
        }


    }
}
