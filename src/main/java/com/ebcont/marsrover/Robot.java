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
        try {
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

        } catch (ObstacleException e) {
            reportingModule.reportObstacle(e.getPosition());
        } finally {
            reportingModule.reportPosition(currentPosition);
            reportingModule.reportDirection(currentDirection);
        }
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
}
