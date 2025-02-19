package org.asi.chatservice.dto.mapper;

import org.asi.chatservice.domain.FriendChat;
import org.asi.chatservice.dto.FriendChatDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.List;

@Mapper(componentModel = "spring", uses = ChatProfileMapper.class)
public interface FriendChatMapper {

    @Named("mapToFriendChatWithIdChatWith")
    @Mapping(target = "chatWith", expression = "java(convertChatWithToId(friendChat.getChatWith()))")
    @Mapping(target = "recipient", qualifiedByName = "chatProfileToChatProfileDTO")
    FriendChatDTO mapToFriendChatDTO(FriendChat friendChat);

    @IterableMapping(qualifiedByName = {"mapToFriendChatWithIdChatWith"})
    List<FriendChatDTO> mapToFriendChatList(List<FriendChat> friendChats);

    default Long convertChatWithToId(FriendChat chatWith) {
        return chatWith.getId();
    }

}
