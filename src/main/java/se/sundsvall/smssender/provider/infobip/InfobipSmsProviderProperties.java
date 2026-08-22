package se.sundsvall.smssender.provider.infobip;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import se.sundsvall.smssender.provider.SmsProviderProperties;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "provider.infobip")
public class InfobipSmsProviderProperties extends SmsProviderProperties {

	@NotBlank
	private String apiKey;
}
