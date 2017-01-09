package org.percepta.mgrankvi;

import com.google.common.collect.Maps;
import org.percepta.mgrankvi.client.geometry.Line;
import org.percepta.mgrankvi.client.map.SeatingMapServerRpc;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Mikael Grankvist - Vaadin Ltd
 */
public class SeatingMap extends AbstractComoponents {


    Map<Integer, FloorMap> floors = Maps.newHashMap();

    Integer visibleFloor;

    public SeatingMap() {
        registerRpc(new SeatingMapServerRpc() {
            @Override
            public void findByName(String name) {
                SearchResult singleByName = getSingleByName(name);
//                singleByName.table.
            }

            @Override
            public void setVisibleFloor(int floor) {
                visibleFloor = floor;
            }

            @Override
            public void itemClick(String roomId, String tableId) {
                System.out.println("Got item selection!");
                Room clickedRoom = floors.get(visibleFloor).getRoomById(roomId);
                Table clickedTable = clickedRoom.getTableById(tableId);

                if (clickedTable != null) {
                    clickedTable.setNameVisibility(!clickedTable.getNameVisibility());
                }

                System.out.println(clickedRoom + " :: " + clickedTable);
            }
        });
    }

    public Room addRoom(int floor, List<Line> lines) {
        FloorMap map = getFloor(floor);
        return map.addRoom(lines);
    }

    public void addLines(int floor, List<Line> lines) {
        FloorMap map = getFloor(floor);
        map.addLines(lines);

    }

    private FloorMap getFloor(int floor) {
        FloorMap map;
        if (floors.containsKey(floor)) {
            map = floors.get(floor);
        } else {
            map = new FloorMap(floor);
            floors.put(floor, map);
            addComponent(map);
            if (visibleFloor == null) {
                visibleFloor = floor;
            }
        }
        return map;
    }

    /**
     * Returns first match for name
     *
     * @param name Name to search for
     * @return First match or null
     */
    private SearchResult getSingleByName(String name) {
        SearchResult result = new SearchResult();
        for (FloorMap floor : floors.values()) {
            for (Room room : floor.getRooms()) {
                for (Table table : room.getTables()) {
                    if (table.getName().toLowerCase().contains(name.toLowerCase())) {
                        result.setFloor(floor);
                        result.setRoom(room);
                        result.setTable(table);
                        return result;
                    }
                }
            }
        }

        return null;
    }

    /**
     * Get the first match for name
     *
     * @param name Name to search for
     * @return First match or empty optional
     */
    public Optional<SearchResult> findByName(String name) {
        return Optional.ofNullable(getSingleByName(name));
    }

    /**
     * Get all matches for searchString
     * @param name Name to search for
     * @return List of matches.
     */
    public List<SearchResult> getMatchesForName(String name) {
        List<SearchResult> result = new LinkedList<>();

        for (FloorMap floor : floors.values()) {
            for (Room room : floor.getRooms()) {
                for (Table table : room.getTables()) {
                    if (table.getName().toLowerCase().contains(name.toLowerCase())) {
                        SearchResult searchResult = new SearchResult();
                        searchResult.setFloor(floor);
                        searchResult.setRoom(room);
                        searchResult.setTable(table);
                        result.add(searchResult);
                    }
                }
            }
        }

        return result;
    }
}
