package com.xkeshi.apigateway.hystrix;

import java.io.IOException;
import java.io.InputStream;
import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

/**
 * Created by ruancl@xkeshi.com on 2017/4/7.
 * 自定义熔断callback
 */
@Component
public class FallBack implements ZuulFallbackProvider {

  @Override
  public String getRoute() {
    return "client-rcl2";
  }

  @Override
  public ClientHttpResponse fallbackResponse() {
    return new ClientHttpResponse() {
      @Override
      public HttpStatus getStatusCode() throws IOException {
        return HttpStatus.BAD_GATEWAY;
      }

      @Override
      public int getRawStatusCode() throws IOException {
        return 0;
      }

      @Override
      public String getStatusText() throws IOException {
        return "sbsbsbsb";
      }

      @Override
      public void close() {

      }

      @Override
      public InputStream getBody() throws IOException {
        return null;
      }

      @Override
      public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
      }
    };
  }
}
