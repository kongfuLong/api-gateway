package com.xkeshi.netty.exception;

import com.xkeshi.core.exceptions.XRuntimeException;

/**
 * Created by ruancl@xkeshi.com on 2017/3/24.
 */
public class XGatewayException extends XRuntimeException{

  public XGatewayException(String message) {
    super(message, new Throwable());
  }
}
