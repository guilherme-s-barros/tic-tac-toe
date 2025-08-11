package io.github.guilhermesbarros.tictactoe.exceptions;

public final class Right<L, R> implements Either<L, R> {
    private final R value;

    public Right(R value) {
        this.value = value;
    }

    @Override
    public boolean isLeft() {
        return false;
    }

    @Override
    public boolean isRight() {
        return true;
    }

    @Override
    public L getLeft() {
        return null;
    }

    @Override
    public R getRight() {
        return value;
    }
}
