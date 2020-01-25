package chat.room.service;

import chat.room.request.MessagePostRequest;
import chat.room.response.IncomingGetResponse;
import chat.room.response.MessageGetResponse;
import chat.room.response.OutcomingGetResponse;

public interface ChatService {
	
	public void sendMessage(MessagePostRequest messagePostRequest);

	public IncomingGetResponse getIncomingMessages(String receiverName);

	public OutcomingGetResponse getOutcomingMessages(String senderName);

	public MessageGetResponse getMessageDetails(String messageSubject);
}
