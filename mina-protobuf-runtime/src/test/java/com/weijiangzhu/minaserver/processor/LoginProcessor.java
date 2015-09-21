package com.weijiangzhu.minaserver.processor;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hengtian.pcmm.communication.mina.processor.MessageProcessor;
import com.weijiangzhu.minaserver.entity.User;

public class LoginProcessor extends MessageProcessor<User> {
	Logger log = LoggerFactory.getLogger(LoginProcessor.class);
	@Override
	protected void processMessage(IoSession session, User message) {
		log.debug("start to process LoginRequest");
	}
}
