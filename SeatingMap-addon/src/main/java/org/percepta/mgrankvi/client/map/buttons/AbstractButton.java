package org.percepta.mgrankvi.client.map.buttons;

import com.google.gwt.canvas.dom.client.Context2d;
import com.vaadin.client.VConsole;

public abstract class AbstractButton {

    public abstract void hover(final int clientX, final int clientY);

    public abstract void clicked();

    protected String fillStyle = "GREEN";

    protected double x = 0;
    protected double y = 0;
    protected double offsetX;
    protected double offsetY;

    public void setX(final double x) {
        if (this.x != x) {
            this.x = x;
        }
    }

    public void setY(final double y) {
        if (this.y != y) {
            this.y = y;
        }
    }

    public void setFillstyle(final String style) {
        fillStyle = style;
    }

    public void paint(final Context2d context) {
        context.save();

        context.setFillStyle(fillStyle);

        context.beginPath();

        context.fillRect(x - offsetX, y - offsetY, 20, 20);

        context.closePath();
        context.stroke();
        context.restore();
    }

    public void paint(final Context2d context, final double progress) {
        context.save();

        context.setFillStyle(fillStyle);

        context.beginPath();

        final double size = 20 * progress;
        context.fillRect(x - offsetX, y - offsetY, size, size);

        context.closePath();
        context.stroke();

        context.restore();
    }

    protected boolean inside(final int pointX, final int pointY) {
        VConsole.log(" -- X: " + pointX + " Y: " + pointY);
        VConsole.log(" -- X min/max: " + (x - offsetX) + " / " + (x - offsetX + 20));
        VConsole.log(" -- Y min/max: " + (y - offsetY) + " / " + (y - offsetY + 20));
        if (pointX > x - offsetX && pointX < x - offsetX + 20 && pointY > y - offsetY && pointY < y - offsetY + 20) {
            return true;
        }
        return false;
    }
}
