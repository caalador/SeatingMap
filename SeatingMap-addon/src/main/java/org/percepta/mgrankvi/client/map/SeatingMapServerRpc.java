package org.percepta.mgrankvi.client.map;

import com.vaadin.shared.communication.ServerRpc;

/**
 * Created by Mikael on 04/01/17.
 */
public interface SeatingMapServerRpc extends ServerRpc {

    void findByName(String name);
}
