package se.sundsvall.smssender.provider.infobip;

import feign.Request;
import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.FeignBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import se.sundsvall.dept44.configuration.feign.FeignConfiguration;
import se.sundsvall.dept44.configuration.feign.FeignMultiCustomizer;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Import(FeignConfiguration.class)
class InfobipConfiguration {

	private final InfobipSmsProviderProperties properties;

	InfobipConfiguration(final InfobipSmsProviderProperties properties) {
		this.properties = properties;
	}

	@Bean
	FeignBuilderCustomizer customizer() {
		return FeignMultiCustomizer.create()
			.withRequestInterceptor(apiKeyInterceptor())
			.withRequestOptions(new Request.Options(
				properties.getConnectTimeout().toMillis(), MILLISECONDS,
				properties.getReadTimeout().toMillis(), MILLISECONDS,
				true))
			.composeCustomizersToOne();
	}

	private RequestInterceptor apiKeyInterceptor() {
		return template -> template.header(
			"Authorization",
			"App " + properties.getApiKey());
	}
}
