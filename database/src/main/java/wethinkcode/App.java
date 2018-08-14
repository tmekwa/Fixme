package wethinkcode;

import wethinkcode.utils.BasicPrompt;

public class App
{
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) {
        clearScreen();
        BasicPrompt bp  = new BasicPrompt();
        bp.databaseLogic();
    }
}
