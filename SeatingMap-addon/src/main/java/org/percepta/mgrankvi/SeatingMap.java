package org.percepta.mgrankvi;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.google.common.collect.Maps;
import org.percepta.mgrankvi.client.geometry.Line;
import org.percepta.mgrankvi.client.geometry.Point;
import org.percepta.mgrankvi.client.map.SeatingMapClientRpc;
import org.percepta.mgrankvi.client.map.SeatingMapServerRpc;
import org.percepta.mgrankvi.path.Dijkstra;
import org.percepta.mgrankvi.path.Node;

/**
 * @author Mikael Grankvist - Vaadin Ltd
 */
public class SeatingMap extends AbstractComoponents {

    Map<Integer, FloorMap> floors = Maps.newHashMap();
    Map<Integer, Node> paths = new HashMap<>();

    Integer visibleFloor;

    public SeatingMap() {
        registerRpc(new SeatingMapServerRpc() {
            @Override
            public void findByName(String name) {
                Optional<SearchResult> byName = SeatingMap.this
                        .findByName(name);
                if (byName.isPresent()) {
                    Table table = byName.get().getTable();
                    moveTableToView(table);
                    table.setNameVisibility(true);
                }
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
                    clickedTable.setNameVisibility(
                            !clickedTable.getNameVisibility());
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
     * @param name
     *            Name to search for
     * @return First match or null
     */
    private SearchResult getSingleByName(String name) {
        SearchResult result = new SearchResult();
        for (FloorMap floor : floors.values()) {
            for (Room room : floor.getRooms()) {
                for (Table table : room.getTables()) {
                    if (table.getName().toLowerCase()
                            .contains(name.toLowerCase())) {
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
     * @param name
     *            Name to search for
     * @return First match or empty optional
     */
    public Optional<SearchResult> findByName(String name) {
        return Optional.ofNullable(getSingleByName(name));
    }

    /**
     * Get all matches for searchString
     * 
     * @param name
     *            Name to search for
     * @return List of matches.
     */
    public List<SearchResult> getMatchesForName(String name) {
        List<SearchResult> result = new LinkedList<>();

        for (FloorMap floor : floors.values()) {
            for (Room room : floor.getRooms()) {
                for (Table table : room.getTables()) {
                    if (table.getName().toLowerCase()
                            .contains(name.toLowerCase())) {
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

    /**
     * Scroll map so that the given table is in view.
     * 
     * @param table
     *            table to show
     */
    public void moveTableToView(Table table) {
        getRpcProxy(SeatingMapClientRpc.class).moveTableToView(table.getId());
    }

    /**
     * Add path nodes.
     * 
     * @param nodes
     *            path nodes
     */
    public void addPaths(Collection<Node> nodes) {
        for (Node node : nodes) {
            paths.put(node.getId(), node);
        }
    }

    /**
     * Add paths by path lines to given floor.
     * 
     * @param pathLines
     *            path lines to add
     * @param floor
     *            floor of added paths
     */
    public void addPaths(Collection<Line> pathLines, int floor) {

        for (Line line : pathLines) {
            Optional<Node> node = getNode(line.start);

            Node nodeStart;
            if (node.isPresent()) {
                nodeStart = node.get();
            } else {
                nodeStart = new Node(
                        (int) (line.start.getX() + line.start.getY()),
                        line.start.clonePoint());
                paths.put(nodeStart.getId(), nodeStart);
            }

            node = getNode(line.end);

            Node nodeEnd;
            if (node.isPresent()) {
                nodeEnd = node.get();
            } else {
                nodeEnd = new Node((int) (line.end.getX() + line.end.getY()),
                        line.end.clonePoint());
                paths.put(nodeEnd.getId(), nodeEnd);
            }

            nodeStart.addConnectedNode(nodeEnd, 1);
        }
    }

    protected Optional<Node> getNode(Point point) {
        for (Node node : paths.values()) {
            if (node.getPosition().equals(point)) {
                return Optional.of(node);
            }
        }
        return Optional.empty();
    }

    /**
     * Get the path between given nodes.
     * 
     * @param fromNode
     *            start node
     * @param toNode
     *            end node
     */
    protected void getPath(int fromNode, int toNode) {
        final Node n1 = paths.get(fromNode);
        final Node n2 = paths.get(toNode);

        if (n1 == null || n2 == null) {
            // final VNotification notification = new VNotification();
            // final Style style = notification.getElement().getStyle();
            // style.setBackgroundColor("#c8ccd0");
            // style.setPadding(15.0, Style.Unit.PX);
            // style.setProperty("border-radius", "4px");
            // style.setProperty("-moz-border-radius", "4px");
            // style.setProperty("-webkit-border-radius", "4px");
            // notification.show("Could not find nodes for both ends!",
            // VNotification.CENTERED_TOP, null);
            return;
        }
        for (final Node node : paths.values()) {
            node.minDistance = Double.POSITIVE_INFINITY;
            node.previous = null;
        }

        Dijkstra.computePaths(n1);
        final LinkedList<Node> pathNodes = Dijkstra.getShortestPathTo(n2);
        Map<Integer, List<Node>> nodesByFloor = new HashMap<>();
        for (Node node : pathNodes) {
            List<Node> nodes = nodesByFloor.get(node.getId());
            if (nodes == null) {
                nodes = new LinkedList<>();
                nodesByFloor.put(node.getId(), nodes);
            }
            nodes.add(node);
        }

        for (Map.Entry<Integer, List<Node>> set : nodesByFloor.entrySet()) {
            VisiblePath visiblePath = new VisiblePath(set.getValue());
            floors.get(set.getKey()).addComponent(visiblePath);
        }
        // final CItem path = new CItem(new LinkedList<Point>(), new
        // Point(position.getX(), position.getY()));
        // for (int i = 0; i < pathNodes.size() - 1; i++) {
        // path.lines.add(new Line(pathNodes.get(i).getPosition(),
        // pathNodes.get(i + 1).getPosition()));
        // path.setColor("RED");
        // }
        // path.circles.add(new Circle(pathNodes.getFirst().getPosition(), 0, 2
        // * Math.PI, 4));
        // path.circles.add(new Circle(pathNodes.getLast().getPosition(), 0, 2 *
        // Math.PI, 4));

    }
}
