package org.percepta.mgrankvi.client.path;

import java.util.LinkedList;
import java.util.List;

import org.percepta.mgrankvi.client.geometry.Line;

import com.vaadin.shared.AbstractComponentState;
import com.vaadin.shared.ui.AbstractComponentContainerState;

/**
 * Created by Mikael on 18/12/16.
 */
public class VisiblePathState extends AbstractComponentState {
    public List<Line> lines = new LinkedList<>();
}
