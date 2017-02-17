package org.percepta.mgrankvi.path;

import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Dijkstra {

    public static void computePaths(final Node source) {
        source.minDistance = 0.;
        final PriorityQueue<Node> vertexQueue = new PriorityQueue<Node>();
        vertexQueue.add(source);

        while (!vertexQueue.isEmpty()) {
            final Node u = vertexQueue.poll();

            // Visit each edge exiting u
            for (final Link e : u.getLinks()) {
                final Node v = e.getTarget();
                final double weight = e.getWeight();
                final double distanceThroughU = u.minDistance + weight;
                if (distanceThroughU < v.minDistance) {
                    vertexQueue.remove(v);
                    v.minDistance = distanceThroughU;
                    v.previous = u;
                    vertexQueue.add(v);
                }
            }
        }
    }

    public static LinkedList<Node> getShortestPathTo(final Node target) {
        final LinkedList<Node> path = new LinkedList<Node>();
        for (Node vertex = target; vertex != null; vertex = vertex.previous) {
            path.add(vertex);
        }
        Collections.reverse(path);
        return path;
    }

}
