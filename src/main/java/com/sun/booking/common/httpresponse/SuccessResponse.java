package com.sun.booking.common.httpresponse;

import org.springframework.http.HttpStatus;

public class SuccessResponse<T> extends BaseResponse<T> {
  public SuccessResponse(T data) {
    super(data, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase());
  }
}
