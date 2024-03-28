package com.jhnfrankz.battleship;

public class Battlefield {
    private final Cell[][] field;

    public Battlefield() {
        this.field = createBattlefield();
    }

    public Cell[][] getField() {
        return field;
    }

    private Cell[][] createBattlefield() {
        Cell[][] field = new Cell[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                field[i][j] = new Cell();
            }
        }

        return field;
    }

    public boolean isAround(int i, int j) {
        try {
            // TODO: dont verify around when cell is in border of game, directly return false
            if (this.field[i][j - 1].isShip()) { //left
                return true;
            }
            if (this.field[i - 1][j].isShip()) { // top
                return true;
            }
            if (this.field[i][j + 1].isShip()) { // right
                return true;
            }
            if (this.field[i + 1][j].isShip()) { // bot
                return true;
            }
            if (this.field[i - 1][j - 1].isShip()) { // top left
                return true;
            }
            if (this.field[i - 1][j + 1].isShip()) { // top right
                return true;
            }
            if (this.field[i + 1][j - 1].isShip()) { // bot left
                return true;
            }
            return this.field[i + 1][j + 1].isShip(); // bot right
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        StringBuilder toPrint = new StringBuilder();
        toPrint.append("  1 2 3 4 5 6 7 8 9 10\n");
        for (int i = 0; i < 10; i++) {
            toPrint.append((char) (i + 65));
            toPrint.append(" ");
            for (int j = 0; j < 10; j++) {
                toPrint.append(this.field[i][j]);
                toPrint.append(" ");
            }
            toPrint.append("\n");
        }

        return toPrint.toString();
    }
}
