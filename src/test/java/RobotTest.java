import static org.mockito.Mockito.verify;

import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RobotTest {

    @Test
    void landingRobotReportsInitialPosition() {
        ReportingModule reportingModule = Mockito.mock(ReportingModule.class);
        Robot robot = new Robot(reportingModule);

        robot.land();

        Position initialPosition = new Position(0, 0);
        verify(reportingModule).reportPosition(initialPosition);
    }

    @Test
    public void landingRobotReportsItsDirection() {
        ReportingModule reportingModule = Mockito.mock(ReportingModule.class);
        Robot robot = new Robot(reportingModule);

        robot.land();

        Direction initialDirection = Direction.NORTH;
        verify(reportingModule).reportDirection(initialDirection);
    }

    public enum Direction {
        NORTH
    }

    public static class Robot {

        private final ReportingModule reportingModule;

        public Robot(ReportingModule reportingModule) {
            this.reportingModule = reportingModule;
        }

        public void land() {
            Position currentPosition = new Position(0, 0);
            reportingModule.reportPosition(currentPosition);

            Direction currentDirection = Direction.NORTH;
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
