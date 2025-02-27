package org.plteco.ploytechcourse.shared.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS 설정을 위한 클래스입니다.
 *
 * 이 클래스는 클라이언트와 서버 간의 출처 간 리소스 공유(CORS)를 허용하도록 설정합니다.
 * 이 설정은 특정 도메인에서 서버로 요청을 보낼 때 발생할 수 있는 CORS 오류를 방지합니다.
 */
@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

    /**
     * CORS 매핑을 설정하는 메서드입니다.
     *
     * @param corsRegistry CORS 설정을 위한 객체
     */
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*")
                .allowedOriginPatterns("https://ptc-front-bves.vercel.app") // 모든 출처 허용
                .allowedOriginPatterns("http://192.168.10.51:5173")
                .allowedOriginPatterns("http://192.168.45.90:5173")
                .allowedOriginPatterns("http://172.30.1.51:5173")
                .allowedOriginPatterns("http://192.168.219.110:5173")
                .allowCredentials(true); // 자격 증명 허용
    }
}
