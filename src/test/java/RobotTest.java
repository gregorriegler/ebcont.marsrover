import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RobotTest {

    private ReportingModule reportingModule;
    private Robot robot;

    @BeforeEach
    void setUp() {
        reportingModule = Mockito.mock(ReportingModule.class);
        robot = new Robot(reportingModule);
    }

    @Test
    void landingRobotReportsInitialPosition() {
        robot.land();

        Position initialPosition = new Position(0, 0);
        verify(reportingModule).reportPosition(initialPosition);
    }

    @Test
    void landingRobotReportsItsDirection() {
        robot.land();

        Direction initialDirection = Direction.NORTH;
        verify(reportingModule).reportDirection(initialDirection);
    }

    @Test
    void moveRobotForwardReportsNewPosition() {
        robot.land();

        robot.executeCommands(new char[]{'f'});

        Position newPosition = new Position(0, 1);
        verify(reportingModule).reportPosition(newPosition);
    }

    @Test
    void moveRobotForwardTwiceReportsNewPosition() {
        robot.land();

        robot.executeCommands(new char[]{'f', 'f'});

        Position newPosition = new Position(0, 2);
        verify(reportingModule).reportPosition(newPosition);
    }

    @Test
    void moveRobotBackwardReportsNewPosition() {
        robot.land();

        robot.executeCommands(new char[]{'b'});

        Position newPosition = new Position(0, -1);
        verify(reportingModule).reportPosition(newPosition);
    }

    @Test
    void robotTurnsLeftAndReportsNewDirection() {
        robot.land();

        robot.executeCommands(new char[]{'l'});

        Direction newDirection = Direction.WEST;
        verify(reportingModule).reportDirection(newDirection);
    }

    @Test
    void robotTurnsLeftAroundAndReportsNewDirection() {
        robot.land();

        robot.executeCommands(new char[]{'l', 'l', 'l', 'l'});

        Direction newDirection = Direction.NORTH;
        verify(reportingModule, atLeast(2)).reportDirection(newDirection);
    }

    @Test
    void robotTurnsRightAndReportsNewDirection() {
        robot.land();

        robot.executeCommands(new char[]{'r'});

        Direction newDirection = Direction.EAST;
        verify(reportingModule).reportDirection(newDirection);
    }

    @Test
    void robotTurnsRightAroundAndReportsNewDirection() {
        robot.land();

        robot.executeCommands(new char[]{'r', 'r', 'r', 'r'});

        Direction newDirection = Direction.NORTH;
        verify(reportingModule, atLeast(2)).reportDirection(newDirection);
    }

    @Test
    void sendingInvalidCommandShouldThrowsAnException() {
        robot.land();

        assertThrows(UnsupportedOperationException.class,
            () -> robot.executeCommands(new char[]{'X'}));
    }

    @Test
    void robotMovesWest() {
        robot.land();

        robot.executeCommands(new char[]{'l', 'f'});

        Position expectedPosition = new Position(-1, 0);
        verify(reportingModule).reportPosition(expectedPosition);
    }

    @Test
    void robotMovesSouth() {
        robot.land();

        robot.executeCommands(new char[]{'l', 'l', 'f'});

        Position expectedPosition = new Position(0, -1);
        verify(reportingModule).reportPosition(expectedPosition);
    }

    @Test
    void robotMovesEast() {
        robot.land();

        robot.executeCommands(new char[]{'r', 'f'});

        Position expectedPosition = new Position(1, 0);
        verify(reportingModule).reportPosition(expectedPosition);
    }

    public enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST;

        public Direction left() {
            int left = ordinal() - 1;
            if (left < 0) {
                left = values().length - 1;
            }
            return Direction.values()[left];
        }

        public Direction right() {
            int right = ordinal() + 1;
            if (right >= values().length) {
                right = 0;
            }
            return Direction.values()[right];
        }
    }

    public static class Robot {

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

    public static class Position {
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
                    throw new IllegalArgumentException("unknown direction: " + direction);
            }

        }

        public Position backward(Direction direction) {
            return new Position(x, y - 1);
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
}
