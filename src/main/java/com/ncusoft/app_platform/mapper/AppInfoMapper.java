package com.ncusoft.app_platform.mapper;

import com.ncusoft.app_platform.model.AppInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 17:12 2020/7/4
 */
@Repository // add by Arno Clare
@Mapper
public interface AppInfoMapper extends tk.mybatis.mapper.common.Mapper<AppInfo>{
}
