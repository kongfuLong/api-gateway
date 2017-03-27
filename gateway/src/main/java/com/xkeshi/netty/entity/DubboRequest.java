package com.xkeshi.netty.entity;

import java.util.Map;

/**
 * Created by ruancl@xkeshi.com on 2017/3/24.
 */
public class DubboRequest {

  private String api;

  private Map<String,String> headers;

  private String content;

  private DubboParam dubboParam;

  public DubboParam getDubboParam() {
    return dubboParam;
  }

  public void setDubboParam(DubboParam dubboParam) {
    this.dubboParam = dubboParam;
  }

  public String getApi() {
    return api;
  }

  public void setApi(String api) {
    this.api = api;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public void setHeaders(Map<String, String> headers) {
    this.headers = headers;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
