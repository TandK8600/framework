package com.TandK.core.netty;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

@Slf4j
@Component
public class WSServer {

	private static class SingletionWSServer{
		static final WSServer instance = new WSServer();
	}
	
	public static WSServer getInstance() {
		return SingletionWSServer.instance;
	}
	
	private EventLoopGroup mainGroup;
	private EventLoopGroup subGroup;
	private ServerBootstrap server;
	private ChannelFuture future;
	
	public WSServer() {
		mainGroup = new NioEventLoopGroup();
		subGroup = new NioEventLoopGroup();
		server = new ServerBootstrap();
		server.group(mainGroup, subGroup)
			.channel(NioServerSocketChannel.class)
			.childHandler(new WSServerInitialzer());
	}
	
	public void start() {
		this.future = server.bind(8088);
		log.info("netty websocket server启动完毕");
	}
}
