package se.sundsvall.smssender.provider.infobip.domain;

import java.util.List;

public record InfobipSmsRequest(
	List<InfobipMessage> messages) {
}
