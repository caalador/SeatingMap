package org.percepta.mgrankvi;

import org.percepta.mgrankvi.client.floor.FloorMapState;
import org.percepta.mgrankvi.client.geometry.Line;
import org.percepta.mgrankvi.client.geometry.Point;

import java.util.List;
import java.util.UUID;

/**
 * @author Mikael Grankvist - Vaadin Ltd
 */
public class FloorMap extends AbstractComoponents {


  public FloorMap() {
    getState().id = UUID.randomUUID().toString();
  }

  public FloorMap(int level) {
    this();
    getState().level = level;
  }

  /**
   * Set Floor map lines.
   * <p/>
   * Note! Clears all old lines.
   *
   * @param lines Lines to set to map
   */
  public void setLines(List<Line> lines) {
    getState().lines = lines;
  }

  /**
   * Add new map lines.
   *
   * @param lines Lines to add to current map
   */
  public void addLines(List<Line> lines) {
    getState().lines.addAll(lines);
  }

  public void setLevel(final int level) {
    getState().level = level;
  }

  @Override
  protected FloorMapState getState() {
    return (FloorMapState) super.getState();
  }

  /**
   * Set the position where this floor resides in relation to the origin point
   *
   * @param initialPosition Initial position Point
   */
  public void setInitialPosition(Point initialPosition) {
    getState().initial = initialPosition;
  }

  public void addRoom(List<Line> lines) {
    Room room = new Room();
    room.addLines(lines);
    addComponent(room);
  }
}
