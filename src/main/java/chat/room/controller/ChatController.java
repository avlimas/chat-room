package chat.room.controller;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chat.room.request.MessagePostRequest;
import chat.room.response.IncomingGetResponse;
import chat.room.response.MessageGetResponse;
import chat.room.response.OutcomingGetResponse;
import chat.room.service.ChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/chat")
@Api(value="Chat", produces = "REST endpoints for chat")
@RequiredArgsConstructor
public class ChatController {

	private final ChatService chatService;
	
	@ApiOperation(value = "Send a message")
	@ApiResponses(value = {
	        @ApiResponse(code = 201, message = "Succesfully send a message"),
	        @ApiResponse(code = 401, message = "You are not authorized to send the message"),
	        @ApiResponse(code = 403, message = "Sending message is forbidden")
	    })
    @PostMapping(value= "/send-message", consumes = "application/json")
    public ResponseEntity<String> sendMessage(
    		@ApiParam(value = "Store JSON to message post request object", required = true) 
    		@Valid @RequestBody MessagePostRequest messagePostRequest)
    {
    	this.chatService.sendMessage(messagePostRequest);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Location", "/api/v1/chat");
        return ResponseEntity.status(HttpStatus.CREATED).headers(responseHeaders).build();
    }
	
	@ApiOperation(value = "Get a list of incoming messages of a user", 
			response = IncomingGetResponse.class,
			responseContainer = "Object")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved list"),
	        @ApiResponse(code = 401, message = "You are not authorized to get the list"),
	        @ApiResponse(code = 403, message = "Accessing the list of messages is forbidden"),
	        @ApiResponse(code = 404, message = "The list you were trying to reach is not found")
	    })
    @GetMapping("/incoming-messages/user/{user}")
    public ResponseEntity<IncomingGetResponse> getIncomingMessages(
			@PathVariable(name = "user") @ApiParam(value = "Receiver name for the messages", required = true) String user)
    {
		IncomingGetResponse incomingGetResponse = this.chatService.getIncomingMessages(user);
        if (incomingGetResponse != null) {
            return ResponseEntity.status(HttpStatus.OK).body(incomingGetResponse);
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
    }
	
	@ApiOperation(value = "Get a list of outcoming messages by a user", 
			response = OutcomingGetResponse.class,
			responseContainer = "Object")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved list"),
	        @ApiResponse(code = 401, message = "You are not authorized to get the list"),
	        @ApiResponse(code = 403, message = "Accessing the list of messages is forbidden"),
	        @ApiResponse(code = 404, message = "The list you were trying to reach is not found")
	    })
    @GetMapping("/outcoming-messages/user/{user}")
    public ResponseEntity<OutcomingGetResponse> getOutcomingMessages(
			@PathVariable(name = "user") @ApiParam(value = "Sender name for the messages", required = true) String user)
    {
		OutcomingGetResponse outcomingGetResponse = this.chatService.getOutcomingMessages(user);
		if (outcomingGetResponse != null) {
	        return ResponseEntity.status(HttpStatus.OK).body(outcomingGetResponse);
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
    }
	
	@ApiOperation(value = "Get message details by subject", 
			response = MessageGetResponse.class,
			responseContainer = "Object")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved message"),
	        @ApiResponse(code = 401, message = "You are not authorized to get the message"),
	        @ApiResponse(code = 403, message = "Accessing the message is forbidden"),
	        @ApiResponse(code = 404, message = "The message you were trying to reach is not found")
	    })
    @GetMapping("/message/subject/{subject}")
    public ResponseEntity<MessageGetResponse> getMessageDetails(
			@PathVariable(name = "subject") @ApiParam(value = "Subject of the message", required = true) String subject)
    {
		MessageGetResponse messageGetResponse = this.chatService.getMessageDetails(subject);
		if (messageGetResponse != null) {
	        return ResponseEntity.status(HttpStatus.OK).body(messageGetResponse);
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
    }
}
