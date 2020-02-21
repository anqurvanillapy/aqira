import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Cli {
    private static final String help = "aqira COMMAND [args...]";

    private static final Map<String, Integer> argsInfo = Map.of(
            "help", 0,
            "version", 0,
            "format", 1,
            "build", 1,
            "run", 1
    );

    public String getSubCommand() {
        return subCommand;
    }

    public String getFile() {
        return file;
    }

    private final String subCommand;
    private final String file;

    private Cli(String subCommand, String file) {
        this.subCommand = subCommand;
        this.file = file;
    }

    public static void printHelp() {
        System.out.println(help);
    }

    @Nullable
    public static Cli parse(@NotNull String[] args) {
        if (args.length == 0) {
            return null;
        }

        String subCommand = args[0];
        Integer argNum = argsInfo.get(subCommand);
        if (argNum == null) {
            return null;
        }

        if (argNum == 0 && args.length - 1 == 0) {
            return new Cli(subCommand, "");
        } else if (argNum == 1 && args.length - 1 == 1) {
            return new Cli(subCommand, args[1]);
        }

        return null;
    }
}
