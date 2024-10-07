package com.study.vocalp2p.controller;

import com.study.vocalp2p.config.BaseResponse;
import com.study.vocalp2p.dto.JoinRoomDTO;
import com.study.vocalp2p.entity.Room;
import com.study.vocalp2p.service.RoomService;
import com.study.vocalp2p.util.RoomStatus;
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

    /**
     * 방을 생성함
     * @param callerEmail
     * @return UUID가 포함된 고유한 방 URL 반환
     */
    @PostMapping
    public BaseResponse<String> createRoom(@RequestParam("callerEmail") String callerEmail){
        Room newRoom = roomService.createRoom(callerEmail);
        // navigate에서 상대결로로 설정해야 리다이렉션됨
        return new BaseResponse<>(newRoom.getRoomId());
    }

    /**
     * 방 정보 불러오기
     * @param roomId
     * @return 해당 id로 조회되는 room 객체 데이터
     */
    @GetMapping("/{roomId}")
    public BaseResponse<Room> getRoom(@PathVariable String roomId){
        return new BaseResponse<>(roomService.getRoomByRoomId(roomId));
    }

    // update랑 delete는 webRTC 흐름에 따라 내부적으로 일어나기 때문에 controller에는 없음.

    /**
     * RoomId, email과 함께 방 입장
     * @param joinRoomDTO
     * @return 현재 입장한 방 status
     */
    @PostMapping("/room/join")
    public BaseResponse<String> joinRoomByLink(@RequestBody JoinRoomDTO joinRoomDTO){
        Room updatedRoom = roomService.joinRoom(joinRoomDTO.getRoomId(), joinRoomDTO.getEmail());
        return new BaseResponse<>(
                roomService.updateRoomStatus(
                        updatedRoom.getRoomId(),
                        RoomStatus.INCALL.getLabel())
                        .getStatus());
    }
}
