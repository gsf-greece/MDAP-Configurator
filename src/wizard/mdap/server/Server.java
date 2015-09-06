/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizard.mdap.server;

import java.io.IOException;
import wizard.mdap.util.Ports;

/**
 *
 * @author Luciddream
 */
public class Server {
    Thread listenerThread;
    UdpListener listener;
    
    public Server() throws IOException {
        listener = new UdpListener(Ports.MDAP_PORT);
        listenerThread = new Thread(listener);
        listenerThread.start();
    }
    
    public void disconnect() {
        if (listenerThread.isAlive()) {
            listenerThread.interrupt();
        }
    }
}
