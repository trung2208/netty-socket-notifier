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
public class WsEntryObject {

    WsPingPongObject pingPong;

    public WsEntryObject() {
    }

    public WsPingPongObject getPingPong() {
        return pingPong;
    }

    public void setPingPong(WsPingPongObject pingPong) {
        this.pingPong = pingPong;
    }

}
