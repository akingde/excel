package com.example.demo.controller.brand;

import com.example.demo.bean.EsgccBrandPo;
import com.example.demo.controller.base.BaseExcelController;
import com.example.demo.service.EsgccBrandService;
import com.example.demo.utils.excel.common.ExcelMessage;
import com.example.demo.utils.excel.read.ExcelCellValue;
import com.example.demo.utils.excel.read.ExcelReadCellCheck;
import com.example.demo.utils.excel.read.ExcelReadContext;
import com.example.demo.utils.excel.read.ExcelReadFieldMapping;
import com.example.demo.utils.excel.write.ExcelWriteFieldMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:品牌
 * @Author: panda
 * @Date:Created in 2018/5/21  10:11
 * @Modify By:
 */
@Controller
@RequestMapping("brand")
public class BrandController {


    /**
     * 跳转到excel操作页面
     *
     * @return
     */
    final String BRAND_LIST = "brand/list";

    @RequestMapping("/list")
    public ModelAndView toPage() {
        return new ModelAndView(this.BRAND_LIST);
    }





}
