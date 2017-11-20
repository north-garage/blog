package io.north.garage.blog.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "clouds.aws.s3")
public class AwsS3Properties {

    private String endpointUrl;

    private String region;

    private String accessKey;

    private String secretKey;

    private String bucketName;
}
