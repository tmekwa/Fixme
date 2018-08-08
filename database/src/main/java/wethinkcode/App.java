package wethinkcode;

public class App
{
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) {
        clearScreen();
        System.out.println("Hello World!");

    }
}
