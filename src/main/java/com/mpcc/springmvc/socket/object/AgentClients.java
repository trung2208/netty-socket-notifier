/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpcc.springmvc.socket.object;

import com.mpcc.springmvc.utils.LookupUtils;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author 24h
 */
public class AgentClients extends HashMap<String, AgentChannels> {

    public static enum AgentState {
        CHANNEL_ONLINE,
        CLIENT_ONLINE,
        CHANNEL_DISCONNECT,
        CLIENT_OFFLINE,
        ERROR;

        public static AgentState lookup(String state) {
            return LookupUtils.Lookup(AgentState.class, state);
        }

    }

}
