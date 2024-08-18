package minesweeper;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Minesweeper {
    private static final char MINE = '*';
    private static final char COVERED = '_';
    private static final char UNCOVERED = '0';
    private char[][] grid;
    private boolean[][] revealed;
    private int size;
    private int mines;

    public char[][] getGrid() {
        return grid;
    }

    public void setGrid(char[][] grid) {
        this.grid = grid;
    }

    public Minesweeper(int size, int mines) {
        this.size = size;
        this.mines = mines;
        this.grid = new char[size][size];
        this.revealed = new boolean[size][size];
        initializeGrid();
        placeMines();
    }

    private void initializeGrid() {
        for (int i = 0; i < size; i++) {
            Arrays.fill(grid[i], COVERED);
        }
    }

    private void placeMines() {
        Random rand = new Random();
        int placedMines = 0;
        while (placedMines < mines) {
            int row = rand.nextInt(size);
            int col = rand.nextInt(size);
            if (grid[row][col] != MINE) {
                grid[row][col] = MINE;
                placedMines++;
            }
        }
    }

    public void displayGrid() {
        System.out.print("  ");
        for (int i = 0; i < size; i++) {
            System.out.print((i + 1) + " ");
        }
        System.out.println();
        for (int i = 0; i < size; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < size; j++) {
                if (revealed[i][j]) {
                    System.out.print(grid[i][j] + " ");
                } else {
                    System.out.print(COVERED + " ");
                }
            }
            System.out.println();
        }
    }

    public boolean uncoverSquare(int row, int col) {
        if (grid[row][col] == MINE) {
            return false;
        }
        reveal(row, col);
        return true;
    }

    private void reveal(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size || revealed[row][col]) {
            return;
        }
        revealed[row][col] = true;
        int adjacentMines = countAdjacentMines(row, col);
        if (adjacentMines > 0) {
            grid[row][col] = (char) ('0' + adjacentMines);
        } else {
            grid[row][col] = UNCOVERED;
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i != 0 || j != 0) {
                        reveal(row + i, col + j);
                    }
                }
            }
        }
    }

    private int countAdjacentMines(int row, int col) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;
                if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size && grid[newRow][newCol] == MINE) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean isGameWon() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] != MINE && !revealed[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Minesweeper!");
        System.out.print("Enter the size of the grid (e.g. 4 for a 4x4 grid): ");
        int size = scanner.nextInt();

        System.out.print("Enter the number of mines to place on the grid (maximum is 35% of the total squares): ");
        int mines = scanner.nextInt();
        Minesweeper game = new Minesweeper(size, mines);

        while (true) {
            game.displayGrid();
            System.out.print("Select a square to reveal (e.g. A1): ");
            String input = scanner.next();
            int row = input.charAt(0) - 'A';
            int col = Integer.parseInt(input.substring(1)) - 1;
            if (!game.uncoverSquare(row, col)) {
                System.out.println("You hit a mine! Game over.");
                break;
            }
            if (game.isGameWon()) {
                System.out.println("Congratulations, you have won the game!");
                break;
            }
        }
        scanner.close();
    }
}
