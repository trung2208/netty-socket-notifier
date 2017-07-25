/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpcc.springmvc.socket.excuters;

import static com.google.appengine.api.search.query.ExpressionLexer.LOG;
import com.mpcc.springmvc.utils.FileUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.NetUtil;
import io.netty.util.concurrent.Future;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


/**
 *
 * @author Administrator
 */
//@Component
public class Start {

    static int PORT =2017;
    final static Logger logger = LoggerFactory.getLogger(Start.class);

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,NetUtil.SOMAXCONN)
                    //.option(ChannelOption.SO_KEEPALIVE, true)
                    //.childOption(ChannelOption.SO_TIMEOUT, 30000)
                    .childHandler(new ServerInitializer());

            Channel ch = b.bind(PORT).sync().channel();
            logger.debug("Web socket server started at port " + PORT + '.');
            logger.debug("Open your browser and navigate to http://localhost:" + PORT + '/');

            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
    @PreDestroy
    void shutdownWorkers(EventLoopGroup boss,EventLoopGroup worker) {
    Future fb = boss.shutdownGracefully();
    Future fw = worker.shutdownGracefully();
    try {
        fb.await();
        fw.await();
    } catch (InterruptedException ignore) {}
}
   

}