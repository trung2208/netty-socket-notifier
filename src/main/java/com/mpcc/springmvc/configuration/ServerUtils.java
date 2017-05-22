/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpcc.springmvc.configuration;

import com.google.gson.Gson;
import com.mpcc.springmvc.fb.model.MessPoJo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import java.util.Collections;

/**
 *
 * @author halv
 */
public class ServerUtils {

    public static void sendMessages(Object objEvent) {

        System.out.println(ServerHandler.ctxs);

        if (Collections.synchronizedList(ServerHandler.ctxs) != null && !Collections.synchronizedList(ServerHandler.ctxs).isEmpty()) {
            for (ChannelHandlerContext ctx : ServerHandler.ctxs) {
                AttributeKey<String> aKey = AttributeKey.valueOf(objEvent.toString());
                if (null == ctx.channel().attr(aKey).get()) {
                    ctx.channel().writeAndFlush(new TextWebSocketFrame(
                            String.valueOf(objEvent)));
                    ctx.channel().attr(aKey).setIfAbsent(objEvent.toString());
                }
            }
        }
    }
}
