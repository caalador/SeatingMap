package org.percepta.mgrankvi.client.map;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.TextBox;

public class SearchBar implements KeyUpHandler {

    private final String SEARCH_TEXT = "Search";

    boolean visible = false;
    boolean animate = false;
    // boolean noChangeEvent = false;

    private final TextBox typeAndEdit = new TextBox();
    private int x = 0;
    private int canvasPosition = 0;
    private final int closedWidth = 90;

    SeatingMapWidget grid;

    HandlerRegistration keyUpHandler, gridHandler;

    public SearchBar(final SeatingMapWidget grid) {
        this.grid = grid;

        // grid.addDomHandler(this, ChangeEvent.getType());
        // grid.addDomHandler(this, KeyUpEvent.getType());

        // typeAndEdit.addChangeHandler(this);
        // keyUpHandler = typeAndEdit.addKeyUpHandler(this);
    }

    public void setVisible(final boolean visible) {
        this.visible = visible;
        if (!visible && keyUpHandler != null) {
            keyUpHandler.removeHandler();
            gridHandler.removeHandler();
            keyUpHandler = null;
            gridHandler = null;
        } else if (keyUpHandler == null) {
            keyUpHandler = typeAndEdit.addKeyUpHandler(this);
            gridHandler = grid.addDomHandler(this, KeyUpEvent.getType());
        }

    }

    public boolean isVisible() {
        return visible;
    }

    public void setAnimate(final boolean animate) {
        this.animate = animate;
    }

    public void paint(final Context2d context) {
        canvasPosition = context.getCanvas().getWidth() - 350;
        x = context.getCanvas().getWidth() - 350;

        context.getCanvas().getParentElement().appendChild(typeAndEdit.getElement());
        final Style editStyle = typeAndEdit.getElement().getStyle();
        editStyle.setPosition(Position.ABSOLUTE);
        editStyle.setTop(-24.0, Style.Unit.PX);
        typeAndEdit.getElement().getStyle().setLeft(context.getCanvas().getWidth() - 340, Style.Unit.PX);
        typeAndEdit.setWidth(220 + "px");
        if (visible) {
            if (animate) {
                grid.setAnimating(true);
                animate = false;
                final int boxLeft = context.getCanvas().getWidth() - 200;

                final Animation animator = new Animation() {

                    @Override
                    protected void onUpdate(final double progress) {
                        final double offset = progress * 27;
                        final double elongate = progress * 140;
                        context.save();
                        context.setFillStyle("LAVENDER");
                        context.beginPath();

                        context.arc(x + 150 - elongate, offset + 10, 10, Math.PI * 0.5, Math.PI, false);
                        context.arc(x + 240, offset + 10, 10, 0, Math.PI * 0.5, false);

                        context.closePath();
                        context.fill();

                        typeAndEdit.getElement().getStyle().setTop(offset - 25, Style.Unit.PX);
                        typeAndEdit.getElement().getStyle().setLeft(boxLeft - elongate, Style.Unit.PX);
                        typeAndEdit.setWidth((80 + elongate) + "px");

                        context.beginPath();

                        context.fillRect(x + 140 - elongate, 0, 110 + elongate, offset + 10);

                        context.setFillStyle("Black");
                        context.setFont("bold 12px Courier New");
                        final double textWidth = context.measureText(SEARCH_TEXT).getWidth();
                        context.fillText(SEARCH_TEXT, x + 230 - textWidth, offset + 10);

                        context.closePath();

                        context.restore();
                    }

                    @Override
                    protected void onComplete() {
                        super.onComplete();
                        grid.setAnimating(false);
                    }
                };
                animator.run(500);
            } else {
                context.save();
                context.setFillStyle("LAVENDER");
                context.beginPath();

                context.arc(x + 10, 37, 10, Math.PI * 0.5, Math.PI, false);
                context.arc(x + 240, 37, 10, 0, Math.PI * 0.5, false);

                context.closePath();
                context.fill();

                typeAndEdit.getElement().getStyle().setTop(2, Style.Unit.PX);

                context.beginPath();

                context.fillRect(x, 0, 250, 37);

                context.setFillStyle("Black");
                context.setFont("bold 12px Courier New");
                final double textWidth = context.measureText(SEARCH_TEXT).getWidth();
                context.fillText(SEARCH_TEXT, x + 230 - textWidth, 37);

                context.closePath();

                context.restore();
            }
        } else if (animate) {
            grid.setAnimating(true);
            animate = false;
            typeAndEdit.setFocus(false);
            final int boxLeft = context.getCanvas().getWidth() - 340;

            final Animation animator = new Animation() {

                @Override
                protected void onUpdate(final double progress) {
                    context.clearRect(x, 0, 250, 47);
                    final double offset = progress * 22;
                    final double elongate = progress * 140;
                    context.save();
                    context.setFillStyle("LAVENDER");
                    context.beginPath();

                    context.arc(x + 10 + elongate, 15 + 22 - offset, 10, Math.PI * 0.5, Math.PI, false);
                    context.arc(x + 240, 15 + 22 - offset, 10, 0, Math.PI * 0.5, false);

                    context.closePath();
                    context.fill();

                    typeAndEdit.getElement().getStyle().setTop(0 - offset, Style.Unit.PX);
                    typeAndEdit.getElement().getStyle().setLeft(boxLeft + elongate, Style.Unit.PX);
                    typeAndEdit.setWidth((220 - elongate) + "px");

                    context.beginPath();

                    context.fillRect(x + elongate, 0, 250 - elongate, 37 - offset);

                    context.setFillStyle("Black");
                    context.setFont("bold 12px Courier New");
                    final double textWidth = context.measureText(SEARCH_TEXT).getWidth();
                    context.fillText(SEARCH_TEXT, x + 230 - textWidth, 15 + 22 - offset);

                    context.closePath();

                    context.restore();
                }

                @Override
                protected void onComplete() {
                    super.onComplete();
                    grid.setAnimating(false);
                }
            };
            animator.run(300);
        } else {
            canvasPosition += 150;

            context.save();
            context.setFillStyle("LAVENDER");
            context.beginPath();

            context.arc(x + 150, 10, 10, Math.PI * 0.5, Math.PI, false);
            context.arc(x + 240, 10, 10, 0, Math.PI * 0.5, false);

            context.closePath();
            context.fill();

            context.beginPath();

            context.fillRect(x + 140, 0, 110, 10);

            context.setFillStyle("Black");
            context.setFont("bold 12px Courier New");
            final double textWidth = context.measureText(SEARCH_TEXT).getWidth();
            context.fillText(SEARCH_TEXT, x + 230 - textWidth, 10);

            context.closePath();

            context.restore();
        }
    }

    public boolean mouseOver(final int clientX, final int clientY) {
        if (visible && clientX > canvasPosition && clientX < canvasPosition + 250 && clientY < 47 && clientY > 0) {
            return true;
        } else if (clientX > canvasPosition && clientX < canvasPosition + closedWidth && clientY < 20 && clientY > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void onKeyUp(final KeyUpEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
            // noChangeEvent = true;
            grid.handleTextFieldValue("/" + typeAndEdit.getValue());
            typeAndEdit.setValue("");
        }
    }
}
