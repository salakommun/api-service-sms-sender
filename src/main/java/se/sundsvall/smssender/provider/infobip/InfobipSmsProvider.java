package se.sundsvall.smssender.provider.infobip;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import se.sundsvall.smssender.api.model.SendSmsRequest;
import se.sundsvall.smssender.provider.SmsProvider;

import static java.util.Optional.ofNullable;

@Service
@EnableConfigurationProperties(InfobipSmsProviderProperties.class)
public class InfobipSmsProvider implements SmsProvider {

	static final String PROVIDER_NAME = "Infobip";

	private final InfobipSmsProviderProperties properties;
	private final InfobipClient client;
	private final InfobipMapper mapper;

	InfobipSmsProvider(
		final InfobipSmsProviderProperties properties,
		final InfobipClient client,
		final InfobipMapper mapper) {

		this.properties = properties;
		this.client = client;
		this.mapper = mapper;
	}

	@Override
	public boolean sendSms(final SendSmsRequest sms, final boolean flash) {
		verifyFlashCapability(flash);

		final var request = mapper.mapFromSendSmsRequest(sms, flash);

		return ofNullable(client.send(request))
			.filter(response -> response.getStatusCode().is2xxSuccessful())
			.map(response -> response.getBody())
			.filter(body -> body.messages() != null && !body.messages().isEmpty())
			.isPresent();
	}

	@Override
	public String getName() {
		return PROVIDER_NAME;
	}

	@Override
	public boolean isEnabled() {
		return properties.isEnabled();
	}

	@Override
	public boolean isFlashSmsCapable() {
		return properties.isFlashCapable();
	}

	@Override
	public int getPriority() {
		return properties.getPriority();
	}
}
