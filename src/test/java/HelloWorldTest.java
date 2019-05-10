import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class HelloWorldTest {

    @Test
    void landingRobotReceivesCoordinatesAndConfirmsThePosition() {
        ReportingModule reportingModule = Mockito.mock(ReportingModule.class);


        Position initialPosition = new Position(0, 0);
        Robot robot = new Robot();
        robot.land();

        verify(reportingModule).reportPosition(initialPosition);
    }


    public static class Robot {

        public void land() {
        }
    }

    public static class Position {
        private final int x;
        private final int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
