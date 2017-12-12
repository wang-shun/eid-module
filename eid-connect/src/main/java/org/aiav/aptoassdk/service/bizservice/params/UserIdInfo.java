package org.aiav.aptoassdk.service.bizservice.params;

import org.aiav.aptoassdk.constants.EIdType;

public class UserIdInfo {
	private EIdType idType;
	private String userName;
	private String userId;

	public UserIdInfo(String userName, String userId, EIdType idType) {
		this.setUserName(userName);
		this.setUserId(userId);
		this.setIdType(idType);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public EIdType getIdType() {
		return idType;
	}

	public void setIdType(EIdType idType) {
		this.idType = idType;
	}
}
