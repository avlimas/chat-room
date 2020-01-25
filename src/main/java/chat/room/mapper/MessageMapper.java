package chat.room.mapper;

import org.mapstruct.Mapper;

import chat.room.model.MessageEntity;
import chat.room.request.MessagePostRequest;
import chat.room.response.MessageGetResponse;
import chat.room.response.object.IncomingMessage;
import chat.room.response.object.OutcomingMessage;

@Mapper(componentModel = "spring")
public interface MessageMapper {

	MessageEntity messagePostRequestToMessageEntity(MessagePostRequest messagePostRequest);
	
	IncomingMessage messageEntityToIncomingMessage(MessageEntity messageEntity);

	OutcomingMessage messageEntityToOutcomingMessage(MessageEntity messageEntity);

	MessageGetResponse messageEntityToMessageGetResponse(MessageEntity messageEntity);
}
