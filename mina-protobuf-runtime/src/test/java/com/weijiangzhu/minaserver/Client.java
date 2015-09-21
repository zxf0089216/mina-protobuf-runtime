package com.weijiangzhu.minaserver;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.hengtian.pcmm.communication.mina.dispatcher.DefaultMessageDispatcher;
import com.hengtian.pcmm.communication.mina.dispatcher.IMessageDispatcher;
import com.hengtian.pcmm.communication.mina.handler.MessageHandler;
import com.weijiangzhu.minaserver.entity.K5Car;
import com.weijiangzhu.minaserver.entity.User;
import com.weijiangzhu.minaserver.messageType.MessageType;
import com.weijiangzhu.minaserver.processor.UserProcessor;

public class Client {
	public static void main(String[] args) {
		NioSocketConnector connector = new NioSocketConnector();
		connector.setConnectTimeoutMillis(3000);
		MessageHandler messageHandler = new MessageHandler();
		IMessageDispatcher messageDispatcher = new DefaultMessageDispatcher();
		UserProcessor userProcessor = new UserProcessor();
		messageHandler.setMessageDispatcher(messageDispatcher);
		userProcessor.setMessageDispatcher(messageDispatcher);
		messageHandler.addMessageProcessor(MessageType.USERINFO, userProcessor);
		connector.setHandler(messageHandler);
		IoSession session;
		for (;;) {
			try {
				ConnectFuture future = connector.connect(new InetSocketAddress("127.0.0.1", 9623));
				future.awaitUninterruptibly();
				session = future.getSession();

				List<K5Car> cars = new ArrayList<K5Car>();
				cars.add(new K5Car("K3", "china"));
				cars.add(new K5Car("k5", "usa"));
				User user = new User(1, cars);

				messageDispatcher.sendMessage(session, MessageType.USERINFO, user);
				break;
			} catch (RuntimeIoException e) {
				e.printStackTrace();
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		try {
			Thread.sleep(50000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// wait until the summation is done
		session.getCloseFuture().awaitUninterruptibly();
		connector.dispose();
	}
}
