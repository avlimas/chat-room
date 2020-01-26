package chat.room.request;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessagePostRequest {
	
	@NotBlank(message = "Sender field is mandatory")
	@JsonProperty("sender")
	private String sender;
    
	@NotBlank(message = "Receiver field is mandatory")
	@JsonProperty("receiver")
	private String receiver;

	@NotBlank(message = "Message's subject is mandatory")
	@JsonProperty("subject")
	private String subject;
	
	@NotBlank(message = "Content of message is mandatory")
	@JsonProperty("content")
	private String content;
}
