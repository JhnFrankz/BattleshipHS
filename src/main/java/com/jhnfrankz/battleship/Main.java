package com.jhnfrankz.battleship;

import java.util.*;

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

        startGameShot();
    }

    public static void startGameField() {
        for (String[] place : gameField) {
            Arrays.fill(place, "~");
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
                        checkCoordinates();

                        if (!isValidLength(ship.getCells())) { //isn't correct length
                            System.out.printf("Error! Wrong length of the %s! Try again:\n\n", ship.getName());
                        } else {
                            if (isCloseAnotherPlace()) {
                                System.out.println("Error! You placed it too close to another one. Try again:\n");
                            } else {
                                break;
                            }
                        }
                    }
                }
            } while (true);

            // We have valid coords
            fillShipPlace();
            showGameField();
        }
    }

    public static void startGameShot() {
        System.out.println("The game starts!\n");
        showGameField();
        readCoordShot();
    }

    public static void readCoordShot() {
        System.out.println("\nTake a shot!\n");
        while (true) {
            String input = scanner.nextLine().trim();
            if (isValidCoord(input)) {
                int[] coord = getNumbersShotCoord(input);
                shotCoord(coord);
                break;
            } else {
                System.out.println("\nError! You entered the wrong coordinates! Try again:\n");
            }
        }
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

    public static int[] getNumbersShotCoord(String coord) {
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

    public static void shotCoord(int[] coord) {

        String actualCoord = gameField[coord[0]][coord[1]];

        if (actualCoord.equals("O")) {
            gameField[coord[0]][coord[1]] = "X";
            showGameField();
            System.out.println("\nYou hit a ship!");
        } else if (actualCoord.equals("~")) {
            gameField[coord[0]][coord[1]] = "M";
            showGameField();
            System.out.println("\nYou missed!");
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

            for (int validNumber : validNumbers) {
                if (String.valueOf(validNumber).equals(number)) {
                    isValid[i] = true;
                    break;
                }
            }
        }

        return isValid[0] && isValid[1];
    }

    public static void checkCoordinates() {
        int coord1X = strCoordinates[0].charAt(0) - 65;
        int coord1Y = Integer.parseInt(strCoordinates[0].substring(1)) - 1;
        int coord2X = strCoordinates[1].charAt(0) - 65;
        int coord2Y = Integer.parseInt(strCoordinates[1].substring(1)) - 1;

        coord1 = new int[]{coord1X, coord1Y};
        coord2 = new int[]{coord2X, coord2Y};

        orderCoords();

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

    public static void fillShipPlace() {
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
                (coord1[0] == coord2[0] && coord1[1] > coord2[1])) { // si el numero es igual y letras
            int[] aux = coord1;
            coord1 = coord2;
            coord2 = aux;
        }
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