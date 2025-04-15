package com.hnptech.monitor.core.web;

import com.hnptech.monitor.core.model.ApiMetrics;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hnptech.monitor.core.storage.ConcurrentHashMapStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class MetricsWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private ConcurrentHashMapStorage metricsStorage;

    private final ObjectMapper mapper = new ObjectMapper();
    private final CopyOnWriteArraySet<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        System.out.println("새로운 WebSocket 연결이 생성되었습니다. 현재 연결 수: " + sessions.size());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
        sessions.remove(session);
        System.out.println("WebSocket 연결이 종료되었습니다. 현재 연결 수: " + sessions.size());
    }

    // 1초마다 모든 연결된 클라이언트에게 메트릭 데이터를 전송
    @Scheduled(fixedRate = 1000)
    public void sendMetrics() {
        if (sessions.isEmpty()) {
            return;
        }
        
        Map<String, ApiMetrics> metrics = metricsStorage.getAllMetrics();
        String payload;
        try {
            payload = mapper.writeValueAsString(metrics);
            TextMessage message = new TextMessage(payload);
            
            for (WebSocketSession session : sessions) {
                if (session.isOpen()) {
                    session.sendMessage(message);
                }
            }
            System.out.println("메트릭 데이터 전송 완료. 연결된 세션 수: " + sessions.size());
        } catch (Exception e) {
            System.err.println("메트릭 데이터 전송 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
