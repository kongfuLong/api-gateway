package com.xkeshi.apigateway.mongo;

import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by ruancl@xkeshi.com on 2017/4/13.
 */
@Document
public class RouteConfig {

    @Id
    private Long id;

    @Field("email")
    @Indexed(unique = true)
    private String serviceId;

    private String serviceName;

    private String pathReg;

    private Boolean stripPrefix = Boolean.TRUE;

    private Boolean retryable;

    private String url;

    private Set<String> sensitiveHeaders = new LinkedHashSet<>();

    public RouteConfig(String serviceId, String pathReg) {
      this.serviceId = serviceId;
      this.pathReg = pathReg;
      this.serviceName = serviceId;
    }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getServiceId() {
    return serviceId;
  }

  public void setServiceId(String serviceId) {
    this.serviceId = serviceId;
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public String getPathReg() {
    return pathReg;
  }

  public void setPathReg(String pathReg) {
    this.pathReg = pathReg;
  }

  public Boolean getStripPrefix() {
    return stripPrefix;
  }

  public void setStripPrefix(Boolean stripPrefix) {
    this.stripPrefix = stripPrefix;
  }

  public Boolean getRetryable() {
    return retryable;
  }

  public void setRetryable(Boolean retryable) {
    this.retryable = retryable;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Set<String> getSensitiveHeaders() {
    return sensitiveHeaders;
  }

  public void setSensitiveHeaders(Set<String> sensitiveHeaders) {
    this.sensitiveHeaders = sensitiveHeaders;
  }
}
