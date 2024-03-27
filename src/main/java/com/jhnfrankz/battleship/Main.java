package com.jhnfrankz.battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String[][] gameField = new String[10][10];
    private static String[] strCoordinates;
    private static int[] coord1;
    private static int[] coord2;
    private static int length;
    private static List<Ship> ships;

    public static void main(String[] args) {
        executeProgram();
    }

    public static void executeProgram() {
        startGameField();
        fillShips();
        showGameField();
        readShipsCoordinates();
    }

    public static void startGameField() {
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[i].length; j++) {
                gameField[i][j] = "~";
            }
        }
    }

    public static void fillShips() {
        ships = new ArrayList<>();
        ships.add(0, new Ship("Aircraft Carrier", 5));
        ships.add(1, new Ship("Battleship", 4));
        ships.add(2, new Ship("Submarine", 3));
        ships.add(3, new Ship("Cruiser", 3));
        ships.add(4, new Ship("Destroyer", 2));
    }

    public static void showGameField() {
        String rowLetters = "ABCDEFGHIJ";

        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < gameField.length; i++) {
            System.out.print(rowLetters.charAt(i));
            for (int j = 0; j < gameField[i].length; j++) {
                System.out.print(" " + gameField[i][j]);
            }
            System.out.println();
        }
    }

    public static void readShipsCoordinates() {
        for (Ship ship : ships) {
            System.out.printf("%nEnter the coordinates of the %s (%d cells):%n%n", ship.getName(), ship.getCells());

            do {
                String[] input = scanner.nextLine().trim().split("\\s+");

                if (input.length != 2 || !isValidAlphabetsRow(input) || !isValidNumbersColumn(input)) {
                    System.out.println("Error!\n");
                } else { // Si está bien el input
                    strCoordinates = new String[]{input[0], input[1]};
                    if (!isValidLocation()) { //isn't horizontally or vertically
                        System.out.println("Error! Wrong ship location! Try again:\n");
                    } else { // Si está bien en orientación
                        calculateLength();

                        if (!isValidLength(ship.getCells())) { //isn't correct length
                            System.out.printf("Error! Wrong length of the %s! Try again:\n\n", ship.getName());
                        } else {
                            break;
                        }
                    }
                }
            } while (true);

            // We have valid coords
            fillShipPlace();
            showGameField();
        }
    }

    public static boolean isValidAlphabetsRow(String[] input) {
        String validAlphabets = "ABCDEFGHIJ";
        boolean[] isValid = new boolean[2]; // Coord 1 and coord 2

        if (validAlphabets.contains(String.valueOf(input[0].charAt(0)))) {
            isValid[0] = true;
        }

        if (validAlphabets.contains(String.valueOf(input[1].charAt(0)))) {
            isValid[1] = true;
        }

        return isValid[0] && isValid[1];
    }

    public static boolean isValidNumbersColumn(String[] input) {
        int[] validNumbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        boolean[] isValid = new boolean[2]; // Coord 1 and coord 2

        for (int i = 0; i < input.length; i++) {
            String number = input[i].substring(1);

            for (int j = 0; j < validNumbers.length; j++) {
                if (String.valueOf(validNumbers[j]).equals(number)) {
                    isValid[i] = true;
                    break;
                }
            }
        }

        return isValid[0] && isValid[1];
    }

    public static void calculateLength() {
        String strCoord1X = strCoordinates[0].substring(0, 1);   //A1
        int coord1Y = Integer.parseInt(strCoordinates[0].substring(1));
        String strCoord2X = strCoordinates[1].substring(0, 1);   //A4
        int coord2Y = Integer.parseInt(strCoordinates[1].substring(1));

        //Coords in numbers for array 1
        int coord1X = fromAlphabetToNumber(strCoord1X); //A = 0
        coord1Y = coord1Y - 1;                          //1 = 0

        //Coords in numbers for array 2
        int coord2X = fromAlphabetToNumber(strCoord2X); //A = 0
        coord2Y = coord2Y - 1;

        coord1 = new int[]{coord1X, coord1Y};
        coord2 = new int[]{coord2X, coord2Y};

        //LENGTH
        if (coord1[0] == coord2[0]) { //isSameRow
            length = Math.abs(coord1[1] - coord2[1]) + 1;
        } else {
            length = Math.abs(coord1[0] - coord2[0]) + 1;
        }
    }

    public static boolean isValidLocation() {
        String strCoord1X = strCoordinates[0].substring(0, 1);   //A1
        int coord1Y = Integer.parseInt(strCoordinates[0].substring(1));
        String strCoord2X = strCoordinates[1].substring(0, 1);   //A4
        int coord2Y = Integer.parseInt(strCoordinates[1].substring(1));

        return (strCoord1X.equals(strCoord2X) && coord1Y != coord2Y)
                || (!strCoord1X.equals(strCoord2X) && coord1Y == coord2Y);
    }

    public static boolean isValidLength(int shipCells) {
        return length == shipCells;
    }

    public static void fillShipPlace() {
        if (coord1[0] == coord2[0]) {  // Si F es igual
            if (coord1[1] <= coord2[1]) {  // 2  -  6
                for (int i = coord1[1]; i <= coord2[1]; i++) {
                    gameField[coord1[0]][i] = "O";
                }
            } else {
                for (int i = coord1[1]; i >= coord2[1]; i--) {
                    gameField[coord1[0]][i] = "O";
                }
            }
        } else {  // si 1 es igual
            if (coord1[0] <= coord2[0]) {   //  A  -  D   ->   0 - 3
                for (int i = coord1[0]; i <= coord2[0]; i++) {
                    gameField[i][coord1[1]] = "O";
                }
            } else {
                for (int i = coord1[0]; i >= coord2[0]; i--) {
                    gameField[i][coord1[1]] = "O";
                }
            }
        }
    }

    public static int fromAlphabetToNumber(String alphabet) {
        //Coords for array
        return switch (alphabet) {
            case "A" -> 0;
            case "B" -> 1;
            case "C" -> 2;
            case "D" -> 3;
            case "E" -> 4;
            case "F" -> 5;
            case "G" -> 6;
            case "H" -> 7;
            case "I" -> 8;
            case "J" -> 9;
            default -> -1;
        };
    }
}

class Ship {
    private final String name;
    private final int cells;

    public Ship(String name, int cells) {
        this.name = name;
        this.cells = cells;
    }

    public String getName() {
        return name;
    }

    public int getCells() {
        return cells;
    }
}




/*public static void readCoordinates() {
    System.out.println("Enter the coordinates of the ship:");
    String[] input = scanner.nextLine().trim().split("\\s+");

    if (input.length != 2 || !isValidAlphabetsRow(input) || !isValidNumbersColumn(input)) {
        System.out.println("Error!");
        error = true;
    } else {
        coordinates = new String[]{input[0], input[1]};
    }
}*/





//PARTS
        /*StringBuilder messageParts = new StringBuilder("Parts:");
        if (coord1[0] == coord2[0]) {  // Si A es igual
            if (coord1[1] <= coord2[1]) {
                for (int i = coord1[1]; i <= coord2[1]; i++) { //generamos numeros
                    messageParts.append(String.format(" %s%d", strCoord1X, i + 1));
                }
            } else {
                for (int i = coord1[1]; i >= coord2[1]; i--) { //generamos numeros
                    messageParts.append(String.format(" %s%d", strCoord1X, i + 1));
                }
            }
        } else {  // si 1 es igual
            if (coord1[0] <= coord2[0]) {
                for (int i = coord1[0]; i <= coord2[0]; i++) {
                    messageParts.append(String.format(" %s%d", fromNumberToAlphabet(i + 1), coord1Y + 1));  //generamos letras
                }
            } else {
                for (int i = coord1[0]; i >= coord2[0]; i--) {
                    messageParts.append(String.format(" %s%d", fromNumberToAlphabet(i + 1), coord1Y + 1));  //generamos letras
                }
            }
        }

        System.out.println(messageParts);*/







    /*public static boolean isThereError(String[] input, Ship ship) {
        if (input.length != 2 || !isValidAlphabetsRow(input) || !isValidNumbersColumn(input)) {
            System.out.println("Error!\n");
            return true;
        } else if(!isValidLocation()) { //isn't horizontally or vertically
            System.out.println("Error! Wrong ship location! Try again:\n");
            return true;
        } else if (!isValidLength(ship.getCells())) { //isn't correct length
            System.out.println("Error! Wrong length of the Submarine! Try again:\n");
            return true;
        }

        return false;
    }*/






    /*public static String fromNumberToAlphabet(int number) {
        //Coords for array
        return switch (number) {
            case 1 -> "A";
            case 2 -> "B";
            case 3 -> "C";
            case 4 -> "D";
            case 5 -> "E";
            case 6 -> "F";
            case 7 -> "G";
            case 8 -> "H";
            case 9 -> "I";
            case 10 -> "J";
            default -> "_";
        };
    }*/