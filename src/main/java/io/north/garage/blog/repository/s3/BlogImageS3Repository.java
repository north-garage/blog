package io.north.garage.blog.repository.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import io.north.garage.blog.repository.BlogImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;

@Repository
public class BlogImageS3Repository implements BlogImageRepository {
    @Autowired
    private AmazonS3 s3Client;

    @Override
    public PutObjectResult putObject(final String bucketName,
                                     final String objectKey,
                                     final InputStream is) throws IOException {

        return s3Client.putObject(bucketName, objectKey, is, new ObjectMetadata());
    }

    @Override
    public S3Object getObject(final String bucketName,
                              final String objectKey) {

        return s3Client.getObject(bucketName, objectKey);
    }

}
