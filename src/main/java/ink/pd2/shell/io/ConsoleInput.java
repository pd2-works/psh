package ink.pd2.shell.io;

import java.io.IOException;
import java.io.InputStream;

public class ConsoleInput implements Input {
    private final InputStream stream;

    public ConsoleInput(InputStream stream) {
        this.stream = stream;
    }

    @Override
    public char read() {
        try {
            return (char) stream.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (char) -1;
    }
}
