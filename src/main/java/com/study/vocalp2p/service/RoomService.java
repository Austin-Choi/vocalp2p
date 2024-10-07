package com.study.vocalp2p.service;

import com.study.vocalp2p.entity.Room;
import com.study.vocalp2p.exception.BaseException;
import com.study.vocalp2p.exception.ErrorCode;
import com.study.vocalp2p.repository.RoomRepository;
import com.study.vocalp2p.util.RoomStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    public Room createRoom(String callerEmail){
        String roomId = UUID.randomUUID().toString();
        Room newRoom = Room.builder()
                .roomId(roomId)
                .callerEmail(callerEmail)
                .status(RoomStatus.ACTIVE.getLabel())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        return roomRepository.save(newRoom);
    }

    public Room getRoomByRoomId(String roomId){
        return roomRepository.findByRoomId(roomId)
                .orElseThrow(()->new BaseException(ErrorCode.ROOM_NOT_FOUND));
    }

    // Refactoring needed!! newStatus -> using Enum RoomStatus.
    public Room updateRoomStatus(String roomId, String newStatus){
        Room room = getRoomByRoomId(roomId); // throws ROOM_NOT_FOUND
        Room updatedRoom = room.toBuilder()
                .status(newStatus)
                .updatedAt(new Date())
                .build();
        return roomRepository.save(updatedRoom);
    }

    public void deleteRoom(String roomId){
        roomRepository.deleteById(roomId);
    }
}
