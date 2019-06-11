package com.ebcont.marsrover;

import com.google.common.collect.ImmutableMap;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class Robot {

    private final ReportingModule reportingModule;
    private Position currentPosition;
    private Direction currentDirection;
    private Collection<Position> obstacles;
    private final Map<Character, Command> commands = new ImmutableMap.Builder<Character, Command>()
        .put('f', robot -> robot.moveForward())
        .put('b', robot -> robot.moveBackward())
        .put('l', robot -> {robot.turnLeft(); return true;})
        .put('r', robot -> {robot.turnRight(); return true;})
        .build();

    public Robot(ReportingModule reportingModule) {
        this.reportingModule = reportingModule;
    }

    public void land() {
        land(Collections.emptyList());
    }

    public void land(Collection<Position> obstacles) {
        currentPosition = new Position(0, 0);
        reportingModule.reportPosition(currentPosition);

        currentDirection = Direction.NORTH;
        reportingModule.reportDirection(currentDirection);

        this.obstacles = obstacles;
    }

    public void executeCommands(char[] characterSequence) {
        for (char character : characterSequence) {
            Command command = commandByCharacter(character);
            boolean shouldContinue = command.execute(this);
            if (!shouldContinue) break;
        }

        reportingModule.reportPosition(currentPosition);
        reportingModule.reportDirection(currentDirection);
    }

    private Command commandByCharacter(char command) {
        if (!this.commands.containsKey(command)) {
            throw new UnsupportedOperationException("Unsupported command " + command);
        }
        return this.commands.get(command);
    }

    private void turnLeft() {
        currentDirection = currentDirection.left();
    }

    private void turnRight() {
        currentDirection = currentDirection.right();
    }

    private boolean moveForward() {
        Position position = currentPosition.forward(currentDirection);
        return moveTo(position);
    }

    private boolean moveBackward() {
        Position position = currentPosition.backward(currentDirection);
        return moveTo(position);
    }

    private boolean moveTo(Position position) {
        if (scanForObstacle(position)) return false;

        currentPosition = position;
        return true;
    }

    private boolean scanForObstacle(Position position) {
        if (hasObstacle(position)) {
            reportingModule.reportObstacle(position);
            return true;
        }
        return false;
    }

    private boolean hasObstacle(Position position) {
        return this.obstacles.contains(position);
    }

    @FunctionalInterface
    private interface Command {

        boolean execute(Robot robot);
    }
}
