package com.ncusoft.app_platform;

import com.ncusoft.app_platform.mapper.AppMapper;
import com.ncusoft.app_platform.model.Vo.HistoryAppVersionVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Arno Clare
 * @date 8:47 2020/7/6
 */
@SpringBootTest
public class AppMapperTest {

    private final AppMapper appMapper;

    @Autowired
    public AppMapperTest(AppMapper appMapper) {
        this.appMapper = appMapper;
    }

    @Test
    public void listHistoryAppVersionVoTest() {
//        List<HistoryAppVersionVo> historyAppVersionVos = appMapper.listHistoryAppVersionVo(16);
//        System.out.println(historyAppVersionVos.get(0));
//        assertNotNull(historyAppVersionVos);
    }
}
