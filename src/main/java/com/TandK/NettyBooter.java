package com.TandK;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.TandK.core.netty.WSServer;

@Slf4j
@Component
public class NettyBooter implements ApplicationListener<ContextRefreshedEvent>{

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent() == null) {
			try {
				WSServer.getInstance().start();
			} catch (Exception e) {
				log.error("WSServer启动错误，此处错误出现在NettyBooter");
				e.printStackTrace();
			}
		}
		
	}

}
