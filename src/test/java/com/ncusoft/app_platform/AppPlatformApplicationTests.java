package com.ncusoft.app_platform;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
class AppPlatformApplicationTests {

    @Autowired
    ValueOperations<String ,Object> valueOperations;
//
//    @Test
//    void contextLoads() {
//        valueOperations.set("test", "redis");
//        System.out.println(valueOperations.get("test"));
//    }

}
