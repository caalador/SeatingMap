package org.percepta.mgrankvi.client.helpers;

import org.percepta.mgrankvi.client.room.RoomWidget;
import org.percepta.mgrankvi.client.table.TableWidget;

/**
 * Created by Mikael on 05/01/17.
 */
public class Clicked {
    public RoomWidget clickedRoom;
    public TableWidget clickedTable;

    public boolean targetableClicked() {
        return clickedRoom != null || clickedTable != null;
    }

    public String getRoomId() {
        return clickedRoom == null ? null: clickedRoom.id;
    }

    public String getTableId() {
        return clickedTable == null ? null : clickedTable.id;
    }
}
