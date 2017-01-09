package org.percepta.mgrankvi.client.map;

import com.vaadin.shared.communication.ClientRpc;

/**
 * Created by Mikael on 09/01/17.
 */
public interface SeatingMapClientRpc extends ClientRpc {

    void moveTableToView(String tableId);
}
