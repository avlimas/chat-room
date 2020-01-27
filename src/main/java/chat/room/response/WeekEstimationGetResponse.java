package chat.room.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeekEstimationGetResponse {
	
	@JsonProperty("fromDate")
	private String fromDate;
	
	@JsonProperty("untilDate")
	private String untilDate;

	@JsonProperty("totalMessages")
	private Integer totalMessages;
}
