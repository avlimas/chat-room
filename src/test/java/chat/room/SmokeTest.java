package chat.room;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import chat.room.controller.ChatController;

@SpringBootTest(classes = ChatApplication.class)
public class SmokeTest {

	@Autowired
	private ChatController chatController;

	@Test
	public void contexLoads() throws Exception {
		assertNotNull(chatController);
	}

	@Test
	public void testRunningApplication() {
		ChatApplication.main(new String[] {});
	}
}
