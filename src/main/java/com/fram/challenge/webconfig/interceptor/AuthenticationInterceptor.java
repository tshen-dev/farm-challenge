package com.fram.challenge.webconfig.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

@Configuration
public class AuthenticationInterceptor implements HandlerInterceptor {

  @Value("${app.authorization-key}")
  private String authorizationKey;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    //could improve by JWT (Keycloak, ...)
    if (StringUtils.equals(authorizationKey, request.getHeader(HttpHeaders.AUTHORIZATION))) {
      return true;
    }
    response.sendError(HttpStatus.UNAUTHORIZED.value());
    return false;
  }
}
