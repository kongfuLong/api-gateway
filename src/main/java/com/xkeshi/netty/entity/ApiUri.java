package com.xkeshi.netty.entity;

/**
 * Created by ruancl@xkeshi.com on 2017/3/23.
 */
public class ApiUri {

  private String name;

  private String host;

  private int port;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getUrl(){
    return String.format("http://%s:%s",host,port);
  }
}
