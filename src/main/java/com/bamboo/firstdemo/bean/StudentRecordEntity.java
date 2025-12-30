package com.bamboo.firstdemo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentRecordEntity {
    private String userId;
    private String contractId;
    private String contractDate;
}
