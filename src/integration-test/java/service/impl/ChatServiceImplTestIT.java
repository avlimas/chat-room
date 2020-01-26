package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;

import chat.room.ChatApplication;
import chat.room.request.MessagePostRequest;
import chat.room.response.IncomingGetResponse;
import chat.room.response.MessageGetResponse;
import chat.room.response.OutcomingGetResponse;
import chat.room.response.object.IncomingMessage;
import chat.room.response.object.OutcomingMessage;
import chat.room.service.ChatService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ChatApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ChatServiceImplTestIT {

	@Autowired
	ChatService chatService;
	
	@Test
    @DisplayName("Testing - Get incoming messages with user 'germany' ")
    @Order(1)
    void getIncomingMessages() {
		String receiver = "germany";
		
		IncomingGetResponse actualIncomingGetResponse = this.chatService.getIncomingMessages(receiver);
		
		assertEquals(getExpectedIncomingGetResponse(), actualIncomingGetResponse);
	}
	
	@Test
    @DisplayName("Testing - Get outcoming messages with user 'germany' ")
    @Order(2)
    void getOutcomingMessages() {
		String sender = "germany";
		
		OutcomingGetResponse actualOutcomingGetResponse = this.chatService.getOutcomingMessages(sender);
		
		assertEquals(getExpectedOutcomingMessages(), actualOutcomingGetResponse);
	}
	
	@Test
    @DisplayName("Testing - Get message detail with subject 'First message' ")
    @Order(3)
    void getMessageDetails() {
		String subject = "First message";
		
		MessageGetResponse actualMessageGetResponse = this.chatService.getMessageDetails(subject);
		
		assertEquals(getExpectedMessageGetResponse(actualMessageGetResponse.getMessageId()), actualMessageGetResponse);
	}
	
	@Test
    @DisplayName("Testing - Send a message")
    @Order(4)
    void sendMessage() {
		String currentTime = LocalDateTime.now().toString();
		String subject = "Integration Subject - " + currentTime; 
		MessagePostRequest messagePostRequest = getMessagePostRequest(subject, currentTime);
		String expectedSender = messagePostRequest.getSender();
		String expectedReceiver = messagePostRequest.getReceiver();
		String expectedContent = messagePostRequest.getContent();
		
		this.chatService.sendMessage(messagePostRequest);
		MessageGetResponse actualMessage = this.chatService.getMessageDetails(subject);
		
		assertEquals(expectedSender, actualMessage.getSender());
		assertEquals(expectedReceiver, actualMessage.getReceiver());
		assertEquals(expectedContent, actualMessage.getContent());
	}
	
	private MessagePostRequest getMessagePostRequest(String subject, String currentTime) {
		return MessagePostRequest.builder().sender("Test_Instance")
				.receiver("Integration").subject(subject)
				.content("Content of integration subject, Date - " + currentTime).build();
	}
	
	private MessageGetResponse getExpectedMessageGetResponse(String messageId) {
		return MessageGetResponse.builder()
				.messageId(messageId)
				.sender("belgium")
				.receiver("germany")
				.content("Content of first message")
				.sentDate(LocalDateTime.of(2020, 01, 25, 13, 32, 51, 931000000))
				.build();
	}
	
	private IncomingGetResponse getExpectedIncomingGetResponse() {
		List<IncomingMessage> incomingMessages = new ArrayList<>();
		incomingMessages.add(IncomingMessage.builder().sender("belgium").subject("First message").build());
		return IncomingGetResponse.builder().incomingMessageList(incomingMessages).build();
	}
	
	private OutcomingGetResponse getExpectedOutcomingMessages() {
		List<OutcomingMessage> outcomingMessages = new ArrayList<>();
		outcomingMessages.add(OutcomingMessage.builder().receiver("belgium").subject("Second message").build());
		return OutcomingGetResponse.builder().outcomingMessageList(outcomingMessages).build();
	}
}
