package org.plteco.ploytechcourse.exception;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/null-test")
    public String triggerNullPointerException() {
        String value = null;
        throw new NullPointerException("null 넣지 마라 개쉥이");
//        return value.toString(); // NPE 발생
    }
}
