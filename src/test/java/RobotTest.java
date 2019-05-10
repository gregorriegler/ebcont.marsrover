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

        robot.executeCommands(new char [] {'f'});

        Position newPosition = new Position(0, 1);
        verify(reportingModule).reportPosition(newPosition);
    }

    @Test
    void moveRobotForwardTwiceReportsNewPosition() {
        robot.land();

        robot.executeCommands(new char [] {'f'});
        robot.executeCommands(new char [] {'f'});

        Position newPosition = new Position(0, 2);
        verify(reportingModule).reportPosition(newPosition);
    }

    @Test
    void moveRobotBackwardReportsNewPosition() {
        robot.land();

        robot.executeCommands(new char [] {'b'});

        Position newPosition = new Position(0, -1);
        verify(reportingModule).reportPosition(newPosition);
    }

    @Test
    void robotTurnsLeftAndReportsNewDirection() {
        robot.land();

        robot.executeCommands(new char [] {'l'});

        Direction newDirection = Direction.WEST;
        verify(reportingModule).reportDirection(newDirection);
    }

    public enum Direction {
        NORTH,
        WEST
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
            if(commands[0] == 'f'){
                currentPosition = currentPosition.northOf();
            }else if(commands[0] == 'b'){
                currentPosition = currentPosition.southOf();
            } else {
                currentDirection = Direction.WEST;
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

        public Position northOf() {
            return new Position(x, y + 1);
        }

        public Position southOf() {
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
