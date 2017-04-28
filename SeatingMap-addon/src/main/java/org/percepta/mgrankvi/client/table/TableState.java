package org.percepta.mgrankvi.client.table;

import java.util.LinkedList;
import java.util.List;

import org.percepta.mgrankvi.client.geometry.Line;

import com.vaadin.shared.ui.AbstractComponentContainerState;

/**
 * Created by Mikael on 18/12/16.
 */
public class TableState extends AbstractComponentContainerState {
    public List<Line> lines = new LinkedList<>();

    public String name = "";

    public String imageUrl = null;

    public boolean nameVisibility = false;
}
