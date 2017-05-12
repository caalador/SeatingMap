package org.percepta.mgrankvi;

/**
 * Created by Mikael on 12/05/17.
 */
public class MapSelectionEvent {
    private final Room room;
    private final Table table;

    public MapSelectionEvent(Room room, Table table) {
        this.room = room;
        this.table = table;
    }

    public boolean isTableSelection() {
        return table != null;
    }

    public Room getRoom() {
        return room;
    }

    public Table getTable() {
        return table;
    }
}
