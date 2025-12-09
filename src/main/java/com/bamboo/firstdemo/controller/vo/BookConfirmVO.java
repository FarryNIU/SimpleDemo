package com.bamboo.firstdemo.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookConfirmVO {
    private boolean success;
    private String requestId;
    private String info;
}
