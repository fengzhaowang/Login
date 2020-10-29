package top.lovefeifei.service;

import org.springframework.stereotype.Service;
import top.lovefeifei.entity.User;

import java.util.List;

@Service
@SuppressWarnings("all")
public interface IndexService {
    //查询用户是否存在
    Integer isExist(String username);
    //查询用户密码
    User findUser(String username);
    //添加用户
    Integer addUser(User user);
}
