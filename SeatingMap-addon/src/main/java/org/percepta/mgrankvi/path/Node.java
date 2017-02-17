package org.percepta.mgrankvi.path;

import org.percepta.mgrankvi.client.geometry.Point;

import java.util.LinkedList;
import java.util.List;

public class Node implements Comparable<Node> {

    protected int id;
    protected List<Link> links = new LinkedList<Link>();
    protected Point position;

    public double minDistance = Double.POSITIVE_INFINITY;
    public Node previous;
    public int level;

    private Node() {
    }

    public Node(final int id, final Point position) {
        this.id = id;
        this.position = position;
    }

    /**
     * Link both nodes with same weight in both directions.
     *
     * @param node
     * @param weight
     */
    public void addConnectedNode(final Node node, final int weight) {
        links.add(new Link(node, weight));
        node.links.add(new Link(this, weight));
    }

    public boolean hasLinkTo(final Node node) {
        for (final Link link : links) {
            if (link.target.equals(node)) {
                return true;
            }
        }
        return false;
    }

    public Link getLinkTo(final Node node) {
        for (final Link link : links) {
            if (link.target.equals(node)) {
                return link;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public List<Link> getLinks() {
        return links;
    }

    public Point getPosition() {
        return position;
    }

    @Override
    public int compareTo(final Node other) {
        return Double.compare(minDistance, other.minDistance);
    }

}
