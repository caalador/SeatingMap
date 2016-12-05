package org.percepta.mgrankvi.client.map;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.canvas.dom.client.Context2d;
import org.percepta.mgrankvi.client.map.buttons.AbstractButton;
import org.percepta.mgrankvi.client.map.buttons.NamesButton;
import org.percepta.mgrankvi.client.map.buttons.PathButton;

import java.util.LinkedList;
import java.util.List;

public class ButtonBar {

    private final int START_BUBBLE_SIZE = 20;
    private final int BAR_HEIGHT = 150;
    private final int BAR_WIDTH = 15;

    private boolean visible = false;
    private boolean animate = false;

    private final List<AbstractButton> buttons = new LinkedList<AbstractButton>();
    private int x = 0;
    private int y = 0;
    private int cornerX;
    private int cornerY;
    private final SeatingMapWidget grid;

    NamesButton names = new NamesButton(this, 25, BAR_HEIGHT + 10);
    PathButton path = new PathButton(this, 25, BAR_HEIGHT - 20);

    public ButtonBar(final SeatingMapWidget grid) {
        this.grid = grid;
        buttons.add(names);
        buttons.add(path);
    }

    public void setVisible(final boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setAnimate(final boolean animate) {
        this.animate = animate;
    }

    public void paint(final Context2d context) {
        x = context.getCanvas().getWidth();
        y = context.getCanvas().getHeight();
        cornerX = context.getCanvas().getWidth();
        cornerY = context.getCanvas().getHeight();
        for (final AbstractButton b : buttons) {
            b.setX(x);
            b.setY(y);
        }
        if (visible) {
            if (animate) {
                grid.setAnimating(true);
                animate = false;

                final Animation elongate = new Animation() {

                    @Override
                    protected void onUpdate(final double progress) {
                        final double offset = progress * 15;
                        final double height = progress * BAR_HEIGHT;
                        context.save();

                        context.setFillStyle("LAVENDER");
                        context.beginPath();

                        context.arc(x - offset, y - height, START_BUBBLE_SIZE, Math.PI, Math.PI * 1.5, false);

                        context.closePath();
                        context.fill();

                        context.beginPath();

                        context.moveTo(x - offset, y - height - START_BUBBLE_SIZE);
                        context.lineTo(x - offset, y - height);
                        context.lineTo(x - offset - START_BUBBLE_SIZE, y - height);

                        context.fillRect(x - offset, y - START_BUBBLE_SIZE - height, offset, START_BUBBLE_SIZE);
                        context.fillRect(x - offset - START_BUBBLE_SIZE, y - height, START_BUBBLE_SIZE + offset, height);

                        context.closePath();
                        context.fill();

                        context.restore();
                    }

                    @Override
                    protected void onComplete() {
                        super.onComplete();
                        // grid.setAnimating(false);

                        final Animation buttonAnimation = new Animation() {

                            @Override
                            protected void onUpdate(final double progress) {
                                for (final AbstractButton b : buttons) {
                                    b.paint(context, progress);
                                }
                            }

                            @Override
                            protected void onComplete() {
                                super.onComplete();
                                grid.setAnimating(false);
                                for (final AbstractButton b : buttons) {
                                    b.paint(context);
                                }
                            }
                        };
                        buttonAnimation.run(300);
                    }
                };
                elongate.run(500);
            } else {
                context.save();
                context.setFillStyle("LAVENDER");
                context.beginPath();

                context.arc(x - 15, y - BAR_HEIGHT, START_BUBBLE_SIZE, Math.PI, Math.PI * 1.5, false);

                context.closePath();
                context.fill();

                context.beginPath();

                context.moveTo(x - 15, y - BAR_HEIGHT - START_BUBBLE_SIZE);
                context.lineTo(x - 15, y - BAR_HEIGHT);
                context.lineTo(x - 15 - START_BUBBLE_SIZE, y - BAR_HEIGHT);

                context.fillRect(x - 15, y - START_BUBBLE_SIZE - BAR_HEIGHT, 15, START_BUBBLE_SIZE);
                context.fillRect(x - 15 - START_BUBBLE_SIZE, y - BAR_HEIGHT, START_BUBBLE_SIZE + 15, BAR_HEIGHT);

                context.closePath();
                context.fill();

                context.restore();

                for (final AbstractButton b : buttons) {
                    b.paint(context);
                }
            }
        } else if (animate) {
            grid.setAnimating(true);
            animate = false;
            names.setFillstyle("GREEN");

            final Animation animator = new Animation() {

                @Override
                protected void onUpdate(final double progress) {
                    context.clearRect(x - 40, y - 325 - START_BUBBLE_SIZE, 40, 325);
                    final double offset = progress * BAR_WIDTH;
                    final double height = progress * BAR_HEIGHT;
                    context.save();

                    context.setFillStyle("LAVENDER");
                    context.beginPath();

                    context.arc(x - BAR_WIDTH + offset, y - BAR_HEIGHT + height, START_BUBBLE_SIZE, Math.PI, Math.PI * 1.5, false);

                    context.closePath();
                    context.fill();

                    context.beginPath();

                    context.moveTo(x - BAR_WIDTH + offset, y - BAR_HEIGHT + height - START_BUBBLE_SIZE);
                    context.lineTo(x - BAR_WIDTH + offset, y - BAR_HEIGHT + height);
                    context.lineTo(x - BAR_WIDTH + offset - START_BUBBLE_SIZE, y - BAR_HEIGHT + height);

                    context.fillRect(x - BAR_WIDTH + offset, y - START_BUBBLE_SIZE - BAR_HEIGHT + height, BAR_WIDTH - offset, START_BUBBLE_SIZE);
                    context.fillRect(x - 35 + offset, y - BAR_HEIGHT + height, START_BUBBLE_SIZE + BAR_WIDTH - offset, BAR_HEIGHT - height);
                    // context.fillRect(x + offset, y - START_BUBBLE_SIZE +
                    // height, offset, START_BUBBLE_SIZE);
                    // context.fillRect(x + offset - START_BUBBLE_SIZE, y +
                    // height, START_BUBBLE_SIZE - offset, height);

                    context.closePath();
                    context.fill();

                    context.restore();
                }

                @Override
                protected void onComplete() {
                    super.onComplete();
                    grid.setAnimating(false);
                }
            };
            animator.run(400);
        } else {
            context.save();
            context.setFillStyle("LAVENDER");
            context.beginPath();

            context.arc(x, y, START_BUBBLE_SIZE, Math.PI, Math.PI * 1.5, false);

            context.closePath();
            context.fill();

            context.beginPath();

            context.moveTo(x, y - START_BUBBLE_SIZE);
            context.lineTo(x, y);
            context.lineTo(x - START_BUBBLE_SIZE, y);

            context.closePath();
            context.fill();

            context.restore();
        }
    }

    public boolean mouseOver(final int clientX, final int clientY) {
        if (visible && clientX > (cornerX - START_BUBBLE_SIZE - BAR_WIDTH) && clientY > (cornerY - BAR_HEIGHT - START_BUBBLE_SIZE)) {
            for (final AbstractButton b : buttons) {
                b.hover(clientX, clientY);
            }
            return true;
        } else if (clientX > (cornerX - START_BUBBLE_SIZE) && clientY > (cornerY - START_BUBBLE_SIZE)) {
            return true;
        }
        return false;
    }

    public void click(final int x, final int y) {
        for (final AbstractButton b : buttons) {
            b.clicked();
        }
    }

    public void showNames() {
        grid.showNames();
    }

//    public void getPath() {
//        grid.getPath();
//    }
}
