package org.percepta.mgrankvi.path;

public class Link {

    protected Node target;
    protected int weight;

    public Link() {
    }

    public Link(final Node target, final int weight) {
        this.target = target;
        this.weight = weight;
    }

    public Node getTarget() {
        return target;
    }

    public int getWeight() {
        return weight;
    }

}
