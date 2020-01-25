package chat.room.response.object;

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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "Message object of an outcoming message")
public class OutcomingMessage {
	
	@ApiModelProperty(notes = "Identifier for receiver")
	@JsonProperty("receiver")
	private String receiver;

    @ApiModelProperty(notes = "Identifier for subject")
	@JsonProperty("subject")
	private String subject;
}
