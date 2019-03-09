package com.company;

import de.tum.in.www1.jReto.Connection;
import de.tum.in.www1.jReto.LocalPeer;
import de.tum.in.www1.jReto.module.wlan.WlanModule;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String args[])
    {
        System.out.println(Hash.getSHA256("omar"));
        try {
            WlanModule wlanModule = new WlanModule("ExampleType");
            LocalPeer localPeer = new LocalPeer(Arrays.asList(wlanModule), Executors.newSingleThreadExecutor());
            localPeer.start(
                    discoveredPeer -> System.out.println("Discovered peer: "+discoveredPeer),
                    removedPeer -> System.out.println("Removed peer: "+removedPeer),
                    (peer, incomingConnection) -> System.out.println("Received incoming connection: "+incomingConnection+" from peer: "+peer)
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
