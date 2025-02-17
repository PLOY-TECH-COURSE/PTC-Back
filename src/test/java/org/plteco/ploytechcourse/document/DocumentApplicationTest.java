package org.plteco.ploytechcourse.document;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.plteco.ploytechcourse.application.document.service.DocumentServiceApplicationImpl;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.document.service.DocumentService;
import org.plteco.ploytechcourse.domain.document.service.Document_HashTagService;
import org.plteco.ploytechcourse.domain.document.service.HashTagService;
import org.plteco.ploytechcourse.domain.like.documentlike.service.DocumentLikeService;
import org.plteco.ploytechcourse.domain.user.signup.repository.UserRepository;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;

import java.util.List;

import static jdk.jfr.internal.jfc.model.Constraint.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DocumentApplicationTest {
    @Mock
    UserContextUtil userContextUtil;
    @Mock
    UserRepository userRepository;

    @Mock
    DocumentService documentService;
    @Mock
    HashTagService hashTagService;
    @Mock
    Document_HashTagService documentHashTagService;
    @Mock
    DocumentLikeService documentLikeService;

    @InjectMocks
    DocumentServiceApplicationImpl documentServiceApplication;

    @Test
    @DisplayName("글 조회 테스트")
    void writeDocumentTest() {
        // given
        Long start = 0L;
        Document document = Document.builder()
                .build();

        // when
        when(documentService.getDocuments(0L, 20L)).thenReturn(List.of());
    }
}
