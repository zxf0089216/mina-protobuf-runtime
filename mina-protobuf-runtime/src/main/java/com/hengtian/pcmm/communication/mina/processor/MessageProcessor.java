package com.hengtian.pcmm.communication.mina.processor;

import java.lang.reflect.ParameterizedType;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hengtian.pcmm.communication.mina.dispatcher.IMessageDispatcher;
import com.hengtian.pcmm.communication.protobuf.GooglebufUtil;

public abstract class MessageProcessor<TMessage> implements IMessageProcessor {
	Logger log = LoggerFactory.getLogger(MessageProcessor.class);
	private IMessageDispatcher messageDispatcher;

	public IMessageDispatcher getMessageDispatcher() {
		return messageDispatcher;
	}

	public void setMessageDispatcher(IMessageDispatcher messageDispatcher) {
		this.messageDispatcher = messageDispatcher;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onMessage(IoSession session, Object obj) {
		Class<TMessage> cls = (Class<TMessage>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		TMessage message = GooglebufUtil.processDecode((byte[]) obj, cls);
		log.debug("start to processMessage:<{}>", message.toString());
		processMessage(session, message);
	}

	protected abstract void processMessage(IoSession session, TMessage message);
}
