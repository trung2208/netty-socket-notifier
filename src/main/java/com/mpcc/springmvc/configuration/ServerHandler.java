/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpcc.springmvc.configuration;


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
 * @author Administrator
 */
public class ServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    public static List<ChannelHandlerContext> ctxs = Collections
            .synchronizedList(new ArrayList<ChannelHandlerContext>());

  final  Logger logger=LoggerFactory.getLogger(ServerHandler.class);
    public static Map<String, String> mapMute = new HashMap<String, String>();

    
    
    @Override
    protected void channelRead0(ChannelHandlerContext chc,
            TextWebSocketFrame txtMsg) throws Exception {

        String messReceived = txtMsg.text();

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming=ctx.channel();
        incoming.writeAndFlush("hello");
      //   ctxs.add(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
     //  ctxs.remove(ctx);
    }
    
    @Override
    public  void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctxs.remove(ctx);
        super.channelInactive(ctx); //To change body of generated methods, choose Tools | Templates.
        logger.debug("remove "+ctx);
    }

    @Override
    public  void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctxs.add(ctx);
        super.channelActive(ctx); //To change body of generated methods, choose Tools | Templates.
        logger.debug("add " + ctx);
    }

}
