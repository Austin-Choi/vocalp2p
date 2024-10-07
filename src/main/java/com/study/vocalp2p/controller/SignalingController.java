package com.study.vocalp2p.controller;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.study.vocalp2p.service.RoomService;
import com.study.vocalp2p.util.RoomStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class SignalingController {
    private final SocketIOServer server;
    private final RoomService roomService;
    private final Logger SignalingLogger = LoggerFactory.getLogger(SignalingController.class);
    private final String ROOM_ID = "roomId";

    public SignalingController(SocketIOServer server,
                               RoomService roomService){
        this.server = server;
        this.roomService = roomService;
    }

    @OnEvent("join")
    public void onJoin(SocketIOClient client, Map<String, Object> data){
        String roomId = data.get("roomId").toString();
        client.joinRoom(roomId);
        SignalingLogger.info("Client joind room: {}", roomId);
    }

    @OnEvent("offer")
    public void onOffer(SocketIOClient client, Map<String, Object> data){
        server.getRoomOperations(data.get(ROOM_ID).toString())
                .sendEvent("offer", data);
    }

    @OnEvent("answer")
    public void onAnswer(SocketIOClient client, Map<String, Object> data){
        String roomId = data.get(ROOM_ID).toString();
        server.getRoomOperations(roomId).sendEvent("answer", data);
        // sdp 교환이 완료되었으므로 DB에 상태 업데이트
        roomService.updateRoomStatus(roomId, RoomStatus.INCALL.getLabel());
    }

    @OnEvent("ice-candidate")
    public void onIceCandidate(SocketIOClient client, Map<String, Object> data){
        server.getRoomOperations(data.get(ROOM_ID).toString())
                .sendEvent("ice-candidate", data);
    }
}
