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

@Service
@RequiredArgsConstructor
@Transactional
public class GCSServiceImpl implements GCSService {

    private final RandomBucketRepository randomBucketRepository;

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

        // 새로운 RandomBucket 엔티티 생성 후 저장 (ID 자동 생성)
        RandomBucket newBucket = new RandomBucket();
        RandomBucket savedBucket = randomBucketRepository.save(newBucket);

        // 생성된 ID 값 가져오기
        Long newFileId = savedBucket.getId();

        // 원본 파일 확장자 유지
        String fileExtension = getFileExtension(file.getOriginalFilename());
        String newFileName = newFileId + fileExtension;

        // 서비스 계정 키 파일을 불러옴
        String keyFileName = "graphite-ally-448608-t2-42698c9bcc52.json";
        InputStream keyFile = ResourceUtils.getURL("classpath:" + keyFileName).openStream();

        // Google Cloud Storage 클라이언트 설정
        Storage storage = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(keyFile))
                .build()
                .getService();

        // BlobInfo를 생성하여 파일의 메타데이터 설정
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, newFileName)
                .setContentType(file.getContentType())
                .build();

        // 파일 업로드
        Blob blob = storage.create(blobInfo, file.getInputStream());

        // 업로드된 파일의 public URL 반환
        return String.format("https://storage.googleapis.com/%s/%s", bucketName, newFileName);
    }

    // 파일 확장자 추출 함수
    private String getFileExtension(String fileName) {
        return fileName != null && fileName.contains(".")
                ? fileName.substring(fileName.lastIndexOf("."))
                : "";
    }
}