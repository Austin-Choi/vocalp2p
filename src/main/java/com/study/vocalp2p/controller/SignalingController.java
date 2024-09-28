package com.study.vocalp2p.controller;

import com.study.vocalp2p.dto.SignalMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SignalingController {

    @MessageMapping("/send")
    @SendTo("/topic/response")
    public SignalMessage signaling(SignalMessage message){
        return message; // 수신한 시그널링 메시지를 그대로 반환하여 상대방에게 전달
    }

}
