package com.study.vocalp2p.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JoinRoomDTO {
    private String roomId;
    private String email;
}
