/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpcc.springmvc.socket.object;

import java.util.ArrayList;

/**
 *
 * @author 24h
 */
public class PingPongObject {

    int ping_count;
    ArrayList<Integer> ping_queue;

    public PingPongObject() {
        ping_count = 0;
        ping_queue = new ArrayList<Integer>();
    }

    public int increasePing() {
        ping_count++;
        ping_queue.add(ping_count);
        return ping_count;
    }

    public boolean removePing(int ping) {
        if (ping_queue.contains(ping)) {
            for (int i = 0; i < ping_queue.indexOf(ping); i++) {
                ping_queue.remove(i);
            }
            return true;
        }
        return false;
    }

    public boolean isDisconnected() {
        return ping_queue.size() > 4;
    }

    public int getPing_count() {
        return ping_count;
    }

    public void setPing_count(int ping_count) {
        this.ping_count = ping_count;
    }

    public ArrayList<Integer> getPing_queue() {
        return ping_queue;
    }

    public void setPing_queue(ArrayList<Integer> ping_queue) {
        this.ping_queue = ping_queue;
    }

}
