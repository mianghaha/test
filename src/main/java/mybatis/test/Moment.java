package mybatis.test;

import java.util.Date;
import java.util.List;

public class Moment {
	
	public Moment(){
		
	}
	
	private String momentId;
	private int gameId;
	private long roleId;
	private String roleName;
	private int serverId;
	private String userId;
	private String photoId;
	private String content;
	private String supplement;
	private String pic1;
	private String pic2;
	private String pic3;
	private String pic4;
	private String remindList;
	private int status;
	private Date createTime;
	private int subjectId;
	private Date hotTime;
	private int hotStatus;
	private List<Object> voteRecordList;
	private List<Object> replyList;
	private int weight;
	
	public String getMomentId() {
		return momentId;
	}

	public void setMomentId(String momentId) {
		this.momentId = momentId;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSupplement() {
		return supplement;
	}

	public void setSupplement(String supplement) {
		this.supplement = supplement;
	}

	public String getPic1() {
		return pic1;
	}

	public void setPic1(String pic1) {
		this.pic1 = pic1;
	}

	public String getPic2() {
		return pic2;
	}

	public void setPic2(String pic2) {
		this.pic2 = pic2;
	}

	public String getPic3() {
		return pic3;
	}

	public void setPic3(String pic3) {
		this.pic3 = pic3;
	}

	public String getPic4() {
		return pic4;
	}

	public void setPic4(String pic4) {
		this.pic4 = pic4;
	}

	public String getRemindList() {
		return remindList;
	}

	public void setRemindList(String remindList) {
		this.remindList = remindList;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public Date getHotTime() {
		return hotTime;
	}

	public void setHotTime(Date hotTime) {
		this.hotTime = hotTime;
	}

	public int getHotStatus() {
		return hotStatus;
	}

	public void setHotStatus(int hotStatus) {
		this.hotStatus = hotStatus;
	}

	public List<Object> getVoteRecordList() {
		return voteRecordList;
	}

	public void setVoteRecordList(List<Object> voteRecordList) {
		this.voteRecordList = voteRecordList;
	}

	public List<Object> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<Object> replyList) {
		this.replyList = replyList;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
}
