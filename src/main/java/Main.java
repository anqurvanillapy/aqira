public class Main {
    private static final String VERSION = "0.1.0";

    public static void main(String[] args) {
        Cli cliArgs = Cli.parse(args);

        if (cliArgs == null) {
            Cli.printHelp();
            System.exit(1);
        }

        switch (cliArgs.getSubCommand()) {
            case "help":
                Cli.printHelp();
                break;

            case "version":
                System.out.println("aqira " + VERSION);
                break;

            case "format":
                System.out.println("formatting source file: " + cliArgs.getFile());
                break;

            case "build":
                System.out.println("building source file: " +
                        cliArgs.getFile());
                break;

            case "run":
                System.out.println("running source file: " + cliArgs.getFile());
                break;
        }
    }
}
