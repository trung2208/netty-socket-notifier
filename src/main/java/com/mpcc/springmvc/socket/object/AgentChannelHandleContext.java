/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpcc.springmvc.socket.object;

import com.mpcc.springmvc.configuration.Constants;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 24h
 */
public class AgentChannelHandleContext {

    ChannelHandlerContext context;
    PingPongObject pingPongObject;

    public AgentChannelHandleContext() {
        pingPongObject = new PingPongObject();
    }

    public AgentChannelHandleContext(ChannelHandlerContext channel) {
        this.context = channel;
        pingPongObject = new PingPongObject();
    }

    public ChannelHandlerContext getChannel() {
        return context;
    }

    public void setChannel(ChannelHandlerContext channel) {
        this.context = channel;
    }

    public PingPongObject getPingPongObject() {
        return pingPongObject;
    }

    public void setPingPongObject(PingPongObject pingPongObject) {
        this.pingPongObject = pingPongObject;
    }

    public void doPing(String user) {
//        try {
        Date date = new Date();
        WsTranferObject object = new WsTranferObject();
        object.setType("BUZZ");
        object.setUser(user);
        WsPingPongObject wppo = new WsPingPongObject();
        pingPongObject.increasePing();
        wppo.setPing(pingPongObject.getPing_count());
        wppo.setCur_time(Constants.DATE_FORMAT.format(date));
        if (object.getEntry() == null) {
            object.setEntry(new WsEntryObject[]{new WsEntryObject()});
        }
        object.getEntry()[0].setPingPong(wppo);
        context.channel().writeAndFlush(new TextWebSocketFrame(Constants.GSON.toJson(object)));
        // ChannelFuture cf = future.await();
//            if (!cf.isDone() || !cf.isSuccess()) {
//
//                Logger.getLogger(AgentChannelHandleContext.class.getName()).log(Level.SEVERE, null, "error while send to client " + context.channel().remoteAddress());
//                context.fireChannelInactive();
//            }
//        } catch (InterruptedException ex) {
//            Logger.getLogger(AgentChannelHandleContext.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public boolean removePing(int ping) {
        return pingPongObject.removePing(ping);
    }

    public boolean sendToChannel(WsTranferObject tranfer) {
        try {
            ChannelFuture cf = context.channel().writeAndFlush(Constants.GSON.toJson(tranfer)).sync();
            cf.await();
            if (!cf.isDone() || !cf.isSuccess()) {

                Logger.getLogger(AgentChannelHandleContext.class.getName()).log(Level.SEVERE, null, "error while send to client " + context.channel().remoteAddress());
                context.fireChannelInactive();
                return false;
            }
            return true;
        } catch (InterruptedException ex) {
            Logger.getLogger(AgentChannelHandleContext.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean isDisconnected() {
        return pingPongObject.isDisconnected();
    }
}
