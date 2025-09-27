import java.util.Random;
import java.util.Scanner;

public class main {

    public static int[][] GenerateSquare(int n) {
        int[][] magicSquare = new int[n][n];

        int row = 1;
        int column = (n+1)/2;
        magicSquare[row][column] = 1;
        
        for (int i = 2; i <= n*n; i++) {
            if (magicSquare[(row-1+n)%n][(column+1)%n] == 0) {
                row = (row-1+n)%n;
                column = (column+1)%n;
            } else {
                row = (row+1)%n;
            }
            magicSquare[row][column] = i;
        }
        return magicSquare;
    }

        public static void shuffleSquare(int[][] square) {
        int n = square.length;
        Random rand = new Random();

        for (int i = 0; i < n * n; i++) {
            int row = rand.nextInt(n);
            int col = rand.nextInt(n);

            int[] dRow = {0, 0, -1, 1};
            int[] dCol = {-1, 1, 0, 0};
            int dir = rand.nextInt(4);

            int newRow = (row + dRow[dir] + n) % n;
            int newCol = (col + dCol[dir] + n) % n;

            int temp = square[row][col];
            square[row][col] = square[newRow][newCol];
            square[newRow][newCol] = temp;
        }
    }

    public static void printSquare(int[][] square) {
        for (int[] row : square) {
            for (int item : row) {
                System.out.print(item + " ");
            }
            System.out.println();
        }
    }    
    
    public static boolean isMagicSquare(int[][] square) {
        int n = square.length;
        int magicSum = n * (n * n + 1) / 2;

        for (int i = 0; i < n; i++) {
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < n; j++) {
                rowSum += square[i][j];
                colSum += square[j][i];
            }
            if (rowSum != magicSum || colSum != magicSum) {
                return false;
            }
        }

        int diagSum1 = 0;
        int diagSum2 = 0;
        for (int i = 0; i < n; i++) {
            diagSum1 += square[i][i];
            diagSum2 += square[i][n - 1 - i];
        }
        return diagSum1 == magicSum && diagSum2 == magicSum;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n;

        while (true) {
            System.out.print("Enter the size of the magic square (has to be odd): ");
            if (scanner.hasNextInt()) {
                n = scanner.nextInt();
                if (n % 2 != 0) {
                    scanner.nextLine();
                    break;
                } else {
                    System.out.println("The number must be odd. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next();
            }
        }
        int[][] sSquare = GenerateSquare(n);

        int[][] pSquare = new int[n][n];
        for (int i = 0; i < n; i++) {
            pSquare[i] = sSquare[i].clone();
        }
        
        shuffleSquare(pSquare);

        System.out.println("Your Puzzle:");
        printSquare(pSquare);

        int moveCount = 0;

        while (true) {
            if (isMagicSquare(pSquare)) {
                System.out.println("Puzzle solved!");
                break;
            }
            System.out.println();
            System.out.print("Enter your move (row column direction): ");
            String input = scanner.nextLine();

            String[] inpArray = input.split(" ");
            if (inpArray.length != 3) {
                System.out.println("Invalid input format. Please enter in the form 'i j direction'.");
                continue;
            }

            int i, j;
            try {
                i = Integer.parseInt(inpArray[0]);
                j = Integer.parseInt(inpArray[1]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid indices. Please enter valid integers (between 0 and " + (pSquare.length-1) + ") for i and j.");
                continue;
            }

            String direction = inpArray[2].toUpperCase();
            if (!(direction.equals("U") || direction.equals("D") || direction.equals("L") || direction.equals("R"))) {
                System.out.println("Invalid direction. Use U, D, L, or R.");
                continue;
            }

            if (i < 0 || i >= n || j < 0 || j >= n) {
                System.out.println("Indices out of bounds. Please enter valid indices within the range of the square (between 0 and " + (pSquare.length-1) + ").");
                continue;
            }

            int newRow = i, newCol = j;

            switch (direction) {
                case "U" -> newRow = (i - 1 + n) % n;
                case "D" -> newRow = (i + 1) % n;
                case "L" -> newCol = (j - 1 + n) % n;
                case "R" -> newCol = (j + 1) % n;
            }

            int temp = pSquare[i][j];
            pSquare[i][j] = pSquare[newRow][newCol];
            pSquare[newRow][newCol] = temp;

            moveCount++;
            printSquare(pSquare);
        }

        System.out.println("Game finished! Total moves made: " + moveCount);
    }
}