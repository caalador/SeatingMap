package org.percepta.mgrankvi.client.floor;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Widget;
import org.percepta.mgrankvi.client.geometry.Line;
import org.percepta.mgrankvi.client.geometry.Point;
import org.percepta.mgrankvi.client.map.CommandObject;
import org.percepta.mgrankvi.client.map.SeatingMapWidget;

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
public class RoomContainer extends Widget implements Comparable<RoomContainer> {

  String id;
  int level;

  SeatingMapWidget grid;
  private Object selected;
  private List<Line> lines;
  private List<Line> original = new LinkedList<Line>();

  protected Point position = new Point(0, 0);
  protected Point orgPosition = new Point(0, 0);

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

  public void movePosition(final int x, final int y) {
    position.move(x, y);
    orgPosition.move(x, y);
  }

  public void setPosition(final Point position) {
    this.position = position;
  }

  public String getId() {
    return id;
  }

  public void setLines(List<Line> lines) {
    this.lines = lines;
  }

  public void clear() {
//    rooms.clear();
  }

  public void add(final Widget widget) {
//    if (widget instanceof CRoom) {
//      final CRoom room = (CRoom) widget;
//      rooms.add(room);
//    } else if (widget instanceof PathGridItem) {
//      waypoints = (PathGridItem) widget;
//    } else if (widget instanceof VisualItem) {
//      items.add((VisualItem) widget);
//    }
  }

  public void paint() {
    final Context2d context = grid.getCanvas().getContext2d();
//    for (final CRoom room : rooms) {
//      room.paint(context);
//    }
//
//    for (final VisualItem item : items) {
//      item.paint(context);
//    }
//    if (hoverElement != null) {
//      hoverElement.paint(context);
//    }
    context.save();
    context.setStrokeStyle(CssColor.make("BLACK"));

    for (Line l : lines) {
      context.beginPath();
      context.moveTo(position.getX()+l.start.getX(), position.getY()+l.start.getY());
      context.lineTo(position.getX()+l.end.getX(), position.getY()+l.end.getY());
      context.closePath();
      context.stroke();
    }
    context.restore();
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

  public void clickEditable(final double clientX, final double clientY) {

//    boolean selected = false;
//    for (final CRoom room : rooms) {
//      if (!selected && room.pointInObject(clientX, clientY)) {
//        room.setSelection(true);
//        this.selected = room;
//        selected = true;
//        VConsole.log("Room selected. " + this.selected);
//      } else {
//        room.setSelection(false);
//      }
//    }
//    if (!selected) {
//      VConsole.log("Removed room selection. " + this.selected);
//      this.selected = null;
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
//    if (hoverElement == null) {
//      for (final CRoom room : rooms) {
//        if (room.pointInObject(clientX, clientY)) {
//          hoverElement = new InfoButton(room);
//          break;
//        }
//      }
//    } else {
//      if (!hoverElement.getRoom().pointInObject(clientX, clientY)) {
//        hoverElement = null;
//      }
//    }
  }

  public void pan(final int amountx, final int amounty) {
    movePosition(amountx, amounty);
//    for (final CRoom room : rooms) {
//      room.movePosition(amountx, amounty);
//    }
//
//    for (final VisualItem item : items) {
//      item.movePosition(amountx, amounty);
//    }
//    if (updateWaypoints && waypoints != null) {
//      waypoints.movePosition(amountx, amounty);
//    }
  }

  public void scale(final double scale) {
//    for (final CRoom room : rooms) {
//      room.scale(scale);
//    }
//
//    for (final VisualItem item : items) {
//      item.scale(scale);
//    }
    if (original.isEmpty()) {
      copyLines(lines, original);
      orgPosition = new Point(position.getX(), position.getY());
    }
    for (Line line : lines){
      line.start.setX((int) Math.ceil(line.start.getX() * scale));
      line.start.setY((int) Math.ceil(line.start.getY() * scale));
      line.end.setX((int) Math.ceil(line.end.getX() * scale));
      line.end.setY((int) Math.ceil(line.end.getY() * scale));

    }
    position.setX((int) Math.ceil(position.getX() * scale));
    position.setY((int) Math.ceil(position.getY() * scale));

//    for (final VisualItem item : roomItems) {
//      item.scale(scale);
//      final Point position = item.getPosition();
//      position.setX((int) Math.ceil(position.getX() * scale));
//      position.setY((int) Math.ceil(position.getY() * scale));
//      item.setPosition(position);
//      for (final Point p : item.getPoints()) {
//        p.setX((int) Math.ceil(p.getX() * scale));
//        p.setY((int) Math.ceil(p.getY() * scale));
//      }
//      item.pointMoved();
//    }
//    if (roomLabel != null) {
//      roomLabel.scale(scale);
//      final Point position = roomLabel.getPosition();
//      position.setX((int) Math.ceil(position.getX() * scale));
//      position.setY((int) Math.ceil(position.getY() * scale));
//      roomLabel.setPosition(position);
//    }
  }

  private void copyLines(List<Line> lines, List<Line> original) {
    for(Line line : lines) {
      original.add(new Line(new Point(line.start), new Point(line.end)));
    }
  }

  public void reset() {
//    for (final CRoom room : rooms) {
//      room.reset();
//    }
//
//    for (final VisualItem item : items) {
//      item.reset();
//    }
    if (original.isEmpty()) {
      position = new Point(orgPosition.getX(), orgPosition.getY());
      return;
    }
    lines.clear();
    copyLines(original, lines);
    position = new Point(orgPosition.getX(), orgPosition.getY());
    original.clear();
  }

  @Override
  public int compareTo(RoomContainer o) {
    return level == o.level ? 0 : level < o.level ? -1 : 1;
  }

  public void setSelected(Object selected) {
    this.selected = selected;
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

  public Set<String> getNames() {
    final Set<String> names = new HashSet<String>();
//    for (final CRoom room : rooms) {
//      for (final VisualItem item : room.getRoomItems()) {
//        if (item instanceof CTable) {
//          names.add(item.getName());
//        }
//      }
//    }
    return new HashSet<String>(names);
  }

  // stuff
  public boolean namesContain(final CommandObject cmd) {
    return namesContain(cmd.getValue());
  }

  public boolean namesContain(final String name) {
//    for (final CRoom room : rooms) {
//      for (final VisualItem item : room.getRoomItems()) {
//        if (item instanceof CTable) {
//          if (item.getName() != null && item.getName().toLowerCase().contains(name.toLowerCase())) {
//            return true;
//          }
//        }
//      }
//    }
    return false;
  }

  public LinkedList<String> possibilities(final CommandObject cmd) {
    return possibilities(cmd.getValue());
  }

  public LinkedList<String> possibilities(final String name) {
    final LinkedList<String> possible = new LinkedList<String>();

//    for (final CRoom room : rooms) {
//      for (final VisualItem item : room.getRoomItems()) {
//        if (item instanceof CTable) {
//          if (item.getName() != null && item.getName().toLowerCase().contains(name.toLowerCase())) {
//            possible.add(item.getName());
//          }
//        }
//      }
//    }

    Collections.sort(possible);

    return possible;
  }

//  public CTable getTableOfSelectedPerson(final String nameOfSelection) {
//    for (final CRoom room : rooms) {
//      for (final VisualItem item : room.getRoomItems()) {
//        if (item instanceof CTable) {
//          final CTable table = (CTable) item;
//          if (nameOfSelection.equals(table.getName())) {
//            return table;
//          }
//        }
//      }
//    }
//    return null;
//  }

  public void markTableOfSelectedPerson(final String nameOfSelection) {
//    if (markedTable != null) {
//      markedTable.setTableColor("TRANSPARENT");
//      markedTable.setSelected(false);
//    }
//    for (final CRoom room : rooms) {
//      for (final VisualItem item : room.getRoomItems()) {
//        if (item instanceof CTable) {
//          final CTable table = (CTable) item;
//          if (nameOfSelection.equals(table.getName())) {
//            markedTable = table;
//            table.setTableColor("burlywood");
//            table.setSelected(true);
//            moveTableToView(room, table);
//
//            return;
//          }
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

//  public void remove(final CRoom selectedRoom) {
//    rooms.remove(selectedRoom);
//  }

}
