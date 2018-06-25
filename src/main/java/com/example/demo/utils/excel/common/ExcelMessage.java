package com.example.demo.utils.excel.common;

import lombok.Data;

import java.text.MessageFormat;

/**
 * @Description:
 * @Author: panda
 * @Date:Created in 2018/5/21  17:26
 * @Modify By:
 */
@Data
public class ExcelMessage {
    private String sheetName;
    private int rowIndex;
    private int colIndex;
    private String message;

    @Override
    public String toString() {
        return MessageFormat.format("表{0}，行{1}，列{2}，信息：{3}", this.sheetName, this.rowIndex, this.colIndex, this.message);
    }

}
