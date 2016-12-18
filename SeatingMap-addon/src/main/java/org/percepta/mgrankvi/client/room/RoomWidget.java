package org.percepta.mgrankvi.client.room;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Widget;
import org.percepta.mgrankvi.client.geometry.Line;
import org.percepta.mgrankvi.client.geometry.Point;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Mikael on 18/12/16.
 */
public class RoomWidget extends Widget {

    private List<Line> lines;
    private List<Line> original = new LinkedList<Line>();

    protected Point position = new Point(0, 0);
    protected Point orgPosition = new Point(0, 0);

    public RoomWidget() {  // Dummy
        setElement(Document.get().createDivElement());
    }

    public void movePosition(final int x, final int y) {
        position.move(x, y);
        orgPosition.move(x, y);
    }

    public void setPosition(final Point position) {
        this.position = position;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public void paint(Context2d context) {
        context.save();
        context.setStrokeStyle(CssColor.make("BLACK"));

        for (Line l : lines) {
            context.beginPath();
            context.moveTo(position.getX() + l.start.getX(), position.getY() + l.start.getY());
            context.lineTo(position.getX() + l.end.getX(), position.getY() + l.end.getY());
            context.closePath();
            context.stroke();
        }
        context.restore();
    }


    public void scale(final double scale) {
        if (original.isEmpty()) {
            copyLines(lines, original);
            orgPosition = new Point(position.getX(), position.getY());
        }
        for (Line line : lines) {
            line.start.setX((int) Math.ceil(line.start.getX() * scale));
            line.start.setY((int) Math.ceil(line.start.getY() * scale));
            line.end.setX((int) Math.ceil(line.end.getX() * scale));
            line.end.setY((int) Math.ceil(line.end.getY() * scale));

        }
        position.setX((int) Math.ceil(position.getX() * scale));
        position.setY((int) Math.ceil(position.getY() * scale));
    }

    private void copyLines(List<Line> lines, List<Line> original) {
        for (Line line : lines) {
            original.add(new Line(new Point(line.start), new Point(line.end)));
        }
    }

    public void reset() {
        if (original.isEmpty()) {
            position = new Point(orgPosition.getX(), orgPosition.getY());
            return;
        }
        lines.clear();
        copyLines(original, lines);
        position = new Point(orgPosition.getX(), orgPosition.getY());
        original.clear();
    }
}
