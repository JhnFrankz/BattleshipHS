package com.jhnfrankz.battleship;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Battlefield battlefield = new Battlefield();
        Game game = new Game(battlefield);
        UserInterface ui = new UserInterface(scanner, game);

        ui.start();
    }
}