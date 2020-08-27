package com.ncusoft.app_platform.mapper;

import com.ncusoft.app_platform.model.AppVersion;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Arno Clare
 */
@Repository
@org.apache.ibatis.annotations.Mapper
public interface AppVersionMapper extends Mapper<AppVersion> {
    /**
     * 获取最新的 AppVersion 的 id
     * @param appId app_id
     * @return versionId
     */
    Long getLatestAppVersionId(@Param("appId") long appId);
}
