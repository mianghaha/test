package mybatis.test;

import java.util.List;

public interface MomentMapper {
	public Moment getMoment(Long momentId);
	public List<Moment> getMomentByRoleId(Long roleId);
}
