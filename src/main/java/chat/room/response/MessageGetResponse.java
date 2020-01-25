package chat.room.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "Response object for message details")
public class MessageGetResponse {

	@ApiModelProperty(notes = "Identifier for id of the message")
	@JsonProperty("messageId")
	private String messageId;
	
	@ApiModelProperty(notes = "Identifier for sender")
	@JsonProperty("sender")
	private String sender;
	
	@ApiModelProperty(notes = "Identifier for receiver")
	@JsonProperty("receiver")
	private String receiver;

    @ApiModelProperty(notes = "Identifier for content")
	@JsonProperty("content")
	private String content;
    
    @ApiModelProperty(notes = "Identifier for sent date")
	@JsonProperty("sentDate")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime sentDate;
}
