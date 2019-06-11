package com.ebcont.marsrover;

import java.util.Objects;

public class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position forward(Direction direction) {
        switch (direction) {
            case NORTH:
                return new Position(x, y + 1);
            case WEST:
                return new Position(x - 1, y);
            case SOUTH:
                return new Position(x, y - 1);
            case EAST:
                return new Position(x + 1, y);
            default:
                throw new IllegalArgumentException("unknown forward direction: " + direction);
        }
    }

    public Position backward(Direction direction) {
        switch(direction) {
            case NORTH:
                return new Position(x, y - 1);
            case EAST:
                return new Position(x - 1, y);
            case SOUTH:
                return new Position(x, y + 1);
            case WEST:
                return new Position(x + 1, y);
            default:
                throw new IllegalArgumentException("unknown backward direction: " + direction);
        }
    }

    @Override
    public String toString() {
        return "{" + x + ", " + y + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
