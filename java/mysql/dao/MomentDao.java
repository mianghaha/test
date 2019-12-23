package mysql.dao;

import org.apache.ibatis.annotations.Param;

import mysql.bean.Moment;

public interface MomentDao {

	public Moment queryByMomentId(long momentId);
	
	public int insert(Moment moment);
	
	public int updateStatus(@Param("momentId")long momentId, @Param("status")int status);
}
