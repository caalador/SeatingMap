package org.percepta.mgrankvi;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.percepta.mgrankvi.client.geometry.Line;
import org.percepta.mgrankvi.client.geometry.Point;
import org.percepta.mgrankvi.client.map.SeatingMapClientRpc;
import org.percepta.mgrankvi.client.map.SeatingMapServerRpc;
import org.percepta.mgrankvi.path.Dijkstra;
import org.percepta.mgrankvi.path.Node;
import org.percepta.mgrankvi.util.NearestSearch;
import org.percepta.mgrankvi.util.PathMatrix;

/**
 * @author Mikael Grankvist - Vaadin Ltd
 */
public class SeatingMap extends AbstractComoponents {

    private Map<Integer, FloorMap> floors = new HashMap<>();
    // NodeId - Node map
    private Map<Integer, Node> paths = new HashMap<>();
    private Map<Integer, Integer> nodeToFloor = new HashMap<>();
    // Floor, path Node Matrix
    private Map<Integer, NearestSearch> pathPoints = new HashMap<>();
    private Class<? extends NearestSearch> nearestImpl;
    private List<SelectionListener> selectionEvents = new ArrayList<>(0);

    private Integer visibleFloor;
    private boolean autoToggleName = true;

    public SeatingMap() {
        nearestImpl = PathMatrix.class;
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

                if (autoToggleName && clickedTable != null) {
                    clickedTable.setNameVisibility(
                            !clickedTable.getNameVisibility());
                }

                MapSelectionEvent event = new MapSelectionEvent(clickedRoom, clickedTable);
                selectionEvents.forEach(selectionListener -> selectionListener.clickSelectionEvent(event));
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

    public RemoveHandler addSelectionListener(SelectionListener listener) {
        selectionEvents.add(listener);
        return () -> selectionEvents.remove(listener);
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
        List<Node> nodes = new ArrayList<>();

        for (Line line : pathLines) {
            Optional<Node> node = getNode(line.start);

            Node nodeStart;
            if (node.isPresent()) {
                nodeStart = node.get();
            } else {
                nodeStart = new Node(
                        (int) (line.start.getX() + line.start.getY()) + floor,
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

            nodes.add(nodeStart);
            nodes.add(nodeEnd);
            nodeToFloor.put(nodeStart.getId(), floor);
            nodeToFloor.put(nodeEnd.getId(), floor);
        }

        NearestSearch nearest;
        try {
            nearest = nearestImpl.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException
                | InvocationTargetException | NoSuchMethodException e) {
            String msg = String.format(
                    "Failed to create instance for nearest implementation '%s'. using PathMatrix.class instead",
                    nearestImpl.getSimpleName());
            Logger.getLogger("SeatingMap").log(Level.WARNING, msg, e);
            nearest = new PathMatrix();
        }
        nearest.setNodes(nodes);

        pathPoints.put(floor, nearest);
    }

    /**
     * Find closest node from given paths for all tables.
     */
    public void connectTablesToPaths() {
        floors.entrySet().forEach(entry -> {
            FloorMap floor = entry.getValue();
            NearestSearch nearestSearch = pathPoints.get(entry.getKey());
            floor.getTables().forEach(table -> {
                Point centerPoint = table.getCenter();
                Node nearest = nearestSearch.getNearest(centerPoint);

                table.setClosestNodeId(nearest.getId());
            });
        });
    }

    /**
     * Get the path between given nodes.
     * 
     * @param fromNode
     *            start node
     * @param toNode
     *            end node
     * @return if finding the path was successful
     */
    public boolean getPath(int fromNode, int toNode) {
        final Node n1 = paths.get(fromNode);
        final Node n2 = paths.get(toNode);

        if (n1 == null || n2 == null) {
            return false;
        }
        for (final Node node : paths.values()) {
            node.minDistance = Double.POSITIVE_INFINITY;
            node.previous = null;
        }

        Dijkstra.computePaths(n1);
        final LinkedList<Node> pathNodes = Dijkstra.getShortestPathTo(n2);
        Map<Integer, List<Node>> nodesByFloor = new HashMap<>();
        for (Node node : pathNodes) {
            List<Node> nodes = nodesByFloor.get(nodeToFloor.get(node.getId()));
            if (nodes == null) {
                nodes = new LinkedList<>();
                nodesByFloor.put(nodeToFloor.get(node.getId()), nodes);
            }
            nodes.add(node);
        }

        for (Map.Entry<Integer, List<Node>> set : nodesByFloor.entrySet()) {
            VisiblePath visiblePath = new VisiblePath(set.getValue());
            floors.get(set.getKey()).addComponent(visiblePath);
        }

        return true;
    }

    protected Optional<Node> getNode(Point point) {
        for (Node node : paths.values()) {
            if (node.getPosition().equals(point)) {
                return Optional.of(node);
            }
        }
        return Optional.empty();
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
}
