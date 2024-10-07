package com.study.vocalp2p.controller;

import com.study.vocalp2p.config.BaseResponse;
import com.study.vocalp2p.entity.Room;
import com.study.vocalp2p.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "http://localhost:3000")
public class RoomController {
    private final RoomService roomService;
    private final Logger restControllerLogger = LoggerFactory.getLogger(RoomController.class);

    public RoomController(RoomService roomService){
        this.roomService = roomService;
    }

    // Caller의 이메일을 받아 방을 생성하고 URL을 반환
    @PostMapping
    public BaseResponse<String> createRoom(@RequestParam("callerEmail") String callerEmail){
        Room newRoom = roomService.createRoom(callerEmail);
        return new BaseResponse<>("http://localhost:3000/room/" + newRoom.getRoomId());
    }

    @GetMapping("/{roomId}")
    public BaseResponse<Room> getRoom(@PathVariable String roomId){
        return new BaseResponse<>(roomService.getRoomByRoomId(roomId));
    }

    // update랑 delete는 webRTC 흐름에 따라 내부적으로 일어나기 때문에 controller에는 없음.
}
