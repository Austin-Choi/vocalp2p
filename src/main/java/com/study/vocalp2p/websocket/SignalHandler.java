package com.study.vocalp2p.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;

public class SignalHandler extends TextWebSocketHandler {

    private static Logger signalHandlerLogger = LoggerFactory.getLogger(SignalHandler.class);
    // 이메일을 기준으로 세션을 저장
    private static ConcurrentHashMap<String, WebSocketSession> sessions
            = new ConcurrentHashMap<>();

    private String getTargetEmail(String payload){
        // 메시지에서 타겟 이메일 추출
        // 간단한 예시로, payload에서 "to" 필드로부터 추출
        // 실제 JSON Parsing 추후에 필요
        return payload.split(":")[1];
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{
        String email = session.getUri().getQuery().split("=")[1];
        sessions.put(email, session);
        signalHandlerLogger.info("Connected: " + email);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
       // 수신된 메시지를 다른 사용자에게 전달
       String targetEmail = getTargetEmail(message.getPayload());
       if(sessions.containsKey(targetEmail)){
           sessions.get(targetEmail).sendMessage(message);
       }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
        // 연결 종료시 세션 삭제
        String email = session.getUri().getQuery().split("=")[1];
        sessions.remove(email);
    }
}
