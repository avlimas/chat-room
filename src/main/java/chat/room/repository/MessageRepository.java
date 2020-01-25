package chat.room.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import chat.room.model.MessageEntity;

public interface MessageRepository extends MongoRepository<MessageEntity, String> {

	List<MessageEntity> findAllByReceiver(String receiverName);

	List<MessageEntity> findAllBySender(String senderName);
	
	Optional<MessageEntity> findBySubject(String messageSubject);
}
