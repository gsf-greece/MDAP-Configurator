/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizard.mdap.dao;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Luciddream
 */
public class RouterSettingsDao {
    public static Map<String,String> settings = new ConcurrentHashMap<>();
}
