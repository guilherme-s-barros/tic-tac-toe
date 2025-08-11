package io.github.guilhermesbarros.tictactoe.exceptions;

public sealed interface Either<L, R> permits Right, Left {
    boolean isLeft();

    boolean isRight();

    L getLeft();

    R getRight();
}
