package chat.room.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import chat.room.mapper.MessageMapper;
import chat.room.model.MessageEntity;
import chat.room.repository.MessageRepository;
import chat.room.request.MessagePostRequest;
import chat.room.response.DayEstimationGetResponse;
import chat.room.response.IncomingGetResponse;
import chat.room.response.MessageGetResponse;
import chat.room.response.OutcomingGetResponse;
import chat.room.response.WeekEstimationGetResponse;
import chat.room.response.object.IncomingMessage;
import chat.room.response.object.OutcomingMessage;

@DisplayName("Chat Service Test - Testing Mock data")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ChatServiceImplTest {

	private ChatServiceImpl chatServiceImpl;
	
	@Autowired
	private MessageMapper messageMapper;
	
	@Mock
	private MessageRepository messageRepository;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;
    
    @Captor
    ArgumentCaptor<MessageEntity> messageEntityCaptor;

    @Captor
    ArgumentCaptor<LocalDateTime> localDateTimeCaptor;
    
	@BeforeEach
    void setUpForEach() {
		chatServiceImpl = new ChatServiceImpl(messageRepository, messageMapper);
    }
	
	@DisplayName("Estimate Message Daily")
	@Test
	void testEstimateTotalMessagesDaily() {

        //Given
		String currentDate = LocalDate.now().toString();
		List<MessageEntity> messageEntityList = getAllMessagesByAUser();
		given(this.messageRepository.findMessagesByDateBetween(localDateTimeCaptor.capture(), localDateTimeCaptor.capture()))
		.willReturn(messageEntityList);
		
		//When
		DayEstimationGetResponse actualDayEstimationGetResponse = this.chatServiceImpl.getTotalMessagesToday();

		//Then
		DayEstimationGetResponse expectedDayEstimationGetResponse = DayEstimationGetResponse.builder()
				.currentDate(currentDate).totalMessages(2)
				.build();
		then(this.messageRepository).should(atLeastOnce()).findMessagesByDateBetween(any(), any());
		
		assertEquals(expectedDayEstimationGetResponse, actualDayEstimationGetResponse);
	}
	
	@DisplayName("Null Message Daily")
	@Test
	void testNullMessagesDaily() {

        //Given
		List<MessageEntity> messageEntityList = new ArrayList<>();
		given(this.messageRepository.findMessagesByDateBetween(localDateTimeCaptor.capture(), localDateTimeCaptor.capture()))
		.willReturn(messageEntityList);
		
		//When
		DayEstimationGetResponse actualDayEstimationGetResponse = this.chatServiceImpl.getTotalMessagesToday();

		//Then
		DayEstimationGetResponse expectedDayEstimationGetResponse = null;
		then(this.messageRepository).should(atLeastOnce()).findMessagesByDateBetween(any(), any());
		
		assertEquals(expectedDayEstimationGetResponse, actualDayEstimationGetResponse);
	}
	
	@DisplayName("Estimate Message Weekly")
	@Test
	void testEstimateTotalMessagesWeekly() {

        //Given
		LocalDate currentDate = LocalDate.now();
		List<MessageEntity> messageEntityList = getAllMessagesByAUser();
		given(this.messageRepository.findMessagesByDateBetween(localDateTimeCaptor.capture(), localDateTimeCaptor.capture()))
		.willReturn(messageEntityList);
		
		//When
		WeekEstimationGetResponse actualWeekEstimationGetResponse = this.chatServiceImpl.getTotalMessagesWeekly();

		//Then
		WeekEstimationGetResponse expectedWeekEstimationGetResponse = WeekEstimationGetResponse.builder()
				.fromDate(currentDate.minusDays(7).toString()).untilDate(currentDate.toString()).totalMessages(2)
				.build();
		then(this.messageRepository).should(atLeastOnce()).findMessagesByDateBetween(any(), any());

		assertEquals(expectedWeekEstimationGetResponse, actualWeekEstimationGetResponse);
	}
	
	@DisplayName("Null Message Weekly")
	@Test
	void testNullMessagesWeekly() {

        //Given
		List<MessageEntity> messageEntityList = new ArrayList<>();
		given(this.messageRepository.findMessagesByDateBetween(localDateTimeCaptor.capture(), localDateTimeCaptor.capture()))
		.willReturn(messageEntityList);
		
		//When
		WeekEstimationGetResponse actualWeekEstimationGetResponse = this.chatServiceImpl.getTotalMessagesWeekly();

		//Then
		WeekEstimationGetResponse expectedWeekEstimationGetResponse = null;
		then(this.messageRepository).should(atLeastOnce()).findMessagesByDateBetween(any(), any());

		assertEquals(expectedWeekEstimationGetResponse, actualWeekEstimationGetResponse);
	}
	
	@DisplayName("Send Message")
	@Test
	void testSendMessage() {

        //Given
		LocalDateTime mockedDateTime = LocalDateTime.now();
		given(this.messageRepository.save(messageEntityCaptor.capture()))
		.willReturn(getMessageEntity(mockedDateTime).get());
		
		//When
		this.chatServiceImpl.sendMessage(getMessagePostRequest());

		//Then
		then(this.messageRepository).should(atLeastOnce()).save(any());
	}
	
	@DisplayName("Get Incoming Messages of a User")
    @ParameterizedTest
    @CsvSource({"germany"})
	void testGetIncomingMessage(String receiver) {

        //Given
		List<MessageEntity> messageEntityList = getAllMessagesByAUser();
		given(this.messageRepository.findAllByReceiver(stringArgumentCaptor.capture())).willReturn(messageEntityList);
		
		//When
		IncomingGetResponse actualIncomingGetResponse = this.chatServiceImpl.getIncomingMessages(receiver);

		//Then
		IncomingGetResponse expectedIncomingGetResponse = getExpectedIncomingMessages();
		then(this.messageRepository).should(atLeastOnce()).findAllByReceiver(any());
		
		assertEquals(expectedIncomingGetResponse, actualIncomingGetResponse);
	}
	
	@DisplayName("Testing get incoming messages from unknown user")
    @ParameterizedTest
    @CsvSource({"france"})
	void testNullIncomingMessage(String receiver) {
		
        //Given
		List<MessageEntity> messageEntityList = new ArrayList<>();
		given(this.messageRepository.findAllByReceiver(stringArgumentCaptor.capture())).willReturn(messageEntityList);
		
		//When
		IncomingGetResponse actualIncomingGetResponse = this.chatServiceImpl.getIncomingMessages(receiver);

		//Then
		List<IncomingMessage> incomingMessages = new ArrayList<>();
		IncomingGetResponse expectedIncomingGetResponse = IncomingGetResponse.builder().incomingMessageList(incomingMessages).build();
		then(this.messageRepository).should(atLeastOnce()).findAllByReceiver(any());
		
		assertEquals(expectedIncomingGetResponse, actualIncomingGetResponse);
	}
	
	@DisplayName("Get Outcoming Messages of a User")
    @ParameterizedTest
    @CsvSource({"germany"})
	void testGetOutcomingMessage(String sender) {

        //Given
		List<MessageEntity> messageEntityList = getAllMessagesOfAUser();
		given(this.messageRepository.findAllBySender(stringArgumentCaptor.capture())).willReturn(messageEntityList);
		
		//When
		OutcomingGetResponse actualOutcomingGetResponse = this.chatServiceImpl.getOutcomingMessages(sender);

		//Then
		OutcomingGetResponse expectedOutcomingGetResponse = getExpectedOutcomingMessages();
		then(this.messageRepository).should(atLeastOnce()).findAllBySender(any());
		
		assertEquals(expectedOutcomingGetResponse, actualOutcomingGetResponse);
	}
	
	@DisplayName("Testing get outcoming messages from unknown user")
    @ParameterizedTest
    @CsvSource({"france"})
	void testNullOutcomingMessage(String sender) {

        //Given
		List<MessageEntity> messageEntityList = new ArrayList<>();
		given(this.messageRepository.findAllBySender(stringArgumentCaptor.capture())).willReturn(messageEntityList);
		
		//When
		OutcomingGetResponse actualOutcomingGetResponse = this.chatServiceImpl.getOutcomingMessages(sender);

		//Then
		List<OutcomingMessage> outcomingMessages = new ArrayList<>();
		OutcomingGetResponse expectedOutcomingGetResponse = OutcomingGetResponse.builder().outcomingMessageList(outcomingMessages).build();
		then(this.messageRepository).should(atLeastOnce()).findAllBySender(any());
		
		assertEquals(expectedOutcomingGetResponse, actualOutcomingGetResponse);
	}
	
	@DisplayName("Get Message details")
    @ParameterizedTest
    @CsvSource({"First message"})
	void testGetMessageDetails(String subject) {

        //Given
		LocalDateTime mockedDateTime = LocalDateTime.now();
		Optional<MessageEntity> messageEntity = getMessageEntity(mockedDateTime);
		given(this.messageRepository.findBySubject(stringArgumentCaptor.capture())).willReturn(messageEntity);
		
		//When
		MessageGetResponse actualMessageGetResponse = this.chatServiceImpl.getMessageDetails(subject);

		//Then
		MessageGetResponse expectedMessageGetResponse = getExpectedMessageGetResponse(mockedDateTime);
		then(this.messageRepository).should(atLeastOnce()).findBySubject(any());
		
		assertEquals(expectedMessageGetResponse, actualMessageGetResponse);
	}
	
	@DisplayName("Get null of message details")
    @ParameterizedTest
    @CsvSource({"Second message"})
	void testNullMessageDetails(String subject) {

        //Given
		Optional<MessageEntity> messageEntity = Optional.empty();
		given(this.messageRepository.findBySubject(stringArgumentCaptor.capture())).willReturn(messageEntity);
		
		//When
		MessageGetResponse actualMessageGetResponse = this.chatServiceImpl.getMessageDetails(subject);

		//Then
		MessageGetResponse expectedMessageGetResponse = null;
		then(this.messageRepository).should(atLeastOnce()).findBySubject(any());
		
		assertEquals(expectedMessageGetResponse, actualMessageGetResponse);
	}
	
	private Optional<MessageEntity> getMessageEntity(LocalDateTime mockedDateTime) {
		return Optional.of(MessageEntity.builder()
				.messageId("1")
				.sender("Germany")
				.receiver("Belgium")
				.subject("First message")
				.content("Content of first message")
				.sentDate(mockedDateTime)
				.build());
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
	
	private OutcomingGetResponse getExpectedOutcomingMessages() {
		List<OutcomingMessage> outcomingMessages = new ArrayList<>();
		outcomingMessages.add(OutcomingMessage.builder().receiver("belgium").subject("First message").build());
		outcomingMessages.add(OutcomingMessage.builder().receiver("denmark").subject("Second message").build());
		return OutcomingGetResponse.builder().outcomingMessageList(outcomingMessages).build();
	}
	
	private List<MessageEntity> getAllMessagesOfAUser() {
		List<MessageEntity> messageEntityList = new ArrayList<>();
		messageEntityList.add(MessageEntity.builder()
				.messageId("1").sender("germany").receiver("belgium")
				.subject("First message").content("Content of first message").sentDate(LocalDateTime.now())
				.build());
		messageEntityList.add(MessageEntity.builder()
				.messageId("2").sender("germany").receiver("denmark")
				.subject("Second message").content("Content of second message").sentDate(LocalDateTime.now())
				.build());
		return messageEntityList;
	}
	
	private IncomingGetResponse getExpectedIncomingMessages() {
		List<IncomingMessage> incomingMessages = new ArrayList<>();
		incomingMessages.add(IncomingMessage.builder().sender("belgium").subject("First message").build());
		incomingMessages.add(IncomingMessage.builder().sender("denmark").subject("Second message").build());
		return IncomingGetResponse.builder().incomingMessageList(incomingMessages).build();
	}
	
	private List<MessageEntity> getAllMessagesByAUser() {
		List<MessageEntity> messageEntityList = new ArrayList<>();
		messageEntityList.add(MessageEntity.builder()
				.messageId("1").sender("belgium").receiver("germany")
				.subject("First message").content("Content of first message").sentDate(LocalDateTime.now())
				.build());
		messageEntityList.add(MessageEntity.builder()
				.messageId("2").sender("denmark").receiver("germany")
				.subject("Second message").content("Content of second message").sentDate(LocalDateTime.now())
				.build());
		return messageEntityList;
	}
	
	private MessagePostRequest getMessagePostRequest() {
		return MessagePostRequest.builder().sender("Germany").receiver("Belgium").subject("First message")
				.content("Content of first message").build();
	}
	
}
