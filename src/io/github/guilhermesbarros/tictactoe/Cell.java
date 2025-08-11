package io.github.guilhermesbarros.tictactoe;

import io.github.guilhermesbarros.tictactoe.exceptions.Either;
import io.github.guilhermesbarros.tictactoe.exceptions.Left;
import io.github.guilhermesbarros.tictactoe.exceptions.Right;

public enum Cell {
    A1(0, 0), A2(0, 1), A3(0, 2),
    B1(1, 0), B2(1, 1), B3(1, 2),
    C1(2, 0), C2(2, 1), C3(2, 2);

    public final int row;
    public final int col;

    Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public static Either<String, Cell> getCell(int row,
                                               int col) {
        for (Cell p : Cell.values()) {
            if (p.row == row && p.col == col) {
                return new Right<>(p);
            }
        }

        return new Left<>("Célula inválida");
    }
}
