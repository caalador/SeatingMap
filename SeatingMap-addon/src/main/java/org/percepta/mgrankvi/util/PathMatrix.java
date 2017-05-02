package org.percepta.mgrankvi.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.stream.IntStream;

import org.percepta.mgrankvi.client.geometry.Point;
import org.percepta.mgrankvi.path.Node;

/**
 * Created by Mikael on 28/04/17.
 */
public class PathMatrix {

    int maxX = 0;
    int maxY = 0;

    Vector<Vector<Set<Node>>> matrix;

    public PathMatrix(List<Node> nodes) {
        nodes.forEach(this::evaluateMax);

        maxX++;
        maxY++;

        matrix = new Vector<>(maxY);
        IntStream.range(0, maxY).forEach(i -> {
            matrix.add(new Vector<>(maxX));
            IntStream.range(0, maxX)
                    .forEach(j -> matrix.get(i).add(new HashSet()));
        });
        nodes.forEach(node -> {
            int y = getMaxByHundred(node.getPosition().getY());
            int x = getMaxByHundred(node.getPosition().getX());
            Set<Node> nodeSet = matrix.get(y).get(x);
            nodeSet.add(node);
        });
    }

    private void evaluateMax(Node node) {
        int y = getMaxByHundred(node.getPosition().getY());
        int x = getMaxByHundred(node.getPosition().getX());
        if (y > maxY) {
            maxY = y;
        }
        if (x > maxX) {
            maxX = x;
        }
    }

    private int getMaxByHundred(double value) {
        return (int) value / 100;
    }

    public Node getNearest(Point centerPoint) {
        int y = getMaxByHundred(centerPoint.getY());
        int x = getMaxByHundred(centerPoint.getX());

        int dx = 1;
        int dy = 0;
        int segment_length = 1;

        int segment_passed = 0;
        int cutoff = maxX * maxY;
        int tries = 0;

        // The cutoff is not efficient if running close to the sides
        // but the expectation is that a point would be found really
        // close to the search point.
        //
        Node nearest = null;
        boolean segmentDone = false;
        do {
            // Only check nearest if we reside inside matrix, else just advance.
            if (y < maxY || x < maxX || y >= 0 || x >= 0) {
                Node newNearest = getNearest(x, y, nearest);
                if (nearest != null) {
                    double distance = Math.hypot(
                            x - nearest.getPosition().getX(),
                            y - nearest.getPosition().getY());
                    double newDistance = Math.hypot(
                            x - newNearest.getPosition().getX(),
                            y - newNearest.getPosition().getY());
                    if (distance > newDistance) {
                        nearest = newNearest;
                    }
                } else {
                    nearest = newNearest;
                }
            }

            x += dx;
            y += dy;
            segment_passed++;
            segmentDone = false;
            if (segment_length == segment_passed) {
                segment_passed = 0;
                int holder = dx;
                dx = -dy;
                dy = holder;
                if (dy == 0) {
                    segment_length++;
                    segmentDone = true;
                }
            }
            tries++;
            // Run whole segment for the off chance that we have a
            // closer match than the first found.
        } while ((nearest == null && !segmentDone) || cutoff <= tries);

        return nearest;
    }

    private Node getNearest(int x, int y, Node currentNearest) {
        Node nearest = null;
        double dist = Double.MAX_VALUE;

        for (Node node : matrix.get(y).get(x)) {
            double distance = Math.hypot(x - node.getPosition().getX(),
                    y - node.getPosition().getY());
            if (distance < dist) {
                nearest = node;
                dist = distance;
            }
        }
        // Check if the old nearest if closer than the one found in this cell.
        if (currentNearest != null) {
            double currentDistance = Math.hypot(
                    x - currentNearest.getPosition().getX(),
                    y - currentNearest.getPosition().getY());
            double newDistance = Math.hypot(x - nearest.getPosition().getX(),
                    y - nearest.getPosition().getY());
            if (currentDistance < newDistance) {
                nearest = currentNearest;
            }
        }
        return nearest;
    }
}
