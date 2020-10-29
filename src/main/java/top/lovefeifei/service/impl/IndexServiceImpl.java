package top.lovefeifei.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.lovefeifei.dao.IndexDao;
import top.lovefeifei.entity.User;
import top.lovefeifei.service.IndexService;

@Service
@SuppressWarnings("all")
public class IndexServiceImpl implements IndexService {
    @Autowired
    private IndexDao indexDao;

    @Override
    public Integer isExist(String username) {
        return indexDao.isExist(username);
    }

    @Override
    public User findUser(String username) {
        return indexDao.findUser(username);
    }

    @Override
    public Integer addUser(User user) {
        return indexDao.addUser(user);
    }
}
