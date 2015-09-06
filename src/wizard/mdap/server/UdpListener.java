/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wizard.mdap.server;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import wizard.mdap.client.Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.logging.Level;
import wizard.mdap.dao.RouterSettingsDao;
import wizard.mdap.util.Addresses;




/**
 * Sets up the UDP listener.
 * @author Luciddream
 */
public class UdpListener implements Runnable {

    private static final Logger logger = LogManager.getLogger();
    private final byte[] buf;
    private final MulticastSocket sock;
    private DatagramPacket recv;
    private final InetAddress group;
    private final InetAddress routerAddress;
    private final int port;

    /** 
     * Listens for broadcast messages.
     * 
     */
    public UdpListener(int port) throws IOException {
        this.port = port;
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            logger.info("Network Interface: " + interfaces.nextElement());
        }

        group = InetAddress.getByName(Addresses.MULTICAST_IP);
        routerAddress = InetAddress.getByName(Addresses.SERVER_IP);
        sock = new MulticastSocket(port);
        sock.setNetworkInterface(NetworkInterface.getByName("eth1"));
        sock.joinGroup(group);
        buf = new byte[1500];
    }
    
    @Override
    public void run() {
        recv = new DatagramPacket(buf, buf.length);
        try {
            logger.info("[System]: ready to listen.");
            while (!Thread.currentThread().isInterrupted()) {
                sock.receive(recv);
                //System.out.println("Received from: " + recv.getAddress());
                if (recv.getAddress().equals(routerAddress)) {
                    String received = new String(recv.getData(), 0, recv.getLength());
                    // Commands Logging
                    logger.info("Received: " + received);
                    String[] routerData = received.split("\r\n");
                    for (String a : routerData) {
                        String[] tempData = a.split(":",2);
                        if (tempData.length > 1) {
                            RouterSettingsDao.settings.put(tempData[0], tempData[1]);
                            logger.info("Updated " + tempData[0] + "Property");
                        }
                    }
                }
            }
          stop();
          logger.info("MDAP Listener has been stopped");
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            try {
                stop();
            } catch (IOException ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
    }
    private void stop() throws IOException {
        sock.leaveGroup(group);
        sock.close();
    }
    
}
