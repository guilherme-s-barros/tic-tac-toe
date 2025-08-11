package io.github.guilhermesbarros.tictactoe;

import io.github.guilhermesbarros.tictactoe.exceptions.Either;
import io.github.guilhermesbarros.tictactoe.exceptions.Left;
import io.github.guilhermesbarros.tictactoe.exceptions.Right;
import io.github.guilhermesbarros.tictactoe.utils.ClearConsole;

import java.util.*;

public class TicTacToe {
    private static final List<List<Cell>> WIN_PLAYS =
            List.of(List.of(Cell.A1, Cell.A2, Cell.A3),
                    List.of(Cell.B1, Cell.B2, Cell.B3),
                    List.of(Cell.C1, Cell.C2, Cell.C3),
                    List.of(Cell.A1, Cell.B1, Cell.C1),
                    List.of(Cell.A2, Cell.B2, Cell.C2),
                    List.of(Cell.A3, Cell.B3, Cell.C3),
                    List.of(Cell.A1, Cell.B2, Cell.C3),
                    List.of(Cell.C1, Cell.B2, Cell.A3));

    private static final Map<Cell, Character> board = new HashMap<>();

    public void run() {
        var scanner = new Scanner(System.in);
        var currentPlayer = 'X';
        var gameOver = false;
        var hasWinner = false;

        while (!gameOver) {
            drawBoard();
            System.out.printf("Vez do jogador \"%s\"\n", currentPlayer);
            System.out.print(
                    "Escolha uma célula (linha [1-3], coluna [1-3]): ");

            var input = scanner.nextLine();
            ClearConsole.execute();

            Either<String, int[]> validateInputResult = validateInput(input);

            if (validateInputResult.isLeft()) {
                System.out.println(validateInputResult.getLeft());
                continue;
            }

            int[] cellChosen = validateInputResult.getRight();

            Either<String, Cell> getCellResult =
                    Cell.getCell(cellChosen[0] - 1, cellChosen[1] - 1);

            if (getCellResult.isLeft()) {
                System.out.println(getCellResult.getLeft());
                continue;
            }

            Cell cell = getCellResult.getRight();

            Either<String, Character> markResult = mark(cell, currentPlayer);

            if (markResult.isLeft()) {
                System.out.println(markResult.getLeft());
                continue;
            }

            hasWinner = checkIfHasWinner(currentPlayer);

            if (hasWinner || board.size() >= 9) {
                gameOver = true;
            } else {
                currentPlayer = currentPlayer == 'X' ? 'O' : 'X';
            }
        }

        drawBoard();
        System.out.println("  GAME OVER ");

        if (hasWinner) {
            System.out.printf(" \"%s\" venceu!", currentPlayer);
        } else {
            System.out.println("   Empatou ");
        }

        scanner.close();
    }

    private void drawBoard() {
        System.out.println(" TIC TAC TOE ");

        for (int row = 0; row < 3; row++) {
            System.out.print(" ");

            for (int col = 0; col < 3; col++) {
                Cell cell = Cell.getCell(row, col).getRight();
                char symbol = board.getOrDefault(cell, ' ');

                System.out.print(" " + symbol + " ");

                if (col < 2) {
                    System.out.print("|");
                }
            }

            System.out.println();

            if (row < 2) {
                System.out.println(" ---+---+---");
            }
        }
    }

    private Either<String, int[]> validateInput(String input) {
        try {
            var cellChosen =
                    Arrays.stream(input.replaceAll("\\s", "").split(","))
                          .mapToInt(Integer::parseInt).toArray();

            if (cellChosen.length != 2) {
                return new Left<>(
                        "Insira 2 números separados por vírgula, sendo linha e coluna, respectivamente");
            }

            return new Right<>(cellChosen);
        } catch (NumberFormatException exception) {
            return new Left<>("Insira apenas números inteiros");
        }
    }

    private Either<String, Character> mark(Cell cell, char symbol) {
        if (board.containsKey(cell)) {
            return new Left<>("Posição já ocupada!");
        }

        return new Right<>(board.put(cell, symbol));
    }

    private boolean checkIfHasWinner(char symbol) {
        return WIN_PLAYS.stream().anyMatch(combo -> combo.stream().allMatch(
                cell -> board.getOrDefault(cell, ' ') == symbol));
    }
}
