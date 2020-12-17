package ink.pd2.shell.io;

public class ConsoleOutput implements Output {
    @Override
    public void write(char c) {
        System.out.write(c);
    }

    @Override
    public void write(String s) {
        System.out.print(s);
    }
}
