import static org.mockito.Mockito.verify;

import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class HelloWorldTest {

    @Test
    void landingRobotReceivesCoordinatesAndConfirmsThePosition() {
        ReportingModule reportingModule = Mockito.mock(ReportingModule.class);


        Position initialPosition = new Position(0, 0);
        Robot robot = new Robot(reportingModule);
        robot.land();

        verify(reportingModule).reportPosition(initialPosition);
    }


    public static class Robot {

        private final ReportingModule reportingModule;

        public Robot(ReportingModule reportingModule) {
            this.reportingModule = reportingModule;
        }

        public void land() {
            Position currentPosition = new Position(0, 0);
            reportingModule.reportPosition(currentPosition);
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
