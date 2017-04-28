package org.percepta.mgrankvi.client.floor;

import com.vaadin.shared.AbstractComponentState;
import com.vaadin.shared.ui.AbstractComponentContainerState;

import org.percepta.mgrankvi.client.geometry.Line;
import org.percepta.mgrankvi.client.geometry.Point;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Mikael Grankvist - Vaadin Ltd
 */
public class FloorMapState extends AbstractComponentContainerState {

  public int level = 0;

  public List<Line> lines = new LinkedList<Line>();
  
  public Point initial = new Point(0,0);
}