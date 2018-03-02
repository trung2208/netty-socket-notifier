/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpcc.springmvc.socket.object;

/**
 *
 * @author 24h
 */
public class WsTranferObject {
    String type;
    String user;
    WsEntryObject[] entry;

    public WsTranferObject() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public WsEntryObject[] getEntry() {
        return entry;
    }

    public void setEntry(WsEntryObject[] entry) {
        this.entry = entry;
    }
    
    
}
