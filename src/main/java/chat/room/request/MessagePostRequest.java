package chat.room.request;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "POST request object for send a message")
public class MessagePostRequest {
	
    @ApiModelProperty(notes = "Identifier for sender")
	@NotBlank(message = "Sender field is mandatory")
	@JsonProperty("sender")
	private String sender;
    
    @ApiModelProperty(notes = "Identifier for receiver")
	@NotBlank(message = "Receiver field is mandatory")
	@JsonProperty("receiver")
	private String receiver;

    @ApiModelProperty(notes = "Identifier for subject")
	@NotBlank(message = "Message's subject is mandatory")
	@JsonProperty("subject")
	private String subject;

    @ApiModelProperty(notes = "Identifier for content")
	@NotBlank(message = "Content of message is mandatory")
	@JsonProperty("content")
	private String content;
}
