package com.jhnfrankz.battleship;

import java.util.Scanner;

public class UserInterface {
    private final Scanner scanner;
    private final Game game;

    public UserInterface(Scanner scanner, Game game) {
        this.scanner = scanner;
        this.game = game;
    }

    public void start() {
        initialise("Aircraft Carrier", 5);
        initialise("Battleship", 4);
        initialise("Submarine", 3);
        initialise("Cruiser", 3);
        initialise("Destroyer", 2);
        this.game.printGame();
    }

    private void initialise(String shipName, int shipLength) {
        this.game.printGame();
        System.out.printf("Enter the coordinates of the %s (%d cells):\n\n", shipName, shipLength);
        while (true) {
            String input = scanner.nextLine();
            System.out.println();
            if (this.game.placeShip(shipName, shipLength, input)) { // if fill the coord correctly
                break;
            }
        }
    }
}
