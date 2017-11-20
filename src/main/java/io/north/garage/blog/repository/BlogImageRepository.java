package io.north.garage.blog.repository;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;

import java.io.IOException;
import java.io.InputStream;

public interface BlogImageRepository {

    PutObjectResult putObject(final String bucketName,
                              final String objectKey,
                              final InputStream is) throws IOException;

    S3Object getObject(final String bucketName,
                       final String objectKey);

}
