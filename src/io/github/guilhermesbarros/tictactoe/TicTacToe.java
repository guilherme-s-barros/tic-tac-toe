package io.github.guilhermesbarros.tictactoe;

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

            var response = scanner.nextLine();

            ClearConsole.execute();

            try {
                var cellChosen =
                        Arrays.stream(response.replaceAll("\\s", "").split(","))
                              .mapToInt(Integer::parseInt).toArray();

                var cell = Cell.getCell(cellChosen[0] - 1, cellChosen[1] - 1);

                mark(cell, currentPlayer);
                hasWinner = checkIfHasWinner(currentPlayer);

                if (hasWinner || board.size() >= 9) {
                    gameOver = true;
                } else {
                    currentPlayer = currentPlayer == 'X' ? 'O' : 'X';
                }
            } catch (NumberFormatException exception) {
                System.out.println("Insira apenas números inteiros");
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            } catch (ArrayIndexOutOfBoundsException exception) {
                System.out.println("Célula inválida");
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

    public void drawBoard() {
        System.out.println(" TIC TAC TOE ");

        for (int row = 0; row < 3; row++) {
            System.out.print(" ");

            for (int col = 0; col < 3; col++) {
                Cell cell = Cell.getCell(row, col);
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

    public void mark(Cell cell, char symbol) {
        if (board.containsKey(cell)) {
            throw new IllegalArgumentException("Posição já ocupada!");
        }

        board.put(cell, symbol);
    }

    public boolean checkIfHasWinner(char symbol) {
        return WIN_PLAYS.stream().anyMatch(
                combo -> combo.stream().allMatch(
                        cell -> board.getOrDefault(cell, ' ') == symbol));
    }
}
