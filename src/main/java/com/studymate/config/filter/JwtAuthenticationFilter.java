package com.studymate.config.filter;

import com.studymate.config.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            // 1. Request Header 에서 JWT 토큰 추출
            String token = getTokenFromRequest(request);

            // 2. 토큰 유효성 검증
            if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {

                // 3. 토큰에서 userId 추출
                String userId = jwtTokenProvider.getUserIdFromToken(token);

                //4 Spring Security에 인증 정보 저장
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userId,
                                null,
                                Collections.emptyList()
                        );

                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);

                log.info("JWT 인증 성공: userid = {}", userId);
            }
        } catch (Exception e) {
            log.error("JWT 인증 실패", e);
        }

        // 5. 다음 필터로 넘김
        filterChain.doFilter(request, response);
    }

    /**
     * Request Header에서 토큰 추출
     */
    private String getTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7); // "Bearer " 제거
        }

        return null;
    }
}
