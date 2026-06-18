package videominer;

import videominer.src.main.java.aiss.videominer.controller.ChannelController;
import videominer.src.main.java.aiss.videominer.exceptions.ChannelNotFoundException;
import videominer.src.main.java.aiss.videominer.model.Channel;
import videominer.src.main.java.aiss.videominer.repository.ChannelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VideominerApplicationTests {

	@Autowired
	private ChannelController controller;

	@Autowired
	private ChannelRepository dbRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void verifyFetchAllChannels() {
		Channel mockChannel = new Channel();
		mockChannel.setId("mock_id_99");
		mockChannel.setName("Canal de Pruebas Unitarias");
		mockChannel.setDescription("Testing environment");
		mockChannel.setCreatedTime("2026-06-13");

		dbRepository.save(mockChannel);

		List<Channel> channelList = controller.fetchAllChannels();

		assertNotNull(channelList);
		assertFalse(channelList.isEmpty());

		dbRepository.deleteById("mock_id_99");
	}

	@Test
	void verifyInsertNewChannel() {
		Channel newMockChannel = new Channel();
		newMockChannel.setId("mock_id_100");
		newMockChannel.setName("Canal de Desarrollo");
		newMockChannel.setDescription("SpringBoot Tests");
		newMockChannel.setCreatedTime("2026-06-13");

		Channel insertedChannel = controller.insertNewChannel(newMockChannel);

		assertNotNull(insertedChannel);
		assertEquals("mock_id_100", insertedChannel.getId());
		assertEquals("Canal de Desarrollo", insertedChannel.getName());

		dbRepository.deleteById("mock_id_100");
	}

	@Test
	void verifyFetchChannelByIdThrowsException() {
		assertThrows(ChannelNotFoundException.class, () -> {
			controller.fetchChannelById("id_falso_12345");
		});
	}
}