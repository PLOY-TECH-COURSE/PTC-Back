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

        // 모든 경로에 대해 CORS를 허용
//        corsRegistry.addMapping("/**")
//                // 특정 출처에서의 요청만 허용 https://ptc-front-bves.vercel.app
//                .allowedOrigins("https://ptc-front-bves.vercel.app"); // 프론트엔드가 호스팅되는 URL을 설정해야 함.
                corsRegistry.addMapping("/**")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .allowedOrigins("*");
    }
}
