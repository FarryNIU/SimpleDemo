package com.bamboo.firstdemo.controller;

import com.bamboo.firstdemo.bean.StudentRecordEntity;
import com.bamboo.firstdemo.controller.vo.StudentRecordVO;
import com.bamboo.firstdemo.service.BusinessService;
import com.bamboo.firstdemo.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ManageController {
    @Autowired
    ManageService manageService;

    @PostMapping("/allRecords")
    @ResponseBody
    public List<StudentRecordVO> getAllRecords() {
        return manageService.getAllRecords();
    }
}
