package com.ncusoft.app_platform.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author monkJay
 */

@Data
@Component
@ConfigurationProperties(prefix = "qiniu")
public class QiniuUtil {

    private String accessKey;

    private String secretKey;

    private String bucket;

    private String path;
}