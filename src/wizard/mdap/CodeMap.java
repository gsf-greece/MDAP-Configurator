/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wizard.mdap;

import wizard.mdap.dao.RouterSettingsDao;

/**
 * Creates a code that is being sent at the end of a UDP message for verification.
 * @author Luciddream
 */
public class CodeMap {

    private String command;
    private String subCommand;
    private boolean toAnt;
    private boolean secure;
    private int seq;

    /**
     * Default Constructor.
     */
    public CodeMap() {
        secure = false;
        seq = 0;
        toAnt = true;
    }

    /**
     * Creates a CodeMap instance for a specific command. 
     * @param command 
     */
    public CodeMap(String command) {
        this.command = command;

        if ("ANT-SEARCH MDAP/1.1".equals(this.command)) {
            toAnt = false;
        }
    }

    public CodeMap(String command, int seq, boolean secure) {
        this.command = command;
        this.seq = seq;
        this.secure = secure;
        this.toAnt = true;
    }
    public CodeMap(String command, String subCommand, int seq, boolean secure) {
        this.command = command;
        this.seq = seq;
        this.secure = secure;
        this.toAnt = true;
        this.subCommand = subCommand;
    }

    private String getXoredString(String input) {
        char[] inputArray = input.toCharArray();
        int xor = 0;
        for (char a : inputArray) {
            xor = xor ^ a;
        }
        return String.format("%02X", xor);
    }

    public String genCommand() {
        StringBuilder a = new StringBuilder();
        a.append(command).append("\r\n");
        if (subCommand != null) {
            a.append(subCommand).append("\r\n");
        }
        if (seq > 0) {
            a.append("SEQ-NR:").append(seq).append("\r\n");
        }
        if (toAnt) {
            a.append("TO-ANT:").append(RouterSettingsDao.settings.get("ANT-ID")).append("\r\n");
        }
        if (secure) {
            a.append("USER-ID:").append(RouterSettingsDao.settings.get("USER-ID")).append("\r\n").append("USER-PWD:").append(RouterSettingsDao.settings.get("USER-PWD")).append("\r\n");
        }
        a.append(getXoredString(a.toString()));
        return a.toString();
    }
}
