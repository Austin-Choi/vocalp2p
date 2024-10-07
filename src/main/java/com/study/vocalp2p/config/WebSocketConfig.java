package com.study.vocalp2p.config;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// SocketIOServer를 Spring application의 bean으로 정의해야
// SignalingController에서 사용할 수 있음
@Configuration
public class WebSocketConfig {
    @Value("${socket.host}")
    private String host;

    @Value("${socket.port}")
    private int port;

    @Value("${socket.origin}")
    private String origin;

    @Bean
    public SocketIOServer socketIOServer() throws Exception{
        com.corundumstudio.socketio.Configuration config =
                new com.corundumstudio.socketio.Configuration();
        config.setHostname(host);
        config.setPort(port);
        config.setOrigin(origin);
        return new SocketIOServer(config);
    }
}
