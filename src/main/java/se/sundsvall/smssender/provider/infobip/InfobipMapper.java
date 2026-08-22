package se.sundsvall.smssender.provider.infobip;

import java.util.List;
import org.springframework.stereotype.Component;
import se.sundsvall.smssender.api.model.SendSmsRequest;
import se.sundsvall.smssender.provider.infobip.domain.InfobipContent;
import se.sundsvall.smssender.provider.infobip.domain.InfobipDestination;
import se.sundsvall.smssender.provider.infobip.domain.InfobipMessage;
import se.sundsvall.smssender.provider.infobip.domain.InfobipOptions;
import se.sundsvall.smssender.provider.infobip.domain.InfobipSmsRequest;

@Component
public class InfobipMapper {

	public InfobipSmsRequest mapFromSendSmsRequest(final SendSmsRequest sms, final boolean flash) {

		final var destination = new InfobipDestination(sms.getMobileNumber());
		final var content = new InfobipContent(sms.getMessage());

		final var options = new InfobipOptions(flash);

		final var message = new InfobipMessage(
			sms.getSender().getName(),
			List.of(destination),
			content,
			options);

		return new InfobipSmsRequest(List.of(message));
	}
}
