package org.percepta.mgrankvi;

import org.percepta.mgrankvi.client.geometry.Line;
import org.percepta.mgrankvi.client.table.TableState;

import java.util.List;

/**
 * Created by Mikael on 04/01/17.
 */
public class Table extends AbstractComoponents {

    public Table() {
    }

    public Table(List<Line> lines) {
        addLines(lines);
    }

    public void setName(String name) {
        getState().name = name;
    }

    public String getName() {
        return getState(false).name;
    }

    public void setImageUrl(String imageUrl) {
        getState().imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return getState(false).imageUrl;
    }

    public void addLines(List<Line> lines) {
        getState().lines = lines;
    }

    @Override
    protected TableState getState() {
        return (TableState) super.getState();
    }

    @Override
    protected TableState getState(boolean markAsDirty) {
        return (TableState) super.getState(markAsDirty);
    }
}
