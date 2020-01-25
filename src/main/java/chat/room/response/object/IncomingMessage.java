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
@ApiModel(description = "Message object of an incoming message")
public class IncomingMessage {

	@ApiModelProperty(notes = "Identifier for sender")
	@JsonProperty("sender")
	private String sender;

    @ApiModelProperty(notes = "Identifier for subject")
	@JsonProperty("subject")
	private String subject;
}
