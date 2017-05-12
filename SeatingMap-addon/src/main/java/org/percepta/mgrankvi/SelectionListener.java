package org.percepta.mgrankvi;

/**
 * Created by Mikael on 12/05/17.
 */
@FunctionalInterface
public interface SelectionListener {
    void clickSelectionEvent(MapSelectionEvent event);
}
