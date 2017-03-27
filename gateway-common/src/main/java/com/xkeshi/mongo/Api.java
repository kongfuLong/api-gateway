package com.xkeshi.mongo;

import java.math.BigInteger;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by ruancl@xkeshi.com on 2017/3/23.
 */
@Document
public class Api {

  @Id
  private BigInteger id;

  /**
   * Returns the identifier of the document.
   *
   * @return the id
   */
  public BigInteger getId() {
    return id;
  }


  private String name;

  private String host;

  private int port;

  public Api() {
  }

  public Api(String name, String host, int port) {
    this.name = name;
    this.host = host;
    this.port = port;
  }

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Api api = (Api) o;

    return name.equals(api.name);

  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }
}
