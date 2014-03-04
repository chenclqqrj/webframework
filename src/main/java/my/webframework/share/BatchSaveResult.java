package my.webframework.share;

import java.util.List;

public class BatchSaveResult<T> {
	private int recordCount;
	private int sucessCount;
	private List<T> failInfo; // 保存到数据库的时候，失败的集合
	private List<T> validInfo; // 保存前数据校验的错误集合

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public int getSucessCount() {
		return sucessCount;
	}

	public void setSucessCount(int sucessCount) {
		this.sucessCount = sucessCount;
	}

	public List<T> getFailInfo() {
		return failInfo;
	}

	public void setFailInfo(List<T> failInfo) {
		this.failInfo = failInfo;
	}

	public List<T> getValidInfo() {
		return validInfo;
	}

	public void setValidInfo(List<T> validInfo) {
		this.validInfo = validInfo;
	}

}
