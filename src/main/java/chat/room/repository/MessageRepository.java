package chat.room.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import chat.room.model.MessageEntity;

public interface MessageRepository extends MongoRepository<MessageEntity, String> {

	List<MessageEntity> findAllByReceiver(String receiverName);

	List<MessageEntity> findAllBySender(String senderName);
	
	Optional<MessageEntity> findBySubject(String messageSubject);
	
	@Query("{ 'sentDate' : { $gt: ?0, $lt: ?1 } }")
	List<MessageEntity> findMessagesByDateBetween(LocalDateTime fromDate, LocalDateTime untilDate);
}
