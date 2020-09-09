/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iutpj_server;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author HEROK
 */
public class DataPacket implements Serializable {

    private String userID;
    private String route;
    private final  HashMap<String,Object> payLoad;

    public DataPacket() {
        this.userID = null;
        this.route = null;
        this.payLoad = new HashMap();
    }

    public String getUserID() {
        return this.userID;
    }
    public void setUserID(String userID)
    {
        this.userID = userID;
    }

    public String getRoute() {
        return this.route;
    }
    public void setRoute(String route)
    {
        this.route = route;
    }

    public HashMap getPayLoad() {
        return this.payLoad;
    }
    public void addPayLoad(String property, Object data)
    {
        payLoad.put(property, data);
    }
    public Object getValue(String property)
    {
        return payLoad.get(property);
    }
}
