package org.percepta.mgrankvi.util;

import java.util.List;

import org.percepta.mgrankvi.client.geometry.Point;
import org.percepta.mgrankvi.path.Node;

/**
 * Interface for getting the nearest Node for given point.
 * 
 * This can be implemented and given to the SeatingMap if one wants to have a
 * better implementation than the default simple implementation.
 */
public interface NearestSearch {

    void setNodes(List<Node> nodes);

    Node getNearest(Point centerPoint);
}
