package mysql.bean;

import java.util.Date;

public class Moment {

	public Moment() {
		
	}

	public Moment(long momentId, int gameId, long roleId, int serverId, String roleName, String userId, String photoId,
			String content, String supplement, String pic1, String pic2, String pic3, String pic4, String remindList,
			int status, Date createTime, Date lastQueryTime, int subjectId, Date hotTime, int hotStatus, int voteSize,
			int replySize, boolean hasVoted, int giftCount, int popularity, long npcId, int mediaType, String ext1,
			String ext2, String ext3, String permissions, boolean secret) {
		super();
		this.momentId = momentId;
		this.gameId = gameId;
		this.roleId = roleId;
		this.serverId = serverId;
		this.roleName = roleName;
		this.userId = userId;
		this.photoId = photoId;
		this.content = content;
		this.supplement = supplement;
		this.pic1 = pic1;
		this.pic2 = pic2;
		this.pic3 = pic3;
		this.pic4 = pic4;
		this.remindList = remindList;
		this.status = status;
		this.createTime = createTime;
		this.lastQueryTime = lastQueryTime;
		this.subjectId = subjectId;
		this.hotTime = hotTime;
		this.hotStatus = hotStatus;
		this.voteSize = voteSize;
		this.replySize = replySize;
		this.hasVoted = hasVoted;
		this.giftCount = giftCount;
		this.popularity = popularity;
		this.npcId = npcId;
		this.mediaType = mediaType;
		this.ext1 = ext1;
		this.ext2 = ext2;
		this.ext3 = ext3;
		this.permissions = permissions;
		this.secret = secret;
	}
	
	private long momentId;
	private int gameId;
	private long roleId;
	private int serverId;
	private String roleName;
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
	private Date lastQueryTime;
	private int subjectId;
	private Date hotTime;
	private int hotStatus;
	private int voteSize;
	private int replySize;
	private boolean hasVoted;
	private int giftCount;
	private int popularity;
	private long npcId;
	private int mediaType;
	private String ext1;
	private String ext2;
	private String ext3;
	private String permissions;
	private boolean secret;
	public long getMomentId() {
		return momentId;
	}
	public int getGameId() {
		return gameId;
	}
	public long getRoleId() {
		return roleId;
	}
	public int getServerId() {
		return serverId;
	}
	public String getRoleName() {
		return roleName;
	}
	public String getUserId() {
		return userId;
	}
	public String getPhotoId() {
		return photoId;
	}
	public String getContent() {
		return content;
	}
	public String getSupplement() {
		return supplement;
	}
	public String getPic1() {
		return pic1;
	}
	public String getPic2() {
		return pic2;
	}
	public String getPic3() {
		return pic3;
	}
	public String getPic4() {
		return pic4;
	}
	public String getRemindList() {
		return remindList;
	}
	public int getStatus() {
		return status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public Date getLastQueryTime() {
		return lastQueryTime;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public Date getHotTime() {
		return hotTime;
	}
	public int getHotStatus() {
		return hotStatus;
	}
	public int getVoteSize() {
		return voteSize;
	}
	public int getReplySize() {
		return replySize;
	}
	public boolean isHasVoted() {
		return hasVoted;
	}
	public int getGiftCount() {
		return giftCount;
	}
	public int getPopularity() {
		return popularity;
	}
	public long getNpcId() {
		return npcId;
	}
	public int getMediaType() {
		return mediaType;
	}
	public String getExt1() {
		return ext1;
	}
	public String getExt2() {
		return ext2;
	}
	public String getExt3() {
		return ext3;
	}
	public String getPermissions() {
		return permissions;
	}
	public boolean isSecret() {
		return secret;
	}
	public void setMomentId(long momentId) {
		this.momentId = momentId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setSupplement(String supplement) {
		this.supplement = supplement;
	}
	public void setPic1(String pic1) {
		this.pic1 = pic1;
	}
	public void setPic2(String pic2) {
		this.pic2 = pic2;
	}
	public void setPic3(String pic3) {
		this.pic3 = pic3;
	}
	public void setPic4(String pic4) {
		this.pic4 = pic4;
	}
	public void setRemindList(String remindList) {
		this.remindList = remindList;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setLastQueryTime(Date lastQueryTime) {
		this.lastQueryTime = lastQueryTime;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public void setHotTime(Date hotTime) {
		this.hotTime = hotTime;
	}
	public void setHotStatus(int hotStatus) {
		this.hotStatus = hotStatus;
	}
	public void setVoteSize(int voteSize) {
		this.voteSize = voteSize;
	}
	public void setReplySize(int replySize) {
		this.replySize = replySize;
	}
	public void setHasVoted(boolean hasVoted) {
		this.hasVoted = hasVoted;
	}
	public void setGiftCount(int giftCount) {
		this.giftCount = giftCount;
	}
	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}
	public void setNpcId(long npcId) {
		this.npcId = npcId;
	}
	public void setMediaType(int mediaType) {
		this.mediaType = mediaType;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}
	public void setSecret(boolean secret) {
		this.secret = secret;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Moment [momentId=");
		builder.append(momentId);
		builder.append(", gameId=");
		builder.append(gameId);
		builder.append(", roleId=");
		builder.append(roleId);
		builder.append(", serverId=");
		builder.append(serverId);
		builder.append(", roleName=");
		builder.append(roleName);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", photoId=");
		builder.append(photoId);
		builder.append(", content=");
		builder.append(content);
		builder.append(", supplement=");
		builder.append(supplement);
		builder.append(", pic1=");
		builder.append(pic1);
		builder.append(", pic2=");
		builder.append(pic2);
		builder.append(", pic3=");
		builder.append(pic3);
		builder.append(", pic4=");
		builder.append(pic4);
		builder.append(", remindList=");
		builder.append(remindList);
		builder.append(", status=");
		builder.append(status);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", lastQueryTime=");
		builder.append(lastQueryTime);
		builder.append(", subjectId=");
		builder.append(subjectId);
		builder.append(", hotTime=");
		builder.append(hotTime);
		builder.append(", hotStatus=");
		builder.append(hotStatus);
		builder.append(", voteSize=");
		builder.append(voteSize);
		builder.append(", replySize=");
		builder.append(replySize);
		builder.append(", hasVoted=");
		builder.append(hasVoted);
		builder.append(", giftCount=");
		builder.append(giftCount);
		builder.append(", popularity=");
		builder.append(popularity);
		builder.append(", npcId=");
		builder.append(npcId);
		builder.append(", mediaType=");
		builder.append(mediaType);
		builder.append(", ext1=");
		builder.append(ext1);
		builder.append(", ext2=");
		builder.append(ext2);
		builder.append(", ext3=");
		builder.append(ext3);
		builder.append(", permissions=");
		builder.append(permissions);
		builder.append(", secret=");
		builder.append(secret);
		builder.append("]");
		return builder.toString();
	}
	
}
