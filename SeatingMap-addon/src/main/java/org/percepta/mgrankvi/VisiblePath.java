package org.percepta.mgrankvi;

import java.util.List;

import org.percepta.mgrankvi.client.geometry.Line;
import org.percepta.mgrankvi.client.path.VisiblePathState;
import org.percepta.mgrankvi.path.Node;

import com.vaadin.shared.AbstractComponentState;
import com.vaadin.ui.AbstractComponent;

/**
 * Created by Mikael on 12/01/17.
 */
public class VisiblePath extends AbstractComponent {

    public VisiblePath(List<Node> nodeList) {
        for (int i = 0; i < nodeList.size() - 1; i++) {
            addLine(new Line(nodeList.get(i).getPosition(),
                    nodeList.get(i + 1).getPosition()));
        }
    }

    @Override
    protected VisiblePathState getState() {
        return (VisiblePathState) super.getState();
    }

    public void addLine(Line line) {
        getState().lines.add(line);
    }

    public void addLines(List<Line> lines) {
        getState().lines = lines;
    }
}
