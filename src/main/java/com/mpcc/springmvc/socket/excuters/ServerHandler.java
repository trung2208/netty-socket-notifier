/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpcc.springmvc.socket.excuters;

import com.mpcc.springmvc.configuration.Constants;
import com.mpcc.springmvc.socket.object.AgentChannelHandleContext;
import com.mpcc.springmvc.socket.object.AgentChannels;
import com.mpcc.springmvc.socket.object.AgentClients;
import com.mpcc.springmvc.socket.object.PingPongObject;
import com.mpcc.springmvc.socket.object.WsTranferObject;
import com.mpcc.springmvc.utils.LookupUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Trung Nguyen Van
 */
public class ServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    public static List<ChannelHandlerContext> ctxs = Collections
            .synchronizedList(new ArrayList<ChannelHandlerContext>());

    final Logger logger = LoggerFactory.getLogger(ServerHandler.class);
    public static Map<String, String> mapMute = new HashMap<String, String>();
    public static AgentClients agents = new AgentClients();

    public static enum HandlerState {
        JOIN_QUEUE,
        LEAVE_QUEUE,
        BUZZ,
        BUZZED,
        ERROR;

        public static HandlerState lookup(String value) {
            return LookupUtils.Lookup(HandlerState.class, value);
        }

    }

    @Override
    protected void channelRead0(ChannelHandlerContext chc,
            TextWebSocketFrame txtMsg) throws Exception {
        AgentChannelHandleContext handleContext;
        WsTranferObject tranferObject;
        HandlerState state;
        String user = "";
        tranferObject = Constants.GSON.fromJson(txtMsg.text(), WsTranferObject.class);
        state = HandlerState.lookup(tranferObject.getType());
        user = tranferObject.getUser();
        int channel_hashcode = chc.hashCode();
        switch (state) {
            case JOIN_QUEUE:
                if (!agents.containsKey(user)) {
                    agents.put(user, new AgentChannels());
                }
                handleContext = new AgentChannelHandleContext(chc);
                agents.get(user).put(channel_hashcode, handleContext);
                chc.writeAndFlush("ok con de");
                break;
            case LEAVE_QUEUE:
                if (!agents.containsKey(user) || !agents.get(user).containsKey(channel_hashcode)) {
                    break;
                }
                agents.get(user).remove(channel_hashcode);
                if (agents.get(user).isEmpty()) {
                    System.out.println("Client_ offline");
                }

                break;
            case BUZZ:

                break;
            case BUZZED:
                if (user.trim().isEmpty() || !agents.containsKey(user) || !agents.get(user).containsKey(channel_hashcode)) {

                    //do something
                    break;
                }

                int pingged = tranferObject.getEntry()[0].getPingPong().getPing();
                agents.get(user).get(channel_hashcode).removePing(pingged);
                break;
            case ERROR:
                chc.writeAndFlush(txtMsg);
                break;
        }

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("remove " + ctx);
        super.channelInactive(ctx); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("add " + ctx);
        super.channelActive(ctx); //To change body of generated methods, choose Tools | Templates.
    }

}
