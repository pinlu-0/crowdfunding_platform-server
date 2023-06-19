package com.atcpl.crowd.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author cpl
 * @date 2023/1/6
 * @apiNote
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OssProperties {
    private String endPoint;
    private String bucketName;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketDoMain;
}
