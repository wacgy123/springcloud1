package cn.tedu.sp03.user.service;

import cn.tedu.sp01.pojo.User;
import cn.tedu.sp01.service.UserService;
import cn.tedu.web.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Value("${sp.user-service.users}")
    private String userJson; //注入yml中配置的用户数据

    @Override
    public User getUser(Integer id) {
        System.out.println(userJson);
        log.info("id="+id+", users="+userJson);
        //json转换成 List<User>
        List<User> users = JsonUtil.from(userJson, new TypeReference<List<User>>() { });

        for (User u: users) {
            if (id.equals(u.getId())) {
                return u;
            }
        }

        return new User(id, "用户名"+id, "密码"+id);
    }

    @Override
    public void addScore(Integer id, Integer score) {
        log.info("增加用户积分， userId="+id+", score="+score);
    }
}
