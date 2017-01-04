package org.percepta.mgrankvi.client.table;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;
import org.percepta.mgrankvi.client.abstracts.Targetable;
import org.percepta.mgrankvi.client.geometry.Calculations;
import org.percepta.mgrankvi.client.geometry.Point;

/**
 * Created by Mikael on 18/12/16.
 */
public class TableWidget extends Targetable {

    private static double TWO_PI = Math.PI * 2.0;

    protected String nameString;

    protected boolean paintName = false;

    public TableWidget() {  // Dummy
        setElement(Document.get().createDivElement());
    }

    public void setPaintName(boolean paintName) {
        this.paintName = paintName;
        setShadow(paintName);
    }

    @Override
    public void paint(Context2d context) {
        super.paint(context);

        if (paintName) {
            paintName(context);
        }
    }

    public void paintName(final Context2d context) {
        context.save();
        context.setFont("bold 10px Courier New");

        final int width = (int) Math.ceil(context.measureText(nameString).getWidth());
        final double tableWidth = extents.getMaxX() - extents.getMinX();

        final double namePosition = (tableWidth - width) / 2;

        final Point drawPosition = Calculations.combine(Calculations.combine(position, new Point(extents.getMinX(),extents.getMinY())),
                new Point((int) Math.floor(namePosition), (int) Math.floor((extents.getMaxY() - extents.getMinY()) * 0.5 - 10)));

        context.setFillStyle("GREEN");
        context.beginPath();

        context.arc(drawPosition.getX(), drawPosition.getY() + 10, 10, 0, TWO_PI, true);
        context.fillRect(drawPosition.getX(), drawPosition.getY(), width, 20);
        context.arc(drawPosition.getX() + width, drawPosition.getY() + 10, 10, 0, TWO_PI, true);

        context.closePath();
        context.fill();

        context.setFillStyle("WHITE");
        context.beginPath();

        context.fillText(nameString, drawPosition.getX(), drawPosition.getY() + 12);

//        if (imageUrl != null) {
//            final Image image = new Image(imageUrl);
//            context.drawImage(ImageElement.as(image.getElement()), drawPosition.getX(), drawPosition.getY() + 25);
//        }

        context.closePath();
        context.restore();
    }
}
