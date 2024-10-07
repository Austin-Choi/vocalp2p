package com.study.vocalp2p.util;

import lombok.Getter;

@Getter
public enum RoomStatus {
    ACTIVE("ACTIVE"),
    INCALL("IN_CALL"),
    CLOSED("CLOSED");

    private final String label;

    RoomStatus(String label){
        this.label = label;
    }
}
