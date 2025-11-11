package org.example.tutorial_2_homework.manish_airbnb_clone.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.UserEntity;
import org.example.tutorial_2_homework.manish_airbnb_clone.service.JwtService;
import org.example.tutorial_2_homework.manish_airbnb_clone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
@ToString
public class JwtAuthFilters extends OncePerRequestFilter {


    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            /* JWT Token sample look like -> Authorization: Bearer <token>
                 Example:
                 Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIn0.TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ
                 */
            final String requestTokenHeader = request.getHeader("Authorization");

            // If header is missing or invalid, skip JWT processing entirely
            if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {
                log.warn("JWT Token missing or does not start with 'Bearer '");
                filterChain.doFilter(request, response);
                return; // <-- important: stop execution here!
            }

            // Extract JWT token
            String token = requestTokenHeader.substring(7); // remove "Bearer "
            Long userId = jwtService.getUserIdFromToken(token);

            // Validate token and set authentication context
            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserEntity userEntity = (UserEntity) userService.getUserById(userId);

                if (userEntity != null) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userEntity, null, userEntity.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    log.warn("User not found for ID {}", userId);
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            handlerExceptionResolver.resolveException(request, response, null, ex);
        }
    }
}