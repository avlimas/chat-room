package chat.room.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import chat.room.response.object.IncomingMessage;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "Response object for list of incoming messages of a user")
public class IncomingGetResponse {

	@JsonProperty("incomingMessageList")
	List<IncomingMessage> incomingMessageList;
}