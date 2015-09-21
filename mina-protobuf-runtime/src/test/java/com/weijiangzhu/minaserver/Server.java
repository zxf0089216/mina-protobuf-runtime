package com.weijiangzhu.minaserver;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.junit.Before;
import org.junit.Test;

import com.hengtian.pcmm.communication.mina.dispatcher.DefaultMessageDispatcher;
import com.hengtian.pcmm.communication.mina.dispatcher.IMessageDispatcher;
import com.hengtian.pcmm.communication.mina.handler.MessageHandler;
import com.weijiangzhu.minaserver.messageType.MessageType;
import com.weijiangzhu.minaserver.processor.UserProcessor;

public class Server {
	@Before
	public void before() {

	}

	@Test
	public void serverTest() {

	}

	public static void main(String[] args) throws IOException, InterruptedException {
		IoAcceptor acceptor = new NioSocketAcceptor();
		MessageHandler messageHandler = new MessageHandler();
		IMessageDispatcher messageDispatcher = new DefaultMessageDispatcher();
		UserProcessor userProcessor = new UserProcessor();
		messageHandler.setMessageDispatcher(messageDispatcher);
		userProcessor.setMessageDispatcher(messageDispatcher);
		messageHandler.addMessageProcessor(MessageType.USERINFO, userProcessor);
		acceptor.setHandler(messageHandler);
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		acceptor.bind(new InetSocketAddress(9623));
	}
}
