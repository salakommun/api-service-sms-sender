package se.sundsvall.smssender.provider.infobip;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static se.sundsvall.smssender.TestDataFactory.createValidSendSmsRequest;

class InfobipMapperTests {

	private final InfobipMapper mapper = new InfobipMapper();

	@Test
	void testMapFromSendSmsRequest() {
		final var request = createValidSendSmsRequest();

		final var mappedRequest = mapper.mapFromSendSmsRequest(request, false);

		assertThat(mappedRequest.messages()).hasSize(1);

		final var message = mappedRequest.messages().getFirst();

		assertThat(message.sender()).isEqualTo(request.getSender().getName());
		assertThat(message.destinations()).hasSize(1);
		assertThat(message.destinations().getFirst().to()).isEqualTo(request.getMobileNumber());
		assertThat(message.content().text()).isEqualTo(request.getMessage());
		assertThat(message.options().flash()).isFalse();
	}

	@Test
	void testMapFromSendSmsRequestAsFlashSms() {
		final var request = createValidSendSmsRequest();

		final var mappedRequest = mapper.mapFromSendSmsRequest(request, true);

		assertThat(mappedRequest.messages()).hasSize(1);

		final var message = mappedRequest.messages().getFirst();

		assertThat(message.sender()).isEqualTo(request.getSender().getName());
		assertThat(message.destinations()).hasSize(1);
		assertThat(message.destinations().getFirst().to()).isEqualTo(request.getMobileNumber());
		assertThat(message.content().text()).isEqualTo(request.getMessage());
		assertThat(message.options().flash()).isTrue();
	}
}
