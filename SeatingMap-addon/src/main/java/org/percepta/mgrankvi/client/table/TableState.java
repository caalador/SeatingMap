package org.percepta.mgrankvi.client.table;

import com.vaadin.shared.AbstractComponentState;
import org.percepta.mgrankvi.client.geometry.Line;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Mikael on 18/12/16.
 */
public class TableState extends AbstractComponentState {
    public List<Line> lines = new LinkedList<Line>();

    public String name = "";
}
