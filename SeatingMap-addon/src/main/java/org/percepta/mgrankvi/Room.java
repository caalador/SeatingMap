package org.percepta.mgrankvi;

import org.percepta.mgrankvi.client.geometry.Line;
import org.percepta.mgrankvi.client.room.RoomState;

import java.util.List;

/**
 * Created by Mikael on 18/12/16.
 */
public class Room extends AbstractComoponents {

    public void addLines(List<Line> lines) {
        getState().lines = lines;
    }

    @Override
    protected RoomState getState() {
        return (RoomState) super.getState();
    }
}
