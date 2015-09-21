package com.hengtian.pcmm.communication.mina.dispatcher;

import java.util.Collection;

import org.apache.mina.core.session.IoSession;

public interface IMessageDispatcher {
	<T> void sendMessage(IoSession session, Integer msgType, T t);

	<T> void send2All(Integer msgType, T t);

	void addSession(IoSession session);

	void removeSession(IoSession session);

	Collection<IoSession> getIoSessions();
}
