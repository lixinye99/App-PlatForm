package com.ncusoft.app_platform;

import com.ncusoft.app_platform.mapper.AppVersionMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.*;

/**
 * @author Arno Clare
 * @date 15:29 2020/7/6
 */
@SpringBootTest
public class AppVersionMapperTest {
    private final AppVersionMapper appVersionMapper;

    @Autowired
    public AppVersionMapperTest(AppVersionMapper appVersionMapper) {
        this.appVersionMapper = appVersionMapper;
    }

    @Test
    public void getLatestAppVersionIdTest() {
//        Long versionId = appVersionMapper.getLatestAppVersionId(16);
//        System.out.println(versionId);
    }
}
