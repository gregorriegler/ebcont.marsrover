package com.ebcont.marsrover;

public interface ReportingModule {

    void reportPosition(Position position);

    void reportDirection(Direction initialDirection);

    void reportObstacle(Position obstaclePosition);
}
