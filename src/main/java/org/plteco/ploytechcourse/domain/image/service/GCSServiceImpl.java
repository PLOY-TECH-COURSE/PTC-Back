package org.plteco.ploytechcourse.domain.image.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class GCSServiceImpl implements GCSService {

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    /**
     * 파일을 Google Cloud Storage에 업로드하고 업로드된 파일의 URL을 반환합니다.
     *
     * @param file 업로드할 파일
     * @return 업로드된 파일의 URL
     * @throws IOException 업로드 중 발생한 예외
     */
    public String uploadObject(MultipartFile file) throws IOException {

        // 서비스 계정 키 파일을 불러옵니다.
        String keyFileName = "graphite-ally-448608-t2-42698c9bcc52.json";
        InputStream keyFile = ResourceUtils.getURL("classpath:" + keyFileName).openStream();

        // Google Cloud Storage 클라이언트를 설정합니다.
        Storage storage = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(keyFile))
                .build()
                .getService();

        // BlobInfo를 생성하여 파일의 메타데이터를 설정합니다.
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, file.getOriginalFilename())
                .setContentType(file.getContentType())
                .build();

        // 파일을 업로드합니다.
        Blob blob = storage.create(blobInfo, file.getInputStream());

        // 업로드된 파일의 public URL을 생성하여 반환합니다.
        String fileUrl = String.format("https://storage.googleapis.com/%s/%s", bucketName, file.getOriginalFilename());

        return fileUrl;
    }
}