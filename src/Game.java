import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private static final String[][] board = {{" ", " ", " "},
                                             {" ", " ", " "},
                                             {" ", " ", " "}};

    static void run() {
        var scanner = new Scanner(System.in);
        var victoriousPlayer = 0;
        var player1Plays = new ArrayList<int[]>();
        var player2Plays = new ArrayList<int[]>();
        var player1Turn = true;

        System.out.println("*************");
        System.out.println(" TIC TAC TOE ");
        System.out.println("*************");

        while (victoriousPlayer == 0) {
            System.out.println();
            drawBoard();
            System.out.println();
            
            System.out.printf("Player %s turn.\n", player1Turn ? "1" : "2");
            System.out.print("Enter the row (1-3): ");
            var rowChosen = scanner.nextInt();

            while (rowChosen > 3 || rowChosen < 1) {
                System.out.print("Please, enter a valid row number (1-3): ");
                rowChosen = scanner.nextInt();
            }

            System.out.print("Enter the col (1-3): ");
            var colChosen = scanner.nextInt();

            while (colChosen > 3 || colChosen < 1) {
                System.out.print("Please, enter a valid col number (1-3): ");
                colChosen = scanner.nextInt();
            }

            var isCellAvailable = board[rowChosen - 1][colChosen - 1].isBlank();

            if (!isCellAvailable) {
                System.out.println("This cell has already been chosen.");
                continue;
            }

            if (player1Turn) {
                player1Plays.add(new int[]{rowChosen, colChosen});
            } else {
                player2Plays.add(new int[]{rowChosen, colChosen});
            }

            board[rowChosen - 1][colChosen - 1] = player1Turn ? "X" : "O";

            player1Turn = !player1Turn;
        }

        scanner.close();
    }

    static void drawBoard() {
        for (var row : board) {
            for (var col : row) {
                System.out.print(" [" + col + "]");
            }

            System.out.println();
        }
    }
}
