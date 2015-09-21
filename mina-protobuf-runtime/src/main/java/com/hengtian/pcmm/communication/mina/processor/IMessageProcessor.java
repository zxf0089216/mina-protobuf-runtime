package com.hengtian.pcmm.communication.mina.processor;

import org.apache.mina.core.session.IoSession;

public interface IMessageProcessor {
	void onMessage(IoSession session, Object obj);
}
