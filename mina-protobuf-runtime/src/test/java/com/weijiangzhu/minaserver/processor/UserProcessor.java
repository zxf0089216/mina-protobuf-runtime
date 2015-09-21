package com.weijiangzhu.minaserver.processor;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hengtian.pcmm.communication.mina.processor.MessageProcessor;
import com.weijiangzhu.minaserver.entity.User;
import com.weijiangzhu.minaserver.messageType.MessageType;

public class UserProcessor extends MessageProcessor<User> {
	Logger log = LoggerFactory.getLogger(UserProcessor.class);

	@Override
	protected void processMessage(IoSession session, User user) {
		log.debug("Received:{}", user.toString());
		getMessageDispatcher().sendMessage(session, MessageType.USERINFO, user);
	}
}
