package com.a7space.commons.fastdfs;

import java.io.Serializable;

public class FDFSUploadResult implements Serializable {
	private static final long serialVersionUID = -6368131547632720188L;
	private String groupName;
	private String remoteFileName;

	public FDFSUploadResult(String groupName, String remoteFileName) {
		this.groupName = groupName;
		this.remoteFileName = remoteFileName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getRemoteFileName() {
		return remoteFileName;
	}

	public void setRemoteFileName(String remoteFileName) {
		this.remoteFileName = remoteFileName;
	}

}
