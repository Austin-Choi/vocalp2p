package com.study.vocalp2p.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignalMessage {
    private String type; // offer, answer, candidate
    private String sdp; // SDP (Offer/Answer)
    private String candidate; // ICE candidate
}
