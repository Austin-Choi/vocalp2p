package com.study.vocalp2p.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collation = "ko")
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    private String id; // mongoDB ObjectId
    private String roomId; // 방 고유 id(UUID)
    private String callerEmail;
    private String calleeEmail;
    private String status; // 방 상태 (ACTIVE, IN_CALL, CLOSED)
    private Date createdAt; // 방 생성 시간
    private Date updatedAt; // 방 상태가 마지막으로 업데이트된 시간
}
