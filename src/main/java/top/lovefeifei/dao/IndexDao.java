package top.lovefeifei.dao;

import org.springframework.stereotype.Repository;
import top.lovefeifei.entity.User;

@Repository
@SuppressWarnings("all")
public interface IndexDao {
    //查询用户是否存在
    Integer isExist(String username);
    //查询用户密码
    User findUser(String username);
    //添加用户
    Integer addUser(User user);
}
