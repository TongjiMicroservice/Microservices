package com.tongji.microservice.teamsphere.dto.meetingservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantData  implements Serializable {
    int id;
    String name;
    String meeting_id;
    int participant_id;
    String role;
}
