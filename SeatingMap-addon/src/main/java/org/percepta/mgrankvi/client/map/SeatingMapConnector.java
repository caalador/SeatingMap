package org.percepta.mgrankvi.client.map;

import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.client.ui.AbstractHasComponentsConnector;
import com.vaadin.client.ui.SimpleManagedLayout;
import com.vaadin.shared.ui.Connect;
import org.percepta.mgrankvi.SeatingMap;
import org.percepta.mgrankvi.client.floor.RoomContainer;

import java.util.List;

/**
 * @author Mikael Grankvist - Vaadin Ltd
 */
@Connect(SeatingMap.class)
public class SeatingMapConnector extends AbstractHasComponentsConnector implements SimpleManagedLayout, SeatingMapWidget.ActionListener {

    @Override
    protected SeatingMapWidget createWidget() {
        return new SeatingMapWidget(this);
    }

    @Override
    public SeatingMapWidget getWidget() {
        return (SeatingMapWidget) super.getWidget();
    }

    @Override
    public SeatingMapState getState() {
        return (SeatingMapState) super.getState();
    }

    @Override
    public void onConnectorHierarchyChange(ConnectorHierarchyChangeEvent connectorHierarchyChangeEvent) {
        final List<ComponentConnector> children = getChildComponents();
        final SeatingMapWidget widget = getWidget();
        widget.clear();
        for (final ComponentConnector connector : children) {
            widget.add(connector.getWidget());
        }
        for (final ComponentConnector child : connectorHierarchyChangeEvent.getOldChildren()) {
            child.getWidget().removeFromParent();
        }
    }

    @Override
    public void updateCaption(ComponentConnector componentConnector) {

    }

    @Override
    public void layout() {
        final SeatingMapWidget widget = getWidget();
        if (!widget.floors.isEmpty()) {
            if (widget.selectedFloor == null) {
                widget.setFloor(widget.floors.iterator().next());
            } else if (widget.selectedFloor != null) {
                for (final RoomContainer floor : widget.floors) {
                    if (floor.getId().equals(widget.selectedFloor.getId())) {
                        widget.setFloor(floor);
                        break;
                    }
                }
            }
        }
        widget.repaint();
    }

    @Override
    public void find(String searchString) {
        getRpcProxy(SeatingMapServerRpc.class).findByName(searchString);
    }
}
