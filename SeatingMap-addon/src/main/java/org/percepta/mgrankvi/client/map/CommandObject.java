package org.percepta.mgrankvi.client.map;

import org.percepta.mgrankvi.client.geometry.Point;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandObject {

    public enum Command {
        INVALID_STRING, PARSE_FAILED, MOVE_TO, MOVE_BY, SAVE, FIND, PAN, EDIT, PATHING
    }

    Command command = Command.INVALID_STRING;
    int x, y;
    String value;

    public CommandObject(final String command) {
        Logger.getLogger("CommndObject").log(Level.INFO, "Handling command: " + command);
        final String[] split = command.substring(1).split(",");
        if (split.length == 2) {
            try {
                x = Integer.parseInt(split[0]);
                y = Integer.parseInt(split[1]);

                if (command.startsWith("@")) {
                    this.command = Command.MOVE_BY;
                } else if (command.startsWith("m")) {
                    this.command = Command.MOVE_TO;
                } else if (command.startsWith("p")) {
                    this.command = Command.PAN;
                }
            } catch (final NumberFormatException nfe) {
                this.command = Command.PARSE_FAILED;
            }
        }
        if (command.startsWith("s")) {
            this.command = Command.SAVE;
        } else if (command.equalsIgnoreCase("edit")) {
            this.command = Command.EDIT;
        } else if (command.equalsIgnoreCase("path")) {
            this.command = Command.PATHING;
        } else if (command.startsWith("/")) {
            this.command = Command.FIND;
            value = command.substring(1);
        }
        Logger.getLogger("CommndObject").log(Level.INFO, "Parsed command: " + this.command);
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(final Command command) {
        this.command = command;
    }

    public int getX() {
        return x;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(final int y) {
        this.y = y;
    }

    public Point getPosition() {
        return new Point(x, y);
    }

    public String getValue() {
        return value;
    }
}
