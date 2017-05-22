/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpcc.springmvc.configuration;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class ServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    public static List<ChannelHandlerContext> ctxs = Collections
            .synchronizedList(new ArrayList<ChannelHandlerContext>());

    public static Map<String, String> mapMute = new HashMap<String, String>();

    
    
    @Override
    protected void channelRead0(ChannelHandlerContext chc,
            TextWebSocketFrame txtMsg) throws Exception {

        String messReceived = txtMsg.text();

    }

    @Override
    public  void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx); //To change body of generated methods, choose Tools | Templates.
        ctxs.remove(ctx);
    }

    @Override
    public  void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx); //To change body of generated methods, choose Tools | Templates.
        ctxs.add(ctx);
    }

}
