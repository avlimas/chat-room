package chat.room.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import chat.room.model.MessageEntity;
import chat.room.response.MessageGetResponse;
import chat.room.response.object.IncomingMessage;
import chat.room.response.object.OutcomingMessage;

@SpringBootTest
public class MessageMapperTest {

	@Autowired
    MessageMapper messageMapper;
	
	@Test
	void validateIfMapperInputIsNull() {
		IncomingMessage incomingMessage = messageMapper.messageEntityToIncomingMessage(null);
		OutcomingMessage outcomingMessage = messageMapper.messageEntityToOutcomingMessage(null);
		MessageGetResponse messageGetResponse = messageMapper.messageEntityToMessageGetResponse(null);
		MessageEntity messageEntity = messageMapper.messagePostRequestToMessageEntity(null);
		
		assertThat(incomingMessage).isEqualTo(null);
		assertThat(outcomingMessage).isEqualTo(null);
		assertThat(messageGetResponse).isEqualTo(null);
		assertThat(messageEntity).isEqualTo(null);
	}
}
