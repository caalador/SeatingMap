package org.percepta.mgrankvi;

import com.google.common.collect.Maps;
import org.percepta.mgrankvi.client.geometry.Line;

import java.util.List;
import java.util.Map;

/**
 * @author Mikael Grankvist - Vaadin Ltd
 */
public class SeatingMap extends AbstractComoponents {


    Map<Integer, FloorMap> floors = Maps.newHashMap();

    public void addRoom(int floor, List<Line> lines) {
        FloorMap map = getFloor(floor);
        map.addRoom(lines);
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
        }
        return map;
    }

}
