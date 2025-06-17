package org.plteco.ploytechcourse.domain.image.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.image.data.entity.RandomBucket;
import org.plteco.ploytechcourse.domain.image.repository.RandomBucketRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class GCSServiceImpl implements GCSService {

    @Value("${spring.cloud.gcp.storage.credentials.location}")
    private String keyFileName;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    private Storage getStorage() throws IOException {
        InputStream keyFile = ResourceUtils.getURL(keyFileName).openStream();
        return StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(keyFile))
                .build()
                .getService();
    }

    /**
     * 이미지 업로드 실행
     * @param multipartFile 업로드할 파일
     * @return GCS URL
     * @throws IOException 파일 읽기 실패 시
     */
    public String uploadObject(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String uuid = UUID.randomUUID().toString();
        String contentType = multipartFile.getContentType();

        Storage storage = getStorage();

        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, uuid)
                .setContentType(contentType)
                .build();

        storage.create(blobInfo, multipartFile.getInputStream());

        return "https://storage.googleapis.com/" + bucketName + "/" + uuid;
    }

    /**
     * 이미지 삭제
     * @param imageUrl 전체 GCS URL
     * @throws IOException 인증 파일 실패 시
     */
    public void delete(String imageUrl) throws IOException {
        String fileName = extractFileNameFromUrl(imageUrl);

        Storage storage = getStorage();

        boolean deleted = storage.delete(bucketName, fileName);
        if (!deleted) {
            throw new IllegalArgumentException("이미지를 찾을 수 없거나 삭제에 실패했습니다: " + imageUrl);
        }
    }

    /**
     * GCS URL에서 파일 이름만 추출
     * @param url 전체 이미지 URL
     * @return 파일 이름
     */
    private String extractFileNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

}