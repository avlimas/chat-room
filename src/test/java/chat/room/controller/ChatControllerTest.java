package chat.room.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import chat.room.request.MessagePostRequest;
import chat.room.response.IncomingGetResponse;
import chat.room.response.MessageGetResponse;
import chat.room.response.OutcomingGetResponse;
import chat.room.response.object.IncomingMessage;
import chat.room.response.object.OutcomingMessage;
import chat.room.service.ChatService;

@WebMvcTest(ChatController.class)
class ChatControllerTest {

    @MockBean
    private ChatService chatService;
    
    @Autowired
    private MockMvc mockMvc;

    @Captor
    private ArgumentCaptor<String> stringCaptor;
    
    @Autowired
    @Qualifier("jacksonObjectMapper")
    private ObjectMapper objectMapper;

    @DisplayName("Test GET incoming messages of a user")
    @Test
    void getIncomingMessage() throws Exception {
    	// Given
    	String receiver = "germany";
    	given(this.chatService.getIncomingMessages(stringCaptor.capture()))
    	.willReturn(getExpectedIncomingGetResponse());
    	
    	//When
    	mockMvc.perform(get("/api/v1/chat/incoming-messages?receiver={receiver}", receiver)
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk());
    	
    	//Then
    	then(this.chatService).should().getIncomingMessages(stringCaptor.capture());
    }
    
    @DisplayName("Test GET null incoming messages of a user")
    @Test
    void getNullIncomingMessage() throws Exception {
    	// Given
    	String receiver = "germany";
    	given(this.chatService.getIncomingMessages(stringCaptor.capture()))
    	.willReturn(null);
    	
    	//When
    	mockMvc.perform(get("/api/v1/chat/incoming-messages?receiver={receiver}", receiver)
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isNotFound());
    	
    	//Then
    	then(this.chatService).should().getIncomingMessages(stringCaptor.capture());
    }
    
    @DisplayName("Test GET outcoming messages of a user")
    @Test
    void getOutcomingMessage() throws Exception {
    	// Given
    	String sender = "germany";
    	given(this.chatService.getOutcomingMessages(stringCaptor.capture()))
    	.willReturn(getExpectedOutcomingMessages());
    	
    	//When
    	mockMvc.perform(get("/api/v1/chat/outcoming-messages?sender={sender}", sender)
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk());
    	
    	//Then
    	then(this.chatService).should().getOutcomingMessages(stringCaptor.capture());
    }
    
    @DisplayName("Test GET null outcoming messages of a user")
    @Test
    void getNullOutcomingMessage() throws Exception {
    	// Given
    	String sender = "germany";
    	given(this.chatService.getOutcomingMessages(stringCaptor.capture()))
    	.willReturn(null);
    	
    	//When
    	mockMvc.perform(get("/api/v1/chat/outcoming-messages?sender={sender}", sender)
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isNotFound());
    	
    	//Then
    	then(this.chatService).should().getOutcomingMessages(stringCaptor.capture());
    }
    
    @DisplayName("Test GET message details")
    @Test
    void getMessageDetails() throws Exception {
    	// Given
    	String subject = "First message";
		LocalDateTime mockedDateTime = LocalDateTime.now();
    	given(this.chatService.getMessageDetails(stringCaptor.capture()))
    	.willReturn(getExpectedMessageGetResponse(mockedDateTime));
    	
    	//When
    	mockMvc.perform(get("/api/v1/chat/message-details?subject={subject}", subject)
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk());
    	
    	//Then
    	then(this.chatService).should().getMessageDetails(stringCaptor.capture());
    }
    
    @DisplayName("Test GET null message details")
    @Test
    void getNullMessageDetails() throws Exception {
    	// Given
    	String subject = "First message";
    	given(this.chatService.getMessageDetails(stringCaptor.capture()))
    	.willReturn(null);
    	
    	//When
    	mockMvc.perform(get("/api/v1/chat/message-details?subject={subject}", subject)
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isNotFound());
    	
    	//Then
    	then(this.chatService).should().getMessageDetails(stringCaptor.capture());
    }
    
    @DisplayName("Test POST send message ")
    @Test
    void sendMessage() throws Exception {
    	// Given
    	String messagePostRequestJson = objectMapper.writeValueAsString(
    			getMessagePostRequest());
    	
    	//When
    	mockMvc.perform(post("/api/v1/chat/send-message")
                .contentType(MediaType.APPLICATION_JSON)
                .content(messagePostRequestJson))
    			.andExpect(status().isCreated());
    	
    	//Then
    	then(this.chatService).should().sendMessage(any());
    }
    
    private MessagePostRequest getMessagePostRequest() {
		return MessagePostRequest.builder().sender("Germany")
				.receiver("Belgium").subject("First message")
				.content("Content of first message").build();
	}
    
    private MessageGetResponse getExpectedMessageGetResponse(LocalDateTime mockedDateTime) {
		return MessageGetResponse.builder()
				.messageId("1")
				.sender("Germany")
				.receiver("Belgium")
				.content("Content of first message")
				.sentDate(mockedDateTime)
				.build();
	}
    
    private IncomingGetResponse getExpectedIncomingGetResponse() {
		List<IncomingMessage> incomingMessages = new ArrayList<>();
		incomingMessages.add(IncomingMessage.builder().sender("belgium").subject("First message").build());
		incomingMessages.add(IncomingMessage.builder().sender("denmark").subject("Second message").build());
		return IncomingGetResponse.builder().incomingMessageList(incomingMessages).build();
	}
    
    private OutcomingGetResponse getExpectedOutcomingMessages() {
		List<OutcomingMessage> outcomingMessages = new ArrayList<>();
		outcomingMessages.add(OutcomingMessage.builder().receiver("belgium").subject("First message").build());
		outcomingMessages.add(OutcomingMessage.builder().receiver("denmark").subject("Second message").build());
		return OutcomingGetResponse.builder().outcomingMessageList(outcomingMessages).build();
	}
}
