package com.ebcont.marsrover;

import java.util.Collection;
import java.util.Collections;

public class Robot {

    private final ReportingModule reportingModule;
    private Position currentPosition;
    private Direction currentDirection;
    private Collection<Position> obstacles;

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

    public void executeCommands(char[] commands) {
        for (char command : commands) {
            switch (command) {
                case 'f':
                    currentPosition = handleObstacle(currentPosition.forward(currentDirection));
                    break;
                case 'b':
                    currentPosition = handleObstacle(currentPosition.backward(currentDirection));
                    break;
                case 'l':
                    currentDirection = currentDirection.left();
                    break;
                case 'r':
                    currentDirection = currentDirection.right();
                    break;
                default:
                    throw new UnsupportedOperationException("Unsupported command " + command);
            }
        }

        reportingModule.reportPosition(currentPosition);
        reportingModule.reportDirection(currentDirection);
    }

    private Position handleObstacle(Position position) {
        if (hasObstacle(position)) {
            reportingModule.reportObstacle(position);
            return currentPosition;
        }
        return position;
    }

    private boolean hasObstacle(Position position) {
        return this.obstacles.contains(position);
    }
}
