package org.percepta.mgrankvi;

import java.util.List;
import java.util.UUID;

import org.percepta.mgrankvi.client.geometry.Line;
import org.percepta.mgrankvi.client.geometry.Point;
import org.percepta.mgrankvi.client.helpers.Extents;
import org.percepta.mgrankvi.client.table.TableState;

/**
 * Created by Mikael on 04/01/17.
 */
public class Table extends AbstractComoponents {

    private int closestNodeId;
    private Extents extents;
    private Point center;

    public Table() {
        setId(UUID.randomUUID().toString());
    }

    public Table(List<Line> lines) {
        this();
        addLines(lines);
    }

    public void setName(String name) {
        getState().name = name;
    }

    public String getName() {
        return getState(false).name;
    }

    public void setImageUrl(String imageUrl) {
        getState().imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return getState(false).imageUrl;
    }

    public void addLines(List<Line> lines) {
        extents = new Extents(lines);
        center = new Point(
                (extents.getMaxX() - extents.getMinX()) / 2 + extents.getMinX(),
                (extents.getMaxY() - extents.getMinY()) / 2
                        + extents.getMinY());
        getState().lines = lines;
    }

    public void setId(String id) {
        getState().id = id;
    }

    public void setNameVisibility(boolean nameVisibility) {
        getState().nameVisibility = nameVisibility;
    }

    public boolean getNameVisibility() {
        return getState(false).nameVisibility;
    }

    public void setClosestNodeId(int nodeId) {
        closestNodeId = nodeId;
    }

    public int getNodeId() {
        return closestNodeId;
    }

    @Override
    protected TableState getState() {
        return (TableState) super.getState();
    }

    @Override
    protected TableState getState(boolean markAsDirty) {
        return (TableState) super.getState(markAsDirty);
    }

    public Point getCenter() {
        return center;
    }
}
