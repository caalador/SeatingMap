package org.percepta.mgrankvi.client.room;

import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractHasComponentsConnector;
import com.vaadin.shared.ui.Connect;
import org.percepta.mgrankvi.Room;

import java.util.List;

/**
 * Created by Mikael on 18/12/16.
 */
@Connect(Room.class)
public class RoomConnector extends AbstractHasComponentsConnector {

    @Override
    public RoomWidget getWidget() {
        return (RoomWidget) super.getWidget();
    }

    @Override
    public RoomState getState() {
        return (RoomState) super.getState();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

        getWidget().setLines(getState().lines);
        getWidget().id = getState().id;
    }

    @Override
    public void onConnectorHierarchyChange(ConnectorHierarchyChangeEvent connectorHierarchyChangeEvent) {
        final List<ComponentConnector> children = getChildComponents();
        final RoomWidget widget = getWidget();
        widget.clear();
        for (final ComponentConnector connector : children) {
            widget.add(connector.getWidget());
        }
    }

    @Override
    public void updateCaption(ComponentConnector componentConnector) {

    }
}
