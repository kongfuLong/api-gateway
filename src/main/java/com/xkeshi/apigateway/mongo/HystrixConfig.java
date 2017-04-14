package com.xkeshi.apigateway.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * Created by ruancl@xkeshi.com on 2017/4/14.
 */
public class HystrixConfig {

  @Id
  private String id;

  @Indexed(unique = true)
  private String serviceId;

  /**
   * 链接超时时间
   */
  private Integer executionTimeoutInMilliseconds = 1000;
  /**
   * 最大并发数
   */
  private Integer executionIsolationSemaphoreMaxConcurrentRequests = 10;

  public HystrixConfig() {
  }

  public HystrixConfig(String serviceId) {
    this.serviceId = serviceId;
  }

  public HystrixConfig(String serviceId, Integer executionTimeoutInMilliseconds,
      Integer executionIsolationSemaphoreMaxConcurrentRequests) {
    this.serviceId = serviceId;
    this.executionTimeoutInMilliseconds = executionTimeoutInMilliseconds;
    this.executionIsolationSemaphoreMaxConcurrentRequests = executionIsolationSemaphoreMaxConcurrentRequests;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getServiceId() {
    return serviceId;
  }

  public void setServiceId(String serviceId) {
    this.serviceId = serviceId;
  }

  public Integer getExecutionTimeoutInMilliseconds() {
    return executionTimeoutInMilliseconds;
  }

  public void setExecutionTimeoutInMilliseconds(Integer executionTimeoutInMilliseconds) {
    this.executionTimeoutInMilliseconds = executionTimeoutInMilliseconds;
  }

  public Integer getExecutionIsolationSemaphoreMaxConcurrentRequests() {
    return executionIsolationSemaphoreMaxConcurrentRequests;
  }

  public void setExecutionIsolationSemaphoreMaxConcurrentRequests(
      Integer executionIsolationSemaphoreMaxConcurrentRequests) {
    this.executionIsolationSemaphoreMaxConcurrentRequests = executionIsolationSemaphoreMaxConcurrentRequests;
  }
}
