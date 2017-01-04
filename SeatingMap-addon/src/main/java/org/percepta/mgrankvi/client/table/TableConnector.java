package org.percepta.mgrankvi.client.table;

import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractHasComponentsConnector;
import com.vaadin.shared.ui.Connect;
import org.percepta.mgrankvi.Room;
import org.percepta.mgrankvi.Table;

import java.util.List;

/**
 * Created by Mikael on 18/12/16.
 */
@Connect(Table.class)
public class TableConnector extends AbstractHasComponentsConnector {

    @Override
    public TableWidget getWidget() {
        return (TableWidget) super.getWidget();
    }

    @Override
    public TableState getState() {
        return (TableState) super.getState();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

        getWidget().setLines(getState().lines);
        getWidget().nameString = getState().name;
        getWidget().imageUrl = getState().imageUrl;
    }

    @Override
    public void onConnectorHierarchyChange(ConnectorHierarchyChangeEvent connectorHierarchyChangeEvent) {
        final List<ComponentConnector> children = getChildComponents();
        final TableWidget widget = getWidget();
//        widget.clear();
//        for (final ComponentConnector connector : children) {
//            widget.add(connector.getWidget());
//        }
    }

    @Override
    public void updateCaption(ComponentConnector componentConnector) {

    }
}
