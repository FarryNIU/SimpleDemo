package com.bamboo.firstdemo.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentRecordVO {
    private String userId;
    private String contractId;
    private String contractDate;
}
