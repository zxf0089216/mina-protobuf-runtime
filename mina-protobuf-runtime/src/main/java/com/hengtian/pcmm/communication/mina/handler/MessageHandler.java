package com.hengtian.pcmm.communication.mina.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hengtian.pcmm.communication.exception.MissingDispatcherException;
import com.hengtian.pcmm.communication.mina.dispatcher.IMessageDispatcher;
import com.hengtian.pcmm.communication.mina.processor.IMessageProcessor;

public class MessageHandler extends IoHandlerAdapter {
	private Map<Integer, IMessageProcessor> messageProcessorCache;
	private IMessageDispatcher messageDispatcher;
	Logger log = LoggerFactory.getLogger(MessageHandler.class);

	public MessageHandler() {
		this.messageProcessorCache = new HashMap<Integer, IMessageProcessor>();
	}

	public void setMessageDispatcher(IMessageDispatcher messageDispatcher) {
		this.messageDispatcher = messageDispatcher;
	}

	public IMessageDispatcher getMessageDispatcher() {
		return this.messageDispatcher;
	}

	public void putMessageProcessor(Integer messageType, IMessageProcessor messageProcessor) {
		messageProcessorCache.put(messageType, messageProcessor);
	}

	public void sessionCreated(IoSession session) throws Exception {
		log.debug("session created");
	}

	public void sessionOpened(IoSession session) throws Exception {
		log.debug("session opened");
		checkMessageDispatcher();
		messageDispatcher.addSession(session);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		log.error(cause.getMessage(), cause);
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		checkMessageDispatcher();
		if (message instanceof IoBuffer) {
			IoBuffer ioBuffer = (IoBuffer) message;
			int lenth = ioBuffer.getInt();
			int msgType = ioBuffer.getInt();
			byte[] bytes = new byte[lenth];
			ioBuffer.get(bytes);
			IMessageProcessor messageProcessor = messageProcessorCache.get(msgType);
			messageProcessor.onMessage(session, bytes);
		}
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		checkMessageDispatcher();
		messageDispatcher.removeSession(session);
	}

	private void checkMessageDispatcher() {
		if (messageDispatcher == null) {
			throw new MissingDispatcherException(
					"setMessageDispatcher for the object " + this.getClass().getSimpleName());
		}
	}
}
