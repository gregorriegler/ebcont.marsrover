package com.ebcont.marsrover;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collection;

import org.assertj.core.util.Lists;
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
    void robotFacesNorthMovesForward() {
        robot.land();

        robot.executeCommands(new char[]{'f'});

        Position newPosition = new Position(0, 1);
        verify(reportingModule).reportPosition(newPosition);
    }

    @Test
    void robotFacesNorthMovesForwardTwice() {
        robot.land();

        robot.executeCommands(new char[]{'f', 'f'});

        Position newPosition = new Position(0, 2);
        verify(reportingModule).reportPosition(newPosition);
    }

    @Test
    void robotFacesWestMovesForward() {
        robot.land();

        robot.executeCommands(new char[]{'l', 'f'});

        Position expectedPosition = new Position(-1, 0);
        verify(reportingModule).reportPosition(expectedPosition);
    }

    @Test
    void robotFacesSouthMovesForward() {
        robot.land();

        robot.executeCommands(new char[]{'l', 'l', 'f'});

        Position expectedPosition = new Position(0, -1);
        verify(reportingModule).reportPosition(expectedPosition);
    }

    @Test
    void robotFacesEastMovesForward() {
        robot.land();

        robot.executeCommands(new char[]{'r', 'f'});

        Position expectedPosition = new Position(1, 0);
        verify(reportingModule).reportPosition(expectedPosition);
    }

    @Test
    void robotFacesNorthMovesBackward() {
        robot.land();

        robot.executeCommands(new char[]{'b'});

        Position newPosition = new Position(0, -1);
        verify(reportingModule).reportPosition(newPosition);
    }

    @Test
    void robotFacesEastMovesBackward() {
        robot.land();

        robot.executeCommands(new char[]{'r', 'b'});

        Position expectedPosition = new Position(-1, 0);
        verify(reportingModule).reportPosition(expectedPosition);
    }

    @Test
    void robotFacesSouthMovesBackward() {
        robot.land();

        robot.executeCommands(new char[]{'r', 'r', 'b'});

        Position expectedPosition = new Position(0, 1);
        verify(reportingModule).reportPosition(expectedPosition);
    }

    @Test
    void robotFacesWestMovesBackward() {
        robot.land();

        robot.executeCommands(new char[]{'l', 'b'});

        Position expectedPosition = new Position(1, 0);
        verify(reportingModule).reportPosition(expectedPosition);
    }

    @Test
    void robotDetectsObstacleIfMovingForward(){
        Collection<Position> obstacles = Lists.newArrayList(new Position(0, 1));
        robot.land(obstacles);

        robot.executeCommands(new char[] {'f'});

        verify(reportingModule).reportObstacle(new Position(0, 1));
    }
}
