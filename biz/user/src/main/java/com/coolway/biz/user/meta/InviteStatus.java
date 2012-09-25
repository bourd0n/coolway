package com.coolway.biz.user.meta;

public enum InviteStatus {
	NEW("new"), SENDED("sended"), ACCEPTED("accepted");

	private String status;

	InviteStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

}
