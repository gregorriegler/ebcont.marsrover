package com.ebcont.marsrover;

public class Robot {

    private final ReportingModule reportingModule;
    private Position currentPosition;
    private Direction currentDirection;

    public Robot(ReportingModule reportingModule) {
        this.reportingModule = reportingModule;
    }

    public void land() {
        currentPosition = new Position(0, 0);
        reportingModule.reportPosition(currentPosition);

        currentDirection = Direction.NORTH;
        reportingModule.reportDirection(currentDirection);
    }

    public void executeCommands(char[] commands) {
        for (char command : commands) {
            switch (command) {
                case 'f':
                    currentPosition = currentPosition.forward(currentDirection);
                    break;
                case 'b':
                    currentPosition = currentPosition.backward(currentDirection);
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
}
