package com.hengtian.pcmm.communication.mina.dispatcher;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import com.hengtian.pcmm.communication.protobuf.GooglebufUtil;

public class DefaultMessageDispatcher implements IMessageDispatcher {
	private Map<String, IoSession> sessionCache;

	public DefaultMessageDispatcher() {
		sessionCache = new ConcurrentHashMap<String, IoSession>();
	}

	public void addSession(IoSession session) {
		String key = getKey(session);
		this.sessionCache.put(key, session);
	}

	public void removeSession(IoSession session) {
		String key = getKey(session);
		this.sessionCache.remove(key);
	}

	public IoSession getSession(String key) {
		return sessionCache.get(key);
	}

	public Collection<IoSession> getIoSessions() {
		return sessionCache.values();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> void sendMessage(IoSession session, Integer msgType, T t) {
		Class<T> cls = (Class<T>) t.getClass();
		byte[] bytes = GooglebufUtil.processEncode(t, cls);
		IoBuffer ioBuffer = IoBuffer.allocate(bytes.length + 8);
		ioBuffer.putInt(bytes.length);
		ioBuffer.putInt(msgType);
		ioBuffer.put(bytes);
		ioBuffer.flip();
		session.write(ioBuffer);
	}

	@Override
	public <T> void send2All(Integer msgType, T t) {
		Collection<IoSession> sessions = getIoSessions();
		for (IoSession ioSession : sessions) {
			sendMessage(ioSession, msgType, t);
		}
	}

	public static String getKey(String ip, int port) {
		StringBuilder sb = new StringBuilder(128);
		sb.append(ip);
		sb.append(":");
		sb.append(port);
		return sb.toString();
	}

	public static String getKey(IoSession session) {
		InetSocketAddress address = (InetSocketAddress) session.getRemoteAddress();
		return getKey(address.getAddress().getHostAddress(), address.getPort());
	}
}
