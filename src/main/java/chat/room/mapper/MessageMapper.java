package chat.room.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import chat.room.model.MessageEntity;
import chat.room.request.MessagePostRequest;
import chat.room.response.MessageGetResponse;
import chat.room.response.object.IncomingMessage;
import chat.room.response.object.OutcomingMessage;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MessageMapper {

	@Mappings({
		@Mapping(target="messageId", ignore = true),		
		@Mapping(target="sender", source="messagePostRequest.sender"),
		@Mapping(target="receiver", source="messagePostRequest.receiver"),
		@Mapping(target="subject", source="messagePostRequest.subject"),
		@Mapping(target="content", source="messagePostRequest.content"),
		@Mapping(target="sentDate", ignore = true)
	})
	MessageEntity messagePostRequestToMessageEntity(MessagePostRequest messagePostRequest);
	
	IncomingMessage messageEntityToIncomingMessage(MessageEntity messageEntity);

	OutcomingMessage messageEntityToOutcomingMessage(MessageEntity messageEntity);

	MessageGetResponse messageEntityToMessageGetResponse(MessageEntity messageEntity);
}
