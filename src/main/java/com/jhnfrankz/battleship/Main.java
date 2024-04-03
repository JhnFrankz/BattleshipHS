package com.jhnfrankz.battleship;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String[][] gameField = new String[10][10];
    private static String[] strCoordinates;
    private static int[] coord1;
    private static int[] coord2;
    private static int lengthBetweenCoords;
    // Shots
    private static final String[][] fogOfWar = new String[10][10];
    private static int[] shotCoord;

    public static void main(String[] args) {
        executeProgram();
    }

    public static void executeProgram() {
        fillGameField(gameField);
        showGameField(gameField);
        fillShips();

        startGameShot();
    }

    public static void fillGameField(String[][] x) {
        for (String[] i : x) {
            Arrays.fill(i, "~");
        }
    }

    public static void fillShips() {
        readShipCoords("Aircraft Carrier", 5);
        readShipCoords("Battleship", 4);
        readShipCoords("Submarine", 3);
        readShipCoords("Cruiser", 3);
        readShipCoords("Destroyer", 2);
    }

    public static void showGameField(String[][] x) {
        String rowLetters = "ABCDEFGHIJ";

        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < x.length; i++) {
            System.out.print(rowLetters.charAt(i));
            for (int j = 0; j < x[i].length; j++) {
                System.out.print(" " + x[i][j]);
            }
            System.out.println();
        }
    }

    public static void readShipCoords(String name, int shipLength) {
        System.out.printf("%nEnter the coordinates of the %s (%d cells):%n%n", name, shipLength);

        do {
            String[] input = scanner.nextLine().trim().split("\\s+");
            System.out.println();
            // TODO: create a method for errors between

            int result = checkErrors(name, shipLength, input);

            if (result == 0) break;
        } while (true);

        // We have valid coords
        fillCoordPlace();
        showGameField(gameField);
//        }
    }

    public static int checkErrors(String name, int shipLength, String[] input) {
        if (input.length != 2 || !isValidCoord(input[0]) || !isValidCoord(input[1])) {
            System.out.println("Error! Try again:\n");
            return 1;
        }

        strCoordinates = new String[]{input[0], input[1]};
        if (!isValidLocation()) { //isn't horizontally or vertically
            System.out.println("Error! Wrong ship location! Try again:\n");
            return 1;
        }

        checkCoordinates();
        if (!isValidLength(shipLength)) { //isn't correct length
            System.out.printf("Error! Wrong length of the %s! Try again:\n\n", name);
            return 1;
        }

        if (isCloseAnotherPlace()) {
            System.out.println("Error! You placed it too close to another one. Try again:\n");
            return 1;
        }

        return 0;
    }

    public static void startGameShot() {
        System.out.println("\nThe game starts!\n");

        fillGameField(fogOfWar);
        showGameField(fogOfWar);

        readCoordShot();
        fillShotCoord(); // Put symbol in Fog of War

        showGameField(gameField);
    }

    public static void readCoordShot() {
        System.out.println("\nTake a shot!\n");

        while (true) {
            String input = scanner.nextLine().trim();
            System.out.println();

            if (isValidCoord(input)) {
                shotCoord = getShotCoord(input);
                break;
            } else {
                System.out.println("Error! You entered the wrong coordinates! Try again:\n");
            }
        }
    }

    public static void fillShotCoord() {
        String actualCoord = gameField[shotCoord[0]][shotCoord[1]];
        String symbol = "";

        if (actualCoord.equals("O")) {
            symbol = "X";
            showFogOfWar(symbol);
            System.out.println("\nYou hit a ship!\n");
        } else if (actualCoord.equals("~")) {
            symbol = "M";
            showFogOfWar(symbol);
            System.out.println("\nYou missed!\n");
        }

        gameField[shotCoord[0]][shotCoord[1]] = symbol;
    }

    public static void showFogOfWar(String symbol) {
        fogOfWar[shotCoord[0]][shotCoord[1]] = symbol;
        showGameField(fogOfWar);
    }

    public static int[] getShotCoord(String coord) {
        String validAlphabets = "ABCDEFGHIJ";
        String[] validNumbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

        String alphabet = coord.substring(0, 1);
        String number = coord.substring(1);

        int[] coordNumber = new int[2];

        for (int i = 0; i < validAlphabets.length(); i++) {
            if (String.valueOf(validAlphabets.charAt(i)).equals(alphabet)) {
                coordNumber[0] = i;
                break;
            }
        }

        for (int i = 0; i < validNumbers.length; i++) {
            if (validNumbers[i].equals(number)) {
                coordNumber[1] = i;
                break;
            }
        }

        return coordNumber;
    }

    public static boolean isValidCoord(String coord) {
        String validAlphabets = "ABCDEFGHIJ";
        String[] validNumbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

        boolean isAlphabet = validAlphabets.contains(String.valueOf(coord.charAt(0)));
        boolean isNumber = false;

        for (String number : validNumbers) {
            if (number.equals(coord.substring(1))) {
                isNumber = true;
                break;
            }
        }

        return isAlphabet && isNumber;
    }

    public static boolean isValidLocation() {
        String strCoord1X = strCoordinates[0].substring(0, 1);   //A1
        int coord1Y = Integer.parseInt(strCoordinates[0].substring(1));
        String strCoord2X = strCoordinates[1].substring(0, 1);   //A4
        int coord2Y = Integer.parseInt(strCoordinates[1].substring(1));

        return (strCoord1X.equals(strCoord2X) && coord1Y != coord2Y)
                || (!strCoord1X.equals(strCoord2X) && coord1Y == coord2Y);
    }

    // For ships input
    public static void checkCoordinates() {
        int coord1X = strCoordinates[0].charAt(0) - 65;
        int coord1Y = Integer.parseInt(strCoordinates[0].substring(1)) - 1;
        int coord2X = strCoordinates[1].charAt(0) - 65;
        int coord2Y = Integer.parseInt(strCoordinates[1].substring(1)) - 1;

        coord1 = new int[]{coord1X, coord1Y};
        coord2 = new int[]{coord2X, coord2Y};

        orderCoords();

        if (coord1[0] == coord2[0]) { //isSameRow
            lengthBetweenCoords = Math.abs(coord1[1] - coord2[1]) + 1;
        } else {
            lengthBetweenCoords = Math.abs(coord1[0] - coord2[0]) + 1;
        }
    }

    public static boolean isValidLength(int shipLength) {
        return lengthBetweenCoords == shipLength;
    }

    public static boolean isCloseAnotherPlace() {
        if (coord1[0] == coord2[0]) {
            int x = coord1[0];      // Siempre 2 == C
            for (int y = coord1[1]; y <= coord2[1]; y++) {   // 3 al 7
                if (isAdyacentOrActual(x, y)) return true;
            }
        } else {  // si 1 es igual
            int y = coord1[1];
            for (int x = coord1[0]; x <= coord2[0]; x++) {
                if (isAdyacentOrActual(x, y)) return true;
            }
        }

        return false;
    }

    private static boolean isAdyacentOrActual(int x, int y) {
        // Verify actual position
        if (gameField[x][y].equals("O")) {
            return true;
        }

        // Verify adyacent cells
        if (x != 0) {
            if (gameField[x - 1][y].equals("O")) { // top
                return true;
            }

            if (y != 0) {
                if (gameField[x - 1][y - 1].equals("O")) { //top left
                    return true;
                }
            }

            if (y != 9) {
                if (gameField[x - 1][y + 1].equals("O")) { //top right
                    return true;
                }
            }
        }

        if (x != 9) {
            if (gameField[x + 1][y].equals("O")) { // bot
                return true;
            }

            if (y != 9) {
                if (gameField[x + 1][y + 1].equals("O")) { //bot right
                    return true;
                }
            }

            if (y != 0) {
                if (gameField[x + 1][y - 1].equals("O")) { //bot left
                    return true;
                }
            }
        }

        if (y != 0) {
            if (gameField[x][y - 1].equals("O")) { // left
                return true;
            }
        }

        if (y != 9) {
            if (gameField[x][y + 1].equals("O")) { // right
                return true;
            }
        }
        return false;
    }

    public static void fillCoordPlace() {
        if (coord1[0] == coord2[0]) {  // Si F es igual
            int x = coord1[0];
            for (int y = coord1[1]; y <= coord2[1]; y++) {
                gameField[x][y] = "O";
            }
        } else {  // si 1 es igual
            int y = coord1[1];
            for (int x = coord1[0]; x <= coord2[0]; x++) {
                gameField[x][y] = "O";
            }
        }
    }

    private static void orderCoords() {
        if ((coord1[1] == coord2[1] && coord1[0] > coord2[0]) ||
                (coord1[0] == coord2[0] && coord1[1] > coord2[1])) {
            int[] aux = coord1;
            coord1 = coord2;
            coord2 = aux;
        }
    }
}

/*class Ship {
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
}*/


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