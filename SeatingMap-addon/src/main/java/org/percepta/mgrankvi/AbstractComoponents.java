package org.percepta.mgrankvi;

import com.google.common.collect.Lists;
import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.HasComponents;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Mikael on 18/12/16.
 */
public abstract class AbstractComoponents extends AbstractComponentContainer implements HasComponents {

    List<Component> children = Lists.newLinkedList();


    @Override
    public void addComponent(final Component c) {
        if (c == null) {
            return;
        }
        children.add(c);
        super.addComponent(c);
    }

    @Override
    public void removeComponent(final Component c) {
        if (c == null) {
            return;
        }
        children.remove(c);
        super.removeComponent(c);
        markAsDirty();
    }

    @Override
    public void replaceComponent(final Component oldComponent, final Component newComponent) {
        if (newComponent == null) {
            return;
        }
        final int index = children.indexOf(oldComponent);
        if (index != -1) {
            children.remove(index);
            children.add(index, newComponent);
            fireComponentDetachEvent(oldComponent);
            fireComponentAttachEvent(newComponent);
        }
    }

    @Override
    public int getComponentCount() {
        return children.size();
    }

    @Override
    public Iterator<Component> iterator() {
        return children.iterator();
    }

    /**
     * Get child component with id if one has been connected to component
     *
     * @param id String component id
     * @return Component or Null if not found.
     */
    public Component getById(String id) {
        for (Component c : children) {
            if (c.getId() != null && c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

}
