package cn.edu.hfut.backend;

import cn.edu.hfut.backend.dao.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MybatisCacheTests {

    @Autowired
    UserMapper userMapper;

    @Test
    void cacheTest() {
        userMapper.getUserByAccount("123");
        userMapper.getUserByAccount("123");
    }
}
