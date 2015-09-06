package wizard.mdap.client;

import wizard.mdap.CodeMap;
import wizard.mdap.server.UdpListener;

import java.io.*;
import wizard.mdap.dao.RouterSettingsDao;
import wizard.mdap.util.Commands;
import wizard.mdap.util.Ports;

public class Client {    
    private final String defaultUsername = "dsath";
    private final String defaultPassword = "";
    
    Thread senderThread;
    UdpSender sender;

        
    public void connect(String username, String password) throws IOException { 
                
        RouterSettingsDao.settings.put("USER-ID", username);                
        RouterSettingsDao.settings.put("USER-PWD", password);
    
        Commands.list.put("SEARCH",new CodeMap("ANT-SEARCH MDAP/1.1"));
        Commands.list.put("EXEC-CLI", new CodeMap("EXEC-CLI MDAP/1.2","CLI-CMD:service system ipadd name TELNET ip 192.168.1.0/24",1,true));
        //Commands.list.put("EXEC-CLI", new CodeMap("EXEC-CLI MDAP/1.2","CLI-CMD:xdsl debug bitloadinginfo",1,true));
        Commands.list.put("EXEC-CLI2", new CodeMap("EXEC-CLI MDAP/1.2",2,true));
        //Commands.list.put("INFO-EXPANDED","INFO MDAP/1.2\r\nSEQ-NR:1\r\nTO-ANT:1208RA82F\r\nUSER-ID:\r\nUSER-PWD:\r\n0C");
        Commands.list.put("INFO-EXPANDED", new CodeMap("INFO MDAP/1.2",1,true));
        Commands.list.put("INFO-EXPANDED2", new CodeMap("INFO MDAP/1.2",2,true));
        //Commands.list.put("BITLOADINGINFO","EXEC-CLI MDAP/1.2\r\nCLI-CMD:xdsl debug bitloadinginfo\r\nSEQ-NR:1\r\nTO-ANT:1208RA82F\r\nUSER-ID:gsf\r\nUSER-PWD:\r\n1F");
        //Commands.list.put("EXEC-CLI MDAP/1.2\r\nSEQ-NR:2\r\nTO-ANT:1208RA82F\r\nUSER-ID:gsf\r\nUSER-PWD:\r\n5F");
        //Commands.list.put("INFO MDAP/1.2\r\nSEQ-NR:1\r\nTO-ANT:1208RA82F\r\nUSER-ID:gsf\r\nUSER-PWD:\r\n0F");
        //Commands.list.put("INFO MDAP/1.2\r\nSEQ-NR:1\r\nTO-ANT:1208RA82F\r\nUSER-ID:gsf\r\nUSER-PWD:\r\n0E");
        
        
        sender = new UdpSender(Commands.list.get("SEARCH").genCommand());
        

        senderThread = new Thread(sender);
        senderThread.start();
    }
    public void send(String command) throws IOException {
        if (!senderThread.isAlive()) {
            sender = new UdpSender(Commands.list.get(command).genCommand());
            senderThread = new Thread(sender);
        }
    }
    
}