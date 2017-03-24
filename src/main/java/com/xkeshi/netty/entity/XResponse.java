package com.xkeshi.netty.entity;


import java.util.Map;

/**
 * Created by ruancl@xkeshi.com on 2017/3/23.
 */
public class XResponse {

  private String content;

  private Map<String,String> headers;

  private Integer status;//参考http错误码

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public void setHeaders(Map<String, String> headers) {
    this.headers = headers;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }
}
