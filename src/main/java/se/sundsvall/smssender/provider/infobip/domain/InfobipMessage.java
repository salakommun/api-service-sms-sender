package se.sundsvall.smssender.provider.infobip.domain;

import java.util.List;

public record InfobipMessage(
	String sender,
	List<InfobipDestination> destinations,
	InfobipContent content,
	InfobipOptions options) {
}
