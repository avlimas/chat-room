package chat.room.controller;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import chat.room.request.MessagePostRequest;
import chat.room.response.DayEstimationGetResponse;
import chat.room.response.IncomingGetResponse;
import chat.room.response.MessageGetResponse;
import chat.room.response.OutcomingGetResponse;
import chat.room.response.WeekEstimationGetResponse;
import chat.room.service.ChatService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatController {

	private final ChatService chatService;
	
    @PostMapping(value= "/send-message", consumes = "application/json")
    public ResponseEntity<String> sendMessage(
    		@Valid @RequestBody MessagePostRequest messagePostRequest)
    {
    	this.chatService.sendMessage(messagePostRequest);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Location", "/api/v1/chat");
        return ResponseEntity.status(HttpStatus.CREATED).headers(responseHeaders).build();
    }
	
    @GetMapping("/incoming-messages")
    public ResponseEntity<IncomingGetResponse> getIncomingMessages(@RequestParam String receiver)
    {
		IncomingGetResponse incomingGetResponse = this.chatService.getIncomingMessages(receiver);
        if (incomingGetResponse != null) {
            return ResponseEntity.status(HttpStatus.OK).body(incomingGetResponse);
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
    }

    @GetMapping("/outcoming-messages")
    public ResponseEntity<OutcomingGetResponse> getOutcomingMessages(@RequestParam String sender)
    {
		OutcomingGetResponse outcomingGetResponse = this.chatService.getOutcomingMessages(sender);
		if (outcomingGetResponse != null) {
	        return ResponseEntity.status(HttpStatus.OK).body(outcomingGetResponse);
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
    }
	
    @GetMapping("/message-details")
    public ResponseEntity<MessageGetResponse> getMessageDetails(@RequestParam String subject)
    {
		MessageGetResponse messageGetResponse = this.chatService.getMessageDetails(subject);
		if (messageGetResponse != null) {
	        return ResponseEntity.status(HttpStatus.OK).body(messageGetResponse);
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
    }
    
    @GetMapping("/estimate-messages-today")
    public ResponseEntity<DayEstimationGetResponse> getTotalMessagesDaily()
    {
    	DayEstimationGetResponse dayEstimationGetResponse = this.chatService.getTotalMessagesToday();
		if (dayEstimationGetResponse != null) {
	        return ResponseEntity.status(HttpStatus.OK).body(dayEstimationGetResponse);
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
    }
    
    @GetMapping("/estimate-messages-weekly")
    public ResponseEntity<WeekEstimationGetResponse> getTotalMessagesWeekly()
    {
    	WeekEstimationGetResponse weekEstimationGetResponse = this.chatService.getTotalMessagesWeekly();
		if (weekEstimationGetResponse != null) {
	        return ResponseEntity.status(HttpStatus.OK).body(weekEstimationGetResponse);
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
    }
}
