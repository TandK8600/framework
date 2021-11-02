package com.TandK.core.netty;

import java.time.LocalDateTime;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

/**
 * 处理消息的handler
 * 在netty中，是用于为websocket专门处理文本的对象，frame是消息的载体
 * @author TandK
 *
 */
@Slf4j
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{

	// 用于记录和管理所有的channel
	private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		
		// 获取客户端传输过来的消息
		String content = msg.text();
		log.info("接受的数据：{}", content);
		
		// 相当于clients中的所有channel都使用了writeAndFlush()
		clients.writeAndFlush(new TextWebSocketFrame(LocalDateTime.now() + ":" + content));
	}

	/**
	 * 当客户端连接服务端之后
	 * 获取客户端的channel，并放到clients中
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		clients.add(ctx.channel());
	}
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		log.info("客户端断开，channel对应的长id为{}", ctx.channel().id().asLongText());
		log.info("客户端断开，channel对应的短id为{}", ctx.channel().id().asShortText());
		// 当触发当前方法时，可以将当前channel从clients中移除
		// clients.remove(ctx.channel());
	}
}
