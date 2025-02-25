package org.plteco.ploytechcourse.application.document.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import org.plteco.ploytechcourse.application.document.dto.DocumentInfoDTO;
import org.plteco.ploytechcourse.application.document.dto.DocumentUserInfoDTO;

import java.util.List;

public record DocumentDetailGetResponseDTO(
        @NotNull(message = "글 자세히 보기에 document(글 정보)가 비어있습니다.")
        @JsonProperty("document")
        DocumentInfoDTO documentInfoDTO,

        @NotNull(message = "글 자세히 보기에 user_id(작성자 id)이 비어있습니다.")
        DocumentUserInfoDTO userInfoDTO,

        @NotNull(message = "글 자세히 보기에 likes(글 좋아요)가 비어있습니다.")
        Long likes,

        @NotNull(message = "글 자세히 보기에 like_on(좋아요 선택)이 비어있습니다.")
        boolean like_on,

        @NotNull(message = "글 자세히 보기에 favorite_on(즐겨찾기)이 비어있습니다.")
        boolean favorite_on,

        String generation,

        @NotNull(message = "글 자세히 보기에 hash_tag(해시 테그)가 비어있습니다.")
        List<String> hash_tag
) {}