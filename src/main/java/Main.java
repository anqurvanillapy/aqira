import java.io.File;
import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Command;
import picocli.CommandLine.Spec;
import picocli.CommandLine.Parameters;

@Command(name = "aqira", version = "Aqira 0.1.0", description = "Aqira",
        mixinStandardHelpOptions = true, subcommands = {
        CmdBuild.class,
        CmdRun.class,
        CmdFormat.class,
})
public class Main implements Callable<Integer> {
    @Spec
    CommandSpec spec;

    @Override
    public Integer call() {
        spec.commandLine().usage(System.out);
        return 1;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }
}

@Command(name = "build", aliases = "b", description = "Build a source file.")
class CmdBuild implements Callable<Integer> {
    @Parameters(index = "0")
    private File file;

    @Override
    public Integer call() {
        System.out.println("Building file: " + file);
        return 0;
    }
}

@Command(name = "run", aliases = "r", description = "Run a source file.")
class CmdRun implements Callable<Integer> {
    @Parameters(index = "0")
    private File file;

    @Override
    public Integer call() {
        System.out.println("Running file: " + file);
        return 0;
    }
}

@Command(name = "fmt", aliases = "f", description = "Format a source file.")
class CmdFormat implements Callable<Integer> {
    @Parameters(index = "0")
    private File file;

    @Override
    public Integer call() {
        System.out.println("Formatting file: " + file);
        return 0;
    }
}
