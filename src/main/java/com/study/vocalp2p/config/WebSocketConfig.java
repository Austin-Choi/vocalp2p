package com.study.vocalp2p.config;

import com.study.vocalp2p.websocket.SignalHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.*;

// Web Socket 활성화하고 EndPoint 설정
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){
        registry.addHandler(signalHandler(), "/ws")
                .setAllowedOrigins("http://localhost:5173");
    }

    @Bean
    public SignalHandler signalHandler(){
        return new SignalHandler();
    }
}
