package com.sun.booking.common.httpresponse;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListResponse {
  private List<?> content;
  private int curPage;
  private int curPageSize;
  private long totalElements;
  private int totalPages;
}
