package org.percepta.mgrankvi.client.floor;

import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractHasComponentsConnector;
import com.vaadin.shared.ui.Connect;
import org.percepta.mgrankvi.FloorMap;

import java.util.List;

/**
 * @author Mikael Grankvist - Vaadin Ltd
 */
@Connect(FloorMap.class)
public class FloorMapConnector extends AbstractHasComponentsConnector {

  @Override
  public RoomContainer getWidget() {
    return (RoomContainer) super.getWidget();
  };

  @Override
  public FloorMapState getState() {
    return (FloorMapState) super.getState();
  }

  @Override
  public void onStateChanged(StateChangeEvent stateChangeEvent) {
    super.onStateChanged(stateChangeEvent);

    getWidget().setLines(getState().lines);
    getWidget().setPosition(getState().initial);
  }

  @Override
  public void onConnectorHierarchyChange(ConnectorHierarchyChangeEvent connectorHierarchyChangeEvent) {
    final List<ComponentConnector> children = getChildComponents();
    final RoomContainer widget = getWidget();
    widget.clear();
    for (final ComponentConnector connector : children) {
      widget.add(connector.getWidget());
    }
  }

  @Override
  public void updateCaption(ComponentConnector componentConnector) {

  }
}
