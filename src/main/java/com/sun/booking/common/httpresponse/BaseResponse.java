package com.sun.booking.common.httpresponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
  // data can be any type ( object or list of objects )
  private T data;
  private int statusCode;
  private String message;
}
