package org.percepta.mgrankvi.client.room;

import com.vaadin.shared.AbstractComponentState;
import org.percepta.mgrankvi.client.geometry.Line;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Mikael on 18/12/16.
 */
public class RoomState extends AbstractComponentState {
    public List<Line> lines = new LinkedList<Line>();

}
