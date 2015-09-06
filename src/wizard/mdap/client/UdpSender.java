/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wizard.mdap.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;
import wizard.mdap.util.Addresses;
import wizard.mdap.util.Commands;
import wizard.mdap.util.Ports;


/**
 *
 * @author Luciddream
 */
public class UdpSender implements Runnable {

    private static final Logger logger = LogManager.getLogger();    
    private DatagramPacket data;
    private final InetAddress group;
    private final MulticastSocket sock;
    private final String command;

    /**
     * Default MDAP client constructor.
     * @throws IOException 
     */
    public UdpSender(String command) throws IOException {
        this.group = InetAddress.getByName(Addresses.MULTICAST_IP);
        this.sock = new MulticastSocket(Ports.MDAP_PORT);
        this.command = command;
    }
    /**
     * Destroys connection to MDAP service.
     * @throws IOException 
     */
    private void stop() throws IOException {
        sock.leaveGroup(group);
        sock.close();
    }

    public void send(String input) {
        data = new DatagramPacket(input.getBytes(), input.length(),
                group, Ports.MDAP_PORT);
        logger.info("Sending Request: " + input);
        try {
            sock.send(data);
            Thread.sleep(2000);
        } catch (IOException | InterruptedException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void run() {
        try {
            sock.joinGroup(group);
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
            logger.info("[System]: Error, exiting now...");
            System.exit(-1);
        }
        logger.info("[System]: ready to send data.");
        send(command);
        try {
            Thread.sleep(2000); // TODO: This needs to be replaced.
        } catch (InterruptedException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
