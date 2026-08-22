package se.sundsvall.smssender.provider.infobip;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import se.sundsvall.smssender.provider.infobip.domain.InfobipSmsRequest;
import se.sundsvall.smssender.provider.infobip.domain.InfobipSmsResponse;

@FeignClient(
	name = InfobipSmsProvider.PROVIDER_NAME,
	url = "${provider.infobip.base-url}",
	configuration = InfobipConfiguration.class)
interface InfobipClient {

	@PostMapping("/sms/3/messages")
	ResponseEntity<InfobipSmsResponse> send(InfobipSmsRequest request);
}
