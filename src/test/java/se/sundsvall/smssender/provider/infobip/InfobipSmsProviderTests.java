package se.sundsvall.smssender.provider.infobip;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import se.sundsvall.smssender.provider.infobip.domain.InfobipSmsRequest;
import se.sundsvall.smssender.provider.infobip.domain.InfobipSmsResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.sundsvall.smssender.TestDataFactory.createValidSendSmsRequest;

@ActiveProfiles("junit")
@ExtendWith(MockitoExtension.class)
class InfobipSmsProviderTests {

	@Mock
	private InfobipClient mockClient;

	@Mock
	private InfobipSmsProviderProperties mockProperties;

	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private InfobipMapper mockMapper;

	@InjectMocks
	private InfobipSmsProvider provider;

	@Test
	void testSendSms_no_flash_OK() {
		final var response = new InfobipSmsResponse(
			"bulk-id",
			List.of(
				new InfobipSmsResponse.Message(
					"message-id",
					new InfobipSmsResponse.Status(
						1,
						"PENDING",
						26,
						"PENDING_ACCEPTED",
						"Message sent to next instance"),
					"+46701234567")));

		when(mockClient.send(any(InfobipSmsRequest.class)))
			.thenReturn(ResponseEntity.ok(response));

		final var isSent = provider.sendSms(createValidSendSmsRequest(), false);

		assertThat(isSent).isTrue();

		verify(mockClient, times(1)).send(any(InfobipSmsRequest.class));
	}

	@Test
	void testSendSms_no_flash_noContent() {
		when(mockClient.send(any(InfobipSmsRequest.class)))
			.thenReturn(ResponseEntity.ok().build());

		final var isSent = provider.sendSms(createValidSendSmsRequest(), false);

		assertThat(isSent).isFalse();

		verify(mockClient, times(1)).send(any(InfobipSmsRequest.class));
	}

	@Test
	void testSendSms_no_flash_emptyMessages() {
		final var response = new InfobipSmsResponse(
			"bulk-id",
			List.of());

		when(mockClient.send(any(InfobipSmsRequest.class)))
			.thenReturn(ResponseEntity.ok(response));

		final var isSent = provider.sendSms(createValidSendSmsRequest(), false);

		assertThat(isSent).isFalse();

		verify(mockClient, times(1)).send(any(InfobipSmsRequest.class));
	}

	@Test
	void testProviderProperties() {
		when(mockProperties.isEnabled()).thenReturn(true);
		when(mockProperties.isFlashCapable()).thenReturn(true);
		when(mockProperties.getPriority()).thenReturn(1);

		assertThat(provider.getName()).isEqualTo("Infobip");
		assertThat(provider.isEnabled()).isTrue();
		assertThat(provider.isFlashSmsCapable()).isTrue();
		assertThat(provider.getPriority()).isEqualTo(1);
	}
}
