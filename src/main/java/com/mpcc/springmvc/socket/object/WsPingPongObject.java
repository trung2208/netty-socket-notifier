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
public class WsPingPongObject {
    
    int ping;
    String cur_time;

    public WsPingPongObject() {
    }

    public int getPing() {
        return ping;
    }

    public void setPing(int ping) {
        this.ping = ping;
    }

    public String getCur_time() {
        return cur_time;
    }

    public void setCur_time(String cur_time) {
        this.cur_time = cur_time;
    }
    
}
