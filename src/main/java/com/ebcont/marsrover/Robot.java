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
        .put('f', new MoveForwardCommmand())
        .put('b', new MoveBackwardCommmand())
        .put('l', new TurnLeftCommmand())
        .put('r', new TurnRightCommmand())
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
        try {
            for (char character : characterSequence) {
                commandByCharacter(character).execute(this);
            }
        } catch (ObstacleException e) {
            reportingModule.reportObstacle(e.getPosition());
        } finally {
            reportingModule.reportPosition(currentPosition);
            reportingModule.reportDirection(currentDirection);
        }
    }

    private Command commandByCharacter(char command) {
        if (!this.commands.containsKey(command)) {
            throw new UnsupportedOperationException("Unsupported command " + command);
        }
        return this.commands.get(command);
    }

    private void moveForward() throws ObstacleException {
        currentPosition = handleObstacle(currentPosition.forward(currentDirection));
    }

    private void moveBackward() throws ObstacleException {
        currentPosition = handleObstacle(currentPosition.backward(currentDirection));
    }

    private void turnLeft() {
        currentDirection = currentDirection.left();
    }

    private void turnRight() {
        currentDirection = currentDirection.right();
    }

    private Position handleObstacle(Position position) throws ObstacleException {
        if (hasObstacle(position)) {
            throw new ObstacleException(position);
        }
        return position;
    }

    private boolean hasObstacle(Position position) {
        return this.obstacles.contains(position);
    }

    private static class ObstacleException extends Exception {
        private final Position position;

        public ObstacleException(Position position) {
            this.position = position;
        }

        public Position getPosition() {
            return position;
        }
    }

    private interface Command {
        void execute(Robot robot) throws ObstacleException;
    }

    private class MoveForwardCommmand implements Command {

        @Override
        public void execute(Robot robot) throws ObstacleException {
            robot.moveForward();
        }
    }

    private class MoveBackwardCommmand implements Command {

        @Override
        public void execute(Robot robot) throws ObstacleException {
            robot.moveBackward();
        }
    }

    private class TurnLeftCommmand implements Command {

        @Override
        public void execute(Robot robot) {
            robot.turnLeft();
        }
    }

    private class TurnRightCommmand implements Command {

        @Override
        public void execute(Robot robot) {
            robot.turnRight();
        }
    }


}
