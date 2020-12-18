package ink.pd2.shell.io;

import java.io.PrintStream;

public class ConsoleOutput implements Output {
    private final PrintStream stream;

    public ConsoleOutput(PrintStream stream) {
        this.stream = stream;
    }

    @Override
    public void write(char c) {
        stream.write(c);
    }
    @Override
    public void write(String s) {
        stream.print(s);
    }
}
