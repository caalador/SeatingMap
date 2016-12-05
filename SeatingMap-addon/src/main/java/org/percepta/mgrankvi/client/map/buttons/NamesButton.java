package org.percepta.mgrankvi.client.map.buttons;

import com.google.gwt.canvas.dom.client.Context2d;
import org.percepta.mgrankvi.client.map.ButtonBar;

public class NamesButton extends AbstractButton {

    private final ButtonBar bar;

    public NamesButton(final ButtonBar bar, final double offsetX, final double offsetY) {
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

        context.arc(x - offsetX + 10, y - offsetY + 10, 7, 0, Math.PI * 2, true);
        context.fillText("N", x - offsetX + 6, y - offsetY + 13);

        context.closePath();
        context.stroke();

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
            bar.showNames();
        }
    }
}
