/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpcc.springmvc.configuration;

import com.mpcc.springmvc.utils.FileUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import org.apache.log4j.Logger;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;


/**
 *
 * @author Administrator
 */

public class Start {

    static int PORT = Integer.valueOf(8585);
    final static Logger logger = Logger.getLogger(Start.class);
    
    public void run() throws Exception {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(boss, worker).
                    channel(NioServerSocketChannel.class).
                    childHandler(new ServerInitializer());
            Channel ch = server.bind(PORT).sync().channel();
            ch.closeFuture().sync();
        } finally {
//            boss.shutdownGracefully();
//            worker.shutdownGracefully();
            shutdownWorkers(boss,worker);
        }
    }
    
    void shutdownWorkers(EventLoopGroup boss,EventLoopGroup worker) {
    Future fb = boss.shutdownGracefully();
    Future fw = worker.shutdownGracefully();
    try {
        fb.await();
        fw.await();
    } catch (InterruptedException ignore) {}
}

}
