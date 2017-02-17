package org.percepta.mgrankvi;

import com.vaadin.ui.AbstractComponent;
import org.percepta.mgrankvi.client.geometry.Line;
import org.percepta.mgrankvi.path.Node;

import java.util.List;

/**
 * Created by Mikael on 12/01/17.
 */
public class VisiblePath extends AbstractComponent {

    public VisiblePath(List<Node> nodeList) {
        for (int i = 0; i < nodeList.size() - 1; i++) {
            addLine(new Line(nodeList.get(i).getPosition(), nodeList.get(i + 1).getPosition()));
        }
    }

    public void addLine(Line line) {
//        getState().lines.add(line);
    }
    public void addLines(List<Line> lines) {
//        getState().lines = lines;
    }
}
