package org.plteco.ploytechcourse.api.user.repassword;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.user.repassword.dto.PasswordRequestDTO;
import org.plteco.ploytechcourse.application.user.repassword.service.EmailValidation;
import org.plteco.ploytechcourse.application.user.signup.dto.EmailAndCodeDto;
import org.plteco.ploytechcourse.application.user.signup.dto.EmailDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/re")
@Tag(name = "RePassword-controller : 허동운")
public class RepasswordController {

    private final EmailValidation emailValidation;

    @Operation(
            summary = "이메일보냄",
            description = "네 보냅니다"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "가져오기 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping("/send")
    public ResponseEntity<Void> send(
            @RequestBody
            EmailDto emailDto
    ){
        emailValidation.sendEmail(emailDto);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "검증",
            description = "코드맞는지 확인"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "가져오기 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping("/validate")
    public ResponseEntity<Void> validate(
            @RequestBody
            EmailAndCodeDto emailAndCodeDto
    ){
        emailValidation.validate(emailAndCodeDto);
        return ResponseEntity.ok().build();
    }


    @Operation(
            summary = "새비밀번호 만들어요",
            description = "네 만듭니다"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "가져오기 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping("/make")
    public ResponseEntity<Void> make(
            @RequestBody
            PasswordRequestDTO passwordRequestDTO
    ){
        emailValidation.change(passwordRequestDTO);
        return ResponseEntity.ok().build();
    }
}
