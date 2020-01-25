package chat.room.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "messages")
public class MessageEntity {

	@Id
	private String messageId;
	
	private String sender;
	
	private String receiver;
	
	private String subject;
	
	private String content;
	
	private LocalDateTime sentDate;
}
