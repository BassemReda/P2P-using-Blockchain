package com.company;

import de.tum.in.www1.jReto.LocalPeer;
import de.tum.in.www1.jReto.RemotePeer;
import de.tum.in.www1.jReto.module.wlan.WlanModule;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

public class LocalTransPeer {

    private LocalPeer localPeer;
    private Map<RemotePeer, P2PConnection> chatPeers = new HashMap<>();
    private String displayName;

    public LocalTransPeer(Executor mainThreadExecutor) {

        try {
            this.initializeLocalPeer(mainThreadExecutor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initializeLocalPeer(Executor executor) throws Exception {
        /**
         * Create a local peer with a WlanModule. To use the RemoteP2PModule, the RemoteP2P server needs to be deployed locally.
         */
        //RemoteP2PModule remoteModule = new RemoteP2PModule(new URI("ws://localhost:8080/"));
        WlanModule wlanModule = new WlanModule("SimpleP2PChat");
        this.localPeer = new LocalPeer(Arrays.asList(wlanModule), executor);
    }

    public void start(String displayName) {
        this.displayName = displayName;

        this.localPeer.start(
                peer -> createConnection(peer),
                peer -> removeConnection(peer)
        );
    }

    public void createConnection(RemotePeer peer) {
        if (this.chatPeers.get(peer) != null) {
            System.err.println("We already have a chat peer for this peer!");
            return;
        }

        P2PConnection chatPeer = new P2PConnection(peer, this.displayName);
        this.chatPeers.put(peer, chatPeer);
    }
    public void removeConnection(RemotePeer peer) {
        this.chatPeers.remove(peer);
    }

}
