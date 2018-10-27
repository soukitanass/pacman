package ca.usherbrooke.pacman.model.objects;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import ca.usherbrooke.pacman.model.position.Position;

public class MockLevelFactory {

  // E = Empty
  // W = Wall
  //
  // -----
  // | W |
  // -----
  static public Level getMockLevelSingleWall() {
    Level mockLevel = mock(Level.class);
    when(mockLevel.getWidth()).thenReturn(1);
    when(mockLevel.getHeight()).thenReturn(1);
    when(mockLevel.isWall(new Position(0, 0))).thenReturn(true);
    return mockLevel;
  }

  // E = Empty
  // W = Wall
  //
  // -----
  // | E |
  // -----
  static public Level getMockLevelSingleEmpty() {
    Level mockLevel = mock(Level.class);
    when(mockLevel.getWidth()).thenReturn(1);
    when(mockLevel.getHeight()).thenReturn(1);
    when(mockLevel.isWall(new Position(0, 0))).thenReturn(false);
    return mockLevel;
  }

  // T = Tunnel
  //
  // T | T
  // -----
  // T | T
  static public Level getMockLevelTwoByTwoTunnels() {
    Level mockLevel = mock(Level.class);
    when(mockLevel.getWidth()).thenReturn(2);
    when(mockLevel.getHeight()).thenReturn(2);
    when(mockLevel.isTunnel(new Position(0, 0))).thenReturn(true);
    when(mockLevel.isTunnel(new Position(1, 0))).thenReturn(true);
    when(mockLevel.isTunnel(new Position(0, 1))).thenReturn(true);
    when(mockLevel.isTunnel(new Position(1, 1))).thenReturn(true);
    return mockLevel;
  }

  // E = Empty
  // W = Wall
  //
  // E | E
  // ------
  // E | E
  static public Level getMockLevelTwoByTwoEmpty() {
    Level mockLevel = mock(Level.class);
    when(mockLevel.getWidth()).thenReturn(2);
    when(mockLevel.getHeight()).thenReturn(2);
    when(mockLevel.isWall(new Position(0, 0))).thenReturn(false);
    when(mockLevel.isWall(new Position(1, 0))).thenReturn(false);
    when(mockLevel.isWall(new Position(0, 1))).thenReturn(false);
    when(mockLevel.isWall(new Position(1, 1))).thenReturn(false);
    return mockLevel;
  }

  // G = Ghost gate
  // R = Ghost room
  //
  // G | G | G
  // ---------
  // G | R | G
  // ---------
  // G | G | G
  static public Level getMockLevelSingleGhostRoomSurroundedByGhostGates() {
    Level mockLevel = mock(Level.class);
    when(mockLevel.getWidth()).thenReturn(3);
    when(mockLevel.getHeight()).thenReturn(3);
    when(mockLevel.isGhostGate(new Position(0, 0))).thenReturn(true);
    when(mockLevel.isGhostGate(new Position(1, 0))).thenReturn(true);
    when(mockLevel.isGhostGate(new Position(2, 0))).thenReturn(true);
    when(mockLevel.isGhostGate(new Position(0, 1))).thenReturn(true);
    when(mockLevel.isGhostRoom(new Position(1, 1))).thenReturn(true);
    when(mockLevel.isGhostGate(new Position(2, 1))).thenReturn(true);
    when(mockLevel.isGhostGate(new Position(0, 2))).thenReturn(true);
    when(mockLevel.isGhostGate(new Position(1, 2))).thenReturn(true);
    when(mockLevel.isGhostGate(new Position(2, 2))).thenReturn(true);
    return mockLevel;
  }

  // G = Ghost gate
  // R = Ghost room
  //
  // R | R | R
  // ---------
  // R | G | R
  // ---------
  // R | R | R
  static public Level getMockLevelSingleGhostGateSurroundedByGhostRooms() {
    Level mockLevel = mock(Level.class);
    when(mockLevel.getWidth()).thenReturn(3);
    when(mockLevel.getHeight()).thenReturn(3);
    when(mockLevel.isGhostRoom(new Position(0, 0))).thenReturn(true);
    when(mockLevel.isGhostRoom(new Position(1, 0))).thenReturn(true);
    when(mockLevel.isGhostRoom(new Position(2, 0))).thenReturn(true);
    when(mockLevel.isGhostRoom(new Position(0, 1))).thenReturn(true);
    when(mockLevel.isGhostGate(new Position(1, 1))).thenReturn(true);
    when(mockLevel.isGhostRoom(new Position(2, 1))).thenReturn(true);
    when(mockLevel.isGhostRoom(new Position(0, 2))).thenReturn(true);
    when(mockLevel.isGhostRoom(new Position(1, 2))).thenReturn(true);
    when(mockLevel.isGhostRoom(new Position(2, 2))).thenReturn(true);
    return mockLevel;
  }

  // G = Ghost gate
  // E = Empty
  //
  // E | E | E
  // ---------
  // E | G | E
  // ---------
  // E | E | E
  static public Level getMockLevelSingleGhostGateSurroundedByEmptiness() {
    Level mockLevel = mock(Level.class);
    when(mockLevel.getWidth()).thenReturn(3);
    when(mockLevel.getHeight()).thenReturn(3);
    when(mockLevel.isGhostGate(new Position(1, 1))).thenReturn(true);
    return mockLevel;
  }

  // G = Ghost gate
  // E = Empty
  //
  // G | G | G
  // ---------
  // G | E | G
  // ---------
  // G | G | G
  static public Level getMockLevelSingleEmptySurroundedByGhostGates() {
    Level mockLevel = mock(Level.class);
    when(mockLevel.getWidth()).thenReturn(3);
    when(mockLevel.getHeight()).thenReturn(3);
    when(mockLevel.isGhostGate(new Position(0, 0))).thenReturn(true);
    when(mockLevel.isGhostGate(new Position(1, 0))).thenReturn(true);
    when(mockLevel.isGhostGate(new Position(2, 0))).thenReturn(true);
    when(mockLevel.isGhostGate(new Position(0, 1))).thenReturn(true);
    when(mockLevel.isGhostGate(new Position(2, 1))).thenReturn(true);
    when(mockLevel.isGhostGate(new Position(0, 2))).thenReturn(true);
    when(mockLevel.isGhostGate(new Position(1, 2))).thenReturn(true);
    when(mockLevel.isGhostGate(new Position(2, 2))).thenReturn(true);
    return mockLevel;
  }

  // T = Tunnel
  // E = Empty
  //
  // E | E | E
  // ---------
  // E | T | E
  // ---------
  // E | E | E
  static public Level getMockLevelSingleTunnelSurroundedByEmptiness() {
    Level mockLevel = mock(Level.class);
    when(mockLevel.getWidth()).thenReturn(3);
    when(mockLevel.getHeight()).thenReturn(3);
    when(mockLevel.isTunnel(new Position(1, 1))).thenReturn(true);
    return mockLevel;
  }

  // T = Tunnel
  // E = Empty
  //
  // T | T | T
  // ---------
  // T | E | T
  // ---------
  // T | T | T
  static public Level getMockLevelSingleEmptySurroundedByTunnels() {
    Level mockLevel = mock(Level.class);
    when(mockLevel.getWidth()).thenReturn(3);
    when(mockLevel.getHeight()).thenReturn(3);
    when(mockLevel.isTunnel(new Position(0, 0))).thenReturn(true);
    when(mockLevel.isTunnel(new Position(1, 0))).thenReturn(true);
    when(mockLevel.isTunnel(new Position(2, 0))).thenReturn(true);
    when(mockLevel.isTunnel(new Position(0, 1))).thenReturn(true);
    when(mockLevel.isTunnel(new Position(2, 1))).thenReturn(true);
    when(mockLevel.isTunnel(new Position(0, 2))).thenReturn(true);
    when(mockLevel.isTunnel(new Position(1, 2))).thenReturn(true);
    when(mockLevel.isTunnel(new Position(2, 2))).thenReturn(true);
    return mockLevel;
  }

  // E = Empty
  //
  // E | E | E
  // ---------
  // E | E | E
  // ---------
  // E | E | E
  public static Level getMockLevelThreeByThreeEmpty() {
    Level mockLevel = mock(Level.class);
    when(mockLevel.getWidth()).thenReturn(3);
    when(mockLevel.getHeight()).thenReturn(3);
    return mockLevel;
  }

  // W = Wall
  //
  // W | W | W
  // ---------
  // W | W | W
  // ---------
  // W | W | W
  public static Level getMockLevelThreeByThreeWalls() {
    Level mockLevel = mock(Level.class);
    when(mockLevel.getWidth()).thenReturn(3);
    when(mockLevel.getHeight()).thenReturn(3);
    when(mockLevel.isWall(new Position(0, 0))).thenReturn(true);
    when(mockLevel.isWall(new Position(1, 0))).thenReturn(true);
    when(mockLevel.isWall(new Position(2, 0))).thenReturn(true);
    when(mockLevel.isWall(new Position(0, 1))).thenReturn(true);
    when(mockLevel.isWall(new Position(1, 1))).thenReturn(true);
    when(mockLevel.isWall(new Position(2, 1))).thenReturn(true);
    when(mockLevel.isWall(new Position(0, 2))).thenReturn(true);
    when(mockLevel.isWall(new Position(1, 2))).thenReturn(true);
    when(mockLevel.isWall(new Position(2, 2))).thenReturn(true);
    return mockLevel;
  }

  // T = Tunnel
  //
  // T | T | T
  // ---------
  // T | T | T
  // ---------
  // T | T | T
  public static Level getMockLevelThreeByThreeTunnels() {
    Level mockLevel = mock(Level.class);
    when(mockLevel.getWidth()).thenReturn(3);
    when(mockLevel.getHeight()).thenReturn(3);
    when(mockLevel.isTunnel(new Position(0, 0))).thenReturn(true);
    when(mockLevel.isTunnel(new Position(1, 0))).thenReturn(true);
    when(mockLevel.isTunnel(new Position(2, 0))).thenReturn(true);
    when(mockLevel.isTunnel(new Position(0, 1))).thenReturn(true);
    when(mockLevel.isTunnel(new Position(1, 1))).thenReturn(true);
    when(mockLevel.isTunnel(new Position(2, 1))).thenReturn(true);
    when(mockLevel.isTunnel(new Position(0, 2))).thenReturn(true);
    when(mockLevel.isTunnel(new Position(1, 2))).thenReturn(true);
    when(mockLevel.isTunnel(new Position(2, 2))).thenReturn(true);
    return mockLevel;
  }

  // G = Ghost gate
  //
  // G | G | G
  // ---------
  // G | G | G
  // ---------
  // G | G | G
  public static Level getMockLevelThreeByThreeGhostGates() {
    Level mockLevel = mock(Level.class);
    when(mockLevel.getWidth()).thenReturn(3);
    when(mockLevel.getHeight()).thenReturn(3);
    when(mockLevel.isGhostGate(new Position(0, 0))).thenReturn(true);
    when(mockLevel.isGhostGate(new Position(1, 0))).thenReturn(true);
    when(mockLevel.isGhostGate(new Position(2, 0))).thenReturn(true);
    when(mockLevel.isGhostGate(new Position(0, 1))).thenReturn(true);
    when(mockLevel.isGhostGate(new Position(1, 1))).thenReturn(true);
    when(mockLevel.isGhostGate(new Position(2, 1))).thenReturn(true);
    when(mockLevel.isGhostGate(new Position(0, 2))).thenReturn(true);
    when(mockLevel.isGhostGate(new Position(1, 2))).thenReturn(true);
    when(mockLevel.isGhostGate(new Position(2, 2))).thenReturn(true);
    return mockLevel;
  }
}
