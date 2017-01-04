package org.percepta.mgrankvi.client.floor;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Widget;
import org.percepta.mgrankvi.client.abstracts.Item;
import org.percepta.mgrankvi.client.geometry.Line;
import org.percepta.mgrankvi.client.geometry.Point;
import org.percepta.mgrankvi.client.map.CommandObject;
import org.percepta.mgrankvi.client.map.SeatingMapWidget;
import org.percepta.mgrankvi.client.room.RoomWidget;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Mikael Grankvist - Vaadin Ltd
 */
public class RoomContainer extends Item implements Comparable<RoomContainer> {

  String id;
  int level;

  SeatingMapWidget grid;

  protected List<RoomWidget> rooms = new LinkedList<>();

  public RoomContainer() {
    // Dummy
    setElement(Document.get().createDivElement());
    Logger.getLogger("CFloor").log(Level.FINE, " --- Created CFloor");
  }

  public void setGrid(SeatingMapWidget grid) {
    this.grid = grid;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void clear() {
    rooms.clear();
  }

  public void add(final Widget widget) {
    if (widget instanceof RoomWidget) {
      final RoomWidget room = (RoomWidget) widget;
      rooms.add(room);
    }
  }

  public void paint() {
    final Context2d context = grid.getCanvas().getContext2d();
    super.paint(context);

    for (final RoomWidget room : rooms) {
      room.paint(context);
    }
  }

  // Mouse event handlers
  public void click(final double clientX, final double clientY) {
//    if (hoverElement != null && hoverElement.pointInObject(clientX, clientY)) {
//      // fireEvent(new
//      // MenuEvent(MenuEvent.MenuEventType.OPEN_ROOM_INFO,
//      // hoverElement.getRoom().getId()));
//      final CInfoEditor info = new CInfoEditor(this, hoverElement.getRoom());
//      info.setPopupPosition((Window.getClientWidth() / 2) - 350, (Window.getClientHeight() / 2) - 200);
//      info.show();
//    }
  }

  public void clickForRoomSelect(final double downX, final double downY) {
//    for (final CRoom room : rooms) {
//      room.clicked(downX, downY);
//    }
  }

  public void mouseUp() {
//    selected = null;
  }

  public void checkHover(final double clientX, final double clientY) {
    for (final RoomWidget room : rooms) {
      room.setShadow(room.pointInObject(clientX, clientY));
    }
  }

  public void pan(final int amountx, final int amounty) {
    movePosition(amountx, amounty);
    for (final RoomWidget room : rooms) {
      room.movePosition(amountx, amounty);
    }
  }

  public void scale(final double scale) {
    super.scale(scale);
    for (final RoomWidget room : rooms) {
      room.scale(scale);
    }
  }

  public void reset() {
    super.reset();
    for (final RoomWidget room : rooms) {
      room.reset();
    }
  }

  @Override
  public int compareTo(RoomContainer o) {
    return level == o.level ? 0 : level < o.level ? -1 : 1;
  }

  public void showNames() {
//    showNames = !showNames;
//    for (final CRoom room : rooms) {
//      for (final VisualItem item : room.getRoomItems()) {
//        if (item instanceof CTable) {
//          ((CTable) item).setSelected(showNames);
//        }
//      }
//    }
  }


//  private void moveTableToView(final CRoom room, final CTable table) {
//    final double xPointInCanvas = (grid.canvas.getCoordinateSpaceWidth() / 2) - (table.maxX() - table.minX()) / 2;
//    final double yPointInCanvas = (grid.canvas.getCoordinateSpaceHeight() / 2) - (table.maxY() - table.minY()) / 2;
//
//    final double tableCornerX = table.getPositionX() + room.getPositionX();
//    final double tableCornerY = table.getPositionY() + room.getPositionY();
//
//    final double panX = xPointInCanvas - tableCornerX;
//    final double panY = yPointInCanvas - tableCornerY;
//
//    final Animation animate = new Animation() {
//      double movedX = 0;
//      double movedY = 0;
//
//      @Override
//      protected void onUpdate(final double progress) {
//        final double moveX = panX * progress - movedX;
//        final double moveY = panY * progress - movedY;
//        movedX += moveX;
//        movedY += moveY;
//        grid.pan((int) Math.floor(moveX), (int) Math.floor(moveY));
//        grid.repaint();
//      }
//
//      @Override
//      protected void onComplete() {
//        super.onComplete();
//        grid.setAnimating(false);
//      }
//    };
//    grid.setAnimating(true);
//    Logger.getLogger("CFloor").log(Level.FINER, " -- X: " + panX + " Y: " + panY);
//    animate.run(Math.abs(panX) > Math.abs(panY) ? (int) (Math.abs(panX)) : (int) (Math.abs(panY)));
//  }

  public void remove(final RoomWidget room) {
    rooms.remove(room);
  }

}
