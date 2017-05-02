package org.percepta.mgrankvi.client.path;

import org.percepta.mgrankvi.client.abstracts.Item;

import com.google.gwt.dom.client.Document;

/**
 * Created by Mikael on 18/12/16.
 */
public class VisiblePathWidget extends Item {

    public VisiblePathWidget() { // Dummy
        setElement(Document.get().createDivElement());
    }
}