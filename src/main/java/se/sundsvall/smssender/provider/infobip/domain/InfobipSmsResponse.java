package se.sundsvall.smssender.provider.infobip.domain;

import java.util.List;

public record InfobipSmsResponse(
	String bulkId,
	List<Message> messages) {

	public record Message(
		String messageId,
		Status status,
		String destination) {
	}

	public record Status(
		int groupId,
		String groupName,
		int id,
		String name,
		String description) {
	}
}
