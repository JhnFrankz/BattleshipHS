package com.jhnfrankz.battleship;

public class Game {
    private final Battlefield battlefield;

    public Game(Battlefield battlefield) {
        this.battlefield = battlefield;
    }

    public void printGame() {
        System.out.println(this.battlefield);
    }

    private int checkCoordinates(int i1, int j1, int i2, int j2, int shipLength) {
        if (i1 < 0 || i1 > 9 || j1 < 0 || j1 > 9) {
            return -1;  // no valid coords
        } else if (i2 < 0 || i2 > 9 || j2 < 0 || j2 > 9) {
            return -1;
        } else if (i1 == i2 && j1 == j2) {
            return -1;
        } else if (i1 != i2 && j1 != j2) {
            return -1;
        }

        int lengthRows = Math.abs(i1 - i2) + 1;
        int lengthColumns = Math.abs(j1 - j2) + 1;

        if (i1 == i2) {
            if (lengthColumns != shipLength) {
                return 0;   // No valid length for the ship
            }
            for (int i = Math.min(j1, j2); i <= Math.max(j1, j2); i++) {
                if (battlefield.isAround(i1, i)) { // Check columns around
                    return 1; // if there is ships adyacents
                }
            }
        } else {
            if (lengthRows != shipLength) {
                return 0;
            }
            for (int i = Math.min(i1, i2); i <= Math.max(i1, i2); i++) {
                if (battlefield.isAround(i, j1)) {
                    return 1;
                }
            }
        }

        return 2; // All correct
    }

    public boolean placeShip(String shipName, int shipLength, String coordinates) {
        String[] elements = coordinates.split(" ");
        int i1 = elements[0].charAt(0) - 65;
        int j1 = Integer.parseInt(elements[0].substring(1)) - 1;
        int i2 = elements[1].charAt(0) - 65;
        int j2 = Integer.parseInt(elements[1].substring(1)) - 1;

        int result = checkCoordinates(i1, j1, i2, j2, shipLength);

        if (result == - 1) {
            System.out.println("Error! Wrong ship location! Try again:\n");
            return false;
        } else if (result == 0) {
            System.out.printf("Error! Wrong length of the %s! Try again:\n\n", shipName);
            return false;
        } else if (result == 1) {
            System.out.println("Error! You placed it too close to another one. Try again:\n");
            return false;
        }

        if (i1 == i2) {
            for (int i = Math.min(j1, j2); i <= Math.max(j1, j2); i++) {
                this.battlefield.getField()[i1][i].setShip(true);
            }
        } else if (j1 == j2) {
            for (int i = Math.min(i1, i2); i <= Math.max(i1, i2); i++) {
                this.battlefield.getField()[i][j1].setShip(true);
            }
        }

        return true;
    }
}
