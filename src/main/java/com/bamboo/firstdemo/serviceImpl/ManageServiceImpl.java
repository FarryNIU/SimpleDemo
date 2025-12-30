package com.bamboo.firstdemo.serviceImpl;

import com.bamboo.firstdemo.bean.StudentRecordEntity;
import com.bamboo.firstdemo.controller.vo.StudentRecordVO;
import com.bamboo.firstdemo.dao.DataService;
import com.bamboo.firstdemo.dao.mapper.UserMapper;
import com.bamboo.firstdemo.service.ManageService;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.ibatis.cursor.Cursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManageServiceImpl implements ManageService {

    @Autowired
    private DataService dataService;
    @Autowired(required = false)
    private UserMapper userMapper;

    public List<StudentRecordVO> getAllRecords(){
        // 1. 数据库一次性查出 10 万条记录，内存瞬间吃掉几百 MB
        List<StudentRecordEntity> entities = dataService.findAllRecords();

        // 2. 转换成 VO，内存占用翻倍（Entities 和 VOs 同时存在）
        List<StudentRecordVO> vOs = entities.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        // 3. 返回给 Spring 框架进行 JSON 序列化
        // 在这个过程中，整个 List 无法被回收，直到序列化结束通过网络发送出去
        return vOs;
    }


    public void streamAllRecords(HttpServletResponse response) throws IOException {
        // 1. 设置响应头，告诉浏览器这是一个 JSON 流
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        // 2. 获取 Jackson 的 JsonGenerator，直接操作输出流
        ObjectMapper mapper = new ObjectMapper();
        try (JsonGenerator jsonGenerator = mapper.getFactory().createGenerator(response.getOutputStream())) {

            // 开始写入 JSON 数组符号 [
            jsonGenerator.writeStartArray();

            // 3. 使用 MyBatis 的 Cursor 流式查询，保证数据库连接不中断，但内存只持有一条数据
            try (Cursor<StudentRecordEntity> cursor = userMapper.scanAll()) {
                for (StudentRecordEntity entity : cursor) {
                    // 将 Entity 转换为 DTO/VO
                    StudentRecordVO vo = convertToVO(entity);

                    // 4. 直接将单条对象序列化并写入网络流
                    // 写完这一条，这个 vo 对象就失去了引用，可以被随后的 Minor GC 回收
                    jsonGenerator.writeObject(vo);

                    // 刷新缓冲区，确保数据实时发往前端
                    jsonGenerator.flush();
                }
            }

            // 结束写入 JSON 数组符号
            jsonGenerator.writeEndArray();
            jsonGenerator.flush();
        }
    }


    private StudentRecordVO convertToVO(StudentRecordEntity entity) {
        if (entity == null) {
            return null;
        }
        StudentRecordVO vo = new StudentRecordVO();

        // 基础字段赋值
        vo.setContractId(entity.getContractId());
        vo.setUserId(entity.getUserId());
        vo.setContractDate(entity.getContractDate());

        return vo;
    }
}
