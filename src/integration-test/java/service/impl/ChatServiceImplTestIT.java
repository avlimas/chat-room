package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
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
    @DisplayName("Testing - Get incoming messages")
    @Order(1)
    void getIncomingMessages() {
		String currentDate = LocalDate.now().toString();
		String currentTime = LocalDateTime.now().toString();
		String sender = "sender_1_" + currentDate;
		String receiver = "receiver_1_" + currentDate;
		String subject = "Testing incoming message at " + currentDate;
		IncomingGetResponse actualIncomingGetResponse = this.chatService.getIncomingMessages(receiver);
		if (actualIncomingGetResponse.getIncomingMessageList().isEmpty()) {
			this.chatService.sendMessage(getMessagePostRequest(sender, receiver, subject, currentTime));
			actualIncomingGetResponse = this.chatService.getIncomingMessages(receiver);
		}
		
		assertEquals(getExpectedIncomingGetResponse(sender, subject), actualIncomingGetResponse);
	}
	
	@Test
    @DisplayName("Testing - Get outcoming messages")
    @Order(2)
    void getOutcomingMessages() {
		String currentDate = LocalDate.now().toString();
		String currentTime = LocalDateTime.now().toString();
		String sender = "sender_2_" + currentDate;
		String receiver = "receiver_2_" + currentDate;
		String subject = "Testing outcoming message at " + currentDate;
		OutcomingGetResponse actualOutcomingGetResponse = this.chatService.getOutcomingMessages(sender);
		if (actualOutcomingGetResponse.getOutcomingMessageList().isEmpty()) {
			this.chatService.sendMessage(getMessagePostRequest(sender, receiver, subject, currentTime));
			actualOutcomingGetResponse = this.chatService.getOutcomingMessages(sender);
		}
		
		assertEquals(getExpectedOutcomingMessages(receiver, subject), actualOutcomingGetResponse);
	}
	
	@Test
    @DisplayName("Testing - Get message detail")
    @Order(3)
    void getMessageDetails() {
		String currentDate = LocalDate.now().toString();
		LocalDateTime currentTime = LocalDateTime.now();
		String sender = "sender_3_" + currentDate;
		String receiver = "receiver_3_" + currentDate;
		String subject = "Testing message details at " + currentDate;
		
		MessageGetResponse actualMessageGetResponse = this.chatService.getMessageDetails(subject);
		if (actualMessageGetResponse == null) {
			this.chatService.sendMessage(getMessagePostRequest(sender, receiver, subject, currentTime.toString()));
			actualMessageGetResponse = this.chatService.getMessageDetails(subject);
		}
		
		MessageGetResponse expectedMessageGetResponse = MessageGetResponse.builder()
				.messageId(actualMessageGetResponse.getMessageId())
				.sender(sender)
				.receiver(receiver)
				.content(subject)
				.sentDate(currentTime)
				.build();
		
		assertEquals(expectedMessageGetResponse, actualMessageGetResponse);
	}
	
	@Test
    @DisplayName("Testing - Send a message")
    @Order(4)
    void sendMessage() {
		String currentTime = LocalDateTime.now().toString();
		String sender = "sender_4_" + currentTime;
		String receiver = "receiver_4_" + currentTime;
		String subject = "Integration Subject - " + currentTime; 
		MessagePostRequest messagePostRequest = getMessagePostRequest(sender, receiver, subject, currentTime);
		String expectedSender = messagePostRequest.getSender();
		String expectedReceiver = messagePostRequest.getReceiver();
		String expectedContent = messagePostRequest.getContent();
		
		this.chatService.sendMessage(messagePostRequest);
		MessageGetResponse actualMessage = this.chatService.getMessageDetails(subject);
		
		assertEquals(expectedSender, actualMessage.getSender());
		assertEquals(expectedReceiver, actualMessage.getReceiver());
		assertEquals(expectedContent, actualMessage.getContent());
	}
	
	private MessagePostRequest getMessagePostRequest(String sender, String receiver, 
			String subject, String currentTime) {
		return MessagePostRequest.builder().sender(sender)
				.receiver(receiver).subject(subject)
				.content("Content of integration test, Date - " + currentTime).build();
	}
	
	private IncomingGetResponse getExpectedIncomingGetResponse(String sender, String subject) {
		List<IncomingMessage> incomingMessages = new ArrayList<>();
		incomingMessages.add(IncomingMessage.builder().sender(sender).subject(subject).build());
		return IncomingGetResponse.builder().incomingMessageList(incomingMessages).build();
	}
	
	private OutcomingGetResponse getExpectedOutcomingMessages(String receiver, String subject) {
		List<OutcomingMessage> outcomingMessages = new ArrayList<>();
		outcomingMessages.add(OutcomingMessage.builder().receiver(receiver).subject(subject).build());
		return OutcomingGetResponse.builder().outcomingMessageList(outcomingMessages).build();
	}
}
