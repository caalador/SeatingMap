package org.percepta.mgrankvi;

import com.google.common.collect.Lists;
import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.HasComponents;
import org.percepta.mgrankvi.client.floor.FloorMapState;
import org.percepta.mgrankvi.client.geometry.Line;
import org.percepta.mgrankvi.client.geometry.Point;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @author Mikael Grankvist - Vaadin Ltd
 */
public class FloorMap extends AbstractComponentContainer implements HasComponents {


  List<Component> children = Lists.newLinkedList();

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

  @Override
  public void addComponent(final Component c) {
    if (c == null) {
      return;
    }
    children.add(c);
    super.addComponent(c);
  }

  @Override
  public void removeComponent(final Component c) {
    if (c == null) {
      return;
    }
    children.remove(c);
    super.removeComponent(c);
    markAsDirty();
  }

  @Override
  public void replaceComponent(final Component oldComponent, final Component newComponent) {
    if (newComponent == null) {
      return;
    }
    final int index = children.indexOf(oldComponent);
    if (index != -1) {
      children.remove(index);
      children.add(index, newComponent);
      fireComponentDetachEvent(oldComponent);
      fireComponentAttachEvent(newComponent);
    }
  }

  @Override
  public int getComponentCount() {
    return children.size();
  }

  @Override
  public Iterator<Component> iterator() {
    return children.iterator();
  }

  /**
   * Get child component with id if one has been connected to component
   *
   * @param id String component id
   * @return Component or Null if not found.
   */
  public Component getById(String id) {
    for (Component c : children) {
      if (c.getId() != null && c.getId().equals(id)) {
        return c;
      }
    }
    return null;
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
    addLines(lines);
    // TODO: save room lines for hover information
  }
}
