package org.percepta.mgrankvi.client.room;

import com.google.gwt.dom.client.Document;
import org.percepta.mgrankvi.client.abstracts.Targetable;
import org.percepta.mgrankvi.client.geometry.Calculations;
import org.percepta.mgrankvi.client.geometry.Line;
import org.percepta.mgrankvi.client.geometry.Point;

import static org.percepta.mgrankvi.client.geometry.Calculations.lineSegmentsIntersect;

/**
 * Created by Mikael on 18/12/16.
 */
public class RoomWidget extends Targetable {

    public RoomWidget() {  // Dummy
        setElement(Document.get().createDivElement());
    }

    @Override
    public boolean pointInObject(double x, double y) {
        if (x < (position.getX() + extents.getMinX()) || x > (position.getX() + extents.getMaxX()) || y < (position.getY() + extents.getMinY()) || y > (position.getY() + extents.getMaxY())) {
            return false;
        }

        final Point target = new Point(x, y);
        // First point clearly outside shape.
        final Line targetLine = new Line(new Point(position.getX() + extents.getMinX() - 50, position.getY() + extents.getMinY() - 50), target);
        int intercepts = 0;
        for(Line line : getLines()){
//        for (int i = 0; i + 2 <= points.size(); i++) {
//            final Line line = new Line(combine(position, points.get(i)), combine(position, points.get(i + 1)));
            if ((position.getX() + line.start.getX() > x && position.getX() + line.end.getX() > x) || (position.getY() +line.start.getY() > y && position.getY() +line.end.getY() > y)) {
                continue;
            }
            if (Calculations.lineSegmentsIntersect(line.add(position), targetLine)) {
                intercepts++;
            }
        }
//        final Line line = new Line(combine(position, points.getFirst()), combine(position, points.getLast()));
//        if (lineSegmentsIntersect(line, targetLine)) {
//            intercepts++;
//        }

//        for (final VisualItem item : roomItems) {
//            if (item.pointInObject(x, y)) {
//                item.setHovering(true);
//            } else {
//                item.setHovering(false);
//            }
//        }
        return intercepts % 2 == 1;
    }
}
