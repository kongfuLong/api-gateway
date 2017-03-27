package com.xkeshi.netty.entity;

/**
 * Created by ruancl@xkeshi.com on 2017/3/24.
 */
public class DubboParam {

  private String interfaceName;

  private String version;

  private String method;

  private String returnType;

  private String param;

  public String getInterfaceName() {
    return interfaceName;
  }

  public void setInterfaceName(String interfaceName) {
    this.interfaceName = interfaceName;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getReturnType() {
    return returnType;
  }

  public void setReturnType(String returnType) {
    this.returnType = returnType;
  }

  public String getParam() {
    return param;
  }

  public void setParam(String param) {
    this.param = param;
  }
}
