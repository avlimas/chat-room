package chat.room.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import chat.room.mapper.MessageMapper;
import chat.room.model.MessageEntity;
import chat.room.repository.MessageRepository;
import chat.room.request.MessagePostRequest;
import chat.room.response.IncomingGetResponse;
import chat.room.response.MessageGetResponse;
import chat.room.response.OutcomingGetResponse;
import chat.room.response.object.IncomingMessage;
import chat.room.response.object.OutcomingMessage;
import chat.room.service.ChatService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

	private final MessageRepository messageRepository;
	
	private final MessageMapper messageMapper;
	
	public void sendMessage(MessagePostRequest messagePostRequest) 
	{
		MessageEntity messageEntity = messageMapper.messagePostRequestToMessageEntity(messagePostRequest);
		if (messageEntity != null) {
			LocalDateTime currentTime = LocalDateTime.now();
			messageEntity.setSentDate(currentTime);
			this.messageRepository.save(messageEntity);
		}
	}
	
	public IncomingGetResponse getIncomingMessages(String receiverName)
	{
		List<MessageEntity> messageEntityList = this.messageRepository.findAllByReceiver(receiverName);
		List<IncomingMessage> incomingMessageList = new ArrayList<>();
		if (!messageEntityList.isEmpty()) {
			for(MessageEntity messageEntity : messageEntityList) {
				incomingMessageList.add(messageMapper.messageEntityToIncomingMessage(messageEntity));
			}
		}
		return IncomingGetResponse.builder().incomingMessageList(incomingMessageList).build();
	}
	
	public OutcomingGetResponse getOutcomingMessages(String senderName)
	{
		List<MessageEntity> messageEntityList = this.messageRepository.findAllBySender(senderName);
		List<OutcomingMessage> outcomingMessageList = new ArrayList<>();
		if (!messageEntityList.isEmpty()) {
			for(MessageEntity messageEntity : messageEntityList) {
				outcomingMessageList.add(messageMapper.messageEntityToOutcomingMessage(messageEntity));
			}
		}
		return OutcomingGetResponse.builder().outcomingMessageList(outcomingMessageList).build();
	}
	
	public MessageGetResponse getMessageDetails(String messageSubject)
	{
		Optional<MessageEntity> messageEntity = this.messageRepository.findBySubject(messageSubject);
		MessageGetResponse messageGetResponse = null;
		if (messageEntity.isPresent()) {
			messageGetResponse = messageMapper.messageEntityToMessageGetResponse(messageEntity.get());
		}
		return messageGetResponse;
	}
	
	/*
	 * TODO
	 * - an endpoint to estimate the number of total messages that will be sent probably for the rest of the day 
	 * - an endpoint to estimate the number of total messages that will be sent probably for the rest of the week
	 */
}