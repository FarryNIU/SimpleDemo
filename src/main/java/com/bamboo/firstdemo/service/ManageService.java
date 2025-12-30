package com.bamboo.firstdemo.service;

import com.bamboo.firstdemo.controller.vo.StudentRecordVO;

import java.util.List;

public interface ManageService {
    List<StudentRecordVO> getAllRecords();
}
