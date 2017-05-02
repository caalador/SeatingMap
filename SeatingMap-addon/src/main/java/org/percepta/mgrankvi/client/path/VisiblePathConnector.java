package org.percepta.mgrankvi.client.path;

import java.util.List;

import org.percepta.mgrankvi.VisiblePath;

import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.client.annotations.OnStateChange;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.client.ui.AbstractHasComponentsConnector;
import com.vaadin.shared.ui.Connect;

/**
 * Created by Mikael on 18/12/16.
 */
@Connect(VisiblePath.class)
public class VisiblePathConnector extends AbstractComponentConnector {

    @Override
    public VisiblePathWidget getWidget() {
        return (VisiblePathWidget) super.getWidget();
    }

    @Override
    public VisiblePathState getState() {
        return (VisiblePathState) super.getState();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

        getWidget().setLines(getState().lines);
    }

}
