package org.percepta.mgrankvi.client.map.buttons;

import com.google.gwt.canvas.dom.client.Context2d;
import org.percepta.mgrankvi.client.map.ButtonBar;

public class PathButton extends AbstractButton {

    private final ButtonBar bar;

    public PathButton(final ButtonBar bar, final double offsetX, final double offsetY) {
        this.bar = bar;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    @Override
    public void paint(final Context2d context) {
        super.paint(context);
        context.save();

        context.setStrokeStyle("white");
        context.setFillStyle("white");

        context.beginPath();

        // context.arc(x - offsetX + 10, y - offsetY + 10, 7, 0, Math.PI * 2,
        // true);
        context.moveTo(x - offsetX + 8, y - offsetY + 5);
        context.bezierCurveTo(x - offsetX - 5, y - offsetY + 20, x - offsetX + 25, y - offsetY, x - offsetX + 15, y - offsetY + 15);
        context.moveTo(x - offsetX + 8, y - offsetY + 5);

        context.closePath();
        context.stroke();

        context.beginPath();

        context.arc(x - offsetX + 8, y - offsetY + 5, 1.5, 0, Math.PI * 2, true);

        context.closePath();
        context.fill();

        context.beginPath();

        context.arc(x - offsetX + 15, y - offsetY + 15, 1.5, 0, Math.PI * 2, true);

        context.closePath();
        context.fill();

        context.restore();
    }

    @Override
    public void hover(final int clientX, final int clientY) {
        if (inside(clientX, clientY)) {
            fillStyle = "ORANGE";
        } else {
            fillStyle = "GREEN";
        }
    }

    @Override
    public void clicked() {
        if (fillStyle.equals("ORANGE")) {
//            bar.getPath();
        }
    }

}
