package com.bamboo.firstdemo.service;

import com.bamboo.firstdemo.controller.bean.BookRequest;
import com.bamboo.firstdemo.controller.vo.BookConfirmVO;

public interface BusinessService {
    BookConfirmVO book(BookRequest bookRequest);


}
