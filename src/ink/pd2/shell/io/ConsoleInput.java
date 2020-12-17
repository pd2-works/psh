package ink.pd2.shell.io;

import java.io.IOException;

public class ConsoleInput implements Input {
    @Override
    public char read() {
        try {
            return (char) System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (char) -1;
    }
}
