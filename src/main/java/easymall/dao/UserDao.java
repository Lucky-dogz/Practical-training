package easymall.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import easymall.po.User;

@Repository
@Mapper
public interface UserDao {
	public User login(String username);
	public int regist(User user);
}
