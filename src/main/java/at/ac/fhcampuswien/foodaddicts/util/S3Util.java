package at.ac.fhcampuswien.foodaddicts.util;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class S3Util {
    private static final String BUCKET = "food-addicts-images";

    @Value("${aws.accessKeyId}")
    private String ACCESS_KEY;

    @Value("${aws.secretKey}")
    private String SECRET_KEY;

    public String uploadFile(String fileName, InputStream inputStream) {
        System.out.format("Uploading %s to S3 bucket %s...%n", fileName, BUCKET);
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1)
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY)))
                .build();
        s3.putObject(BUCKET, fileName, inputStream, null);
        return s3.getUrl(BUCKET, fileName).toString();
    }
}
