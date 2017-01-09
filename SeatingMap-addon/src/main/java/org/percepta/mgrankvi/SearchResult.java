package org.percepta.mgrankvi;

/**
 * Created by Mikael on 09/01/17.
 */
public class SearchResult {

    private Table table;
    private Room room;
    private FloorMap floor;

    public SearchResult() {
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public FloorMap getFloor() {
        return floor;
    }

    public void setFloor(FloorMap floor) {
        this.floor = floor;
    }
}
