package org.asi.chatservice.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestDTO {

    private Long id;
    private String sentTime;
    private ChatProfileDTO sender;
    private ChatProfileDTO recipient;
}
