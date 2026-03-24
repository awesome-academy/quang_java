package com.sun.booking.common.httpresponse;

import org.springframework.http.HttpStatus;

public class FailedResponse<T> extends BaseResponse<T> {
  public FailedResponse(String message) {
    super(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
  }
  
}
