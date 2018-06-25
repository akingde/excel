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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:品牌导入
 * @Author: panda
 * @Date:Created in 2018/5/21  10:11
 * @Modify By:
 */
@Controller
@RequestMapping("brand/excel")
public class BrandExcelController extends BaseExcelController<EsgccBrandPo> {
    @Autowired
    private EsgccBrandService esgccBrandService;

    @Override
    protected String getTemplateName() {
        return "品牌导入模板";
    }

    /**
     * 设置excel的头文件
     *
     * @return
     */
    @Override
    protected Map<String, String> setTemplateHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("A", "品牌名");
        headers.put("B", "品牌英文名");
        headers.put("C", "排序序号");
        headers.put("D", "品牌首字母");
        headers.put("E", "品牌别名");
        headers.put("F", "品牌logo");
        headers.put("G", "品牌介绍");
        headers.put("H", "品牌创建时间/年");
        headers.put("I", "品牌授权凭证");
        headers.put("J", "品牌类型");
        return headers;
    }

    @Override
    protected String getExportName() {
        return "品牌导出数据";
    }

    /**
     * 设置导出字段映射关系
     *
     * @return
     */
    @Override
    protected ExcelWriteFieldMapping setExportFieldMapping() {
        ExcelWriteFieldMapping excelWriteFieldMapping = new ExcelWriteFieldMapping();

        excelWriteFieldMapping.put("A", "brandName").setHead("品牌名");
        excelWriteFieldMapping.put("B", "brandNumber").setHead("品牌编码");
        excelWriteFieldMapping.put("C", "englishName").setHead("品牌英文名");
        excelWriteFieldMapping.put("D", "brandSort").setHead("排序序号");
        excelWriteFieldMapping.put("E", "firstLetter").setHead("品牌首字母");
        excelWriteFieldMapping.put("F", "brandAlias").setHead("品牌别名");
        excelWriteFieldMapping.put("G", "brandLogoUrl").setHead("品牌logo");
        excelWriteFieldMapping.put("H", "brandDescribe").setHead("品牌介绍");
        excelWriteFieldMapping.put("I", "brandCreateTime").setHead("品牌创建时间/年");
        excelWriteFieldMapping.put("J", "brandCredenceFile").setHead("品牌授权凭证");

        excelWriteFieldMapping.put("K", "brandType").setHead("品牌类型");
        return excelWriteFieldMapping;
    }

    /**
     * 获取导出的数据
     *
     * @param esgccBrandPo
     * @return
     */
    @Override
    protected List<EsgccBrandPo> getExportDateList(EsgccBrandPo esgccBrandPo) {
        return esgccBrandService.selectBrandList(esgccBrandPo);
    }


    /******************************************导入导出分界线*********************************************/


    /**
     * 设置导入字段的映射关系和验证
     *
     * @return
     */
    @Override
    protected ExcelReadFieldMapping setImportFieldMapping() {
        ExcelReadFieldMapping fieldMapping = new ExcelReadFieldMapping();
        fieldMapping.put("A", "brandName").setRequired(true);
        fieldMapping.put("B", "englishName");
        fieldMapping.put("C", "brandSort").setCellCheck(new ExcelReadCellCheck() {
            @Override
            public boolean check(ExcelReadContext<?> context, ExcelCellValue cellValue, ExcelMessage excelMessage) {
                String value = cellValue.getStringValue();
                try {
                    Integer.valueOf(value);
                } catch (Exception e) {
                    excelMessage.setMessage("品牌排序需要一个数字,"+value+"不是一个数字");
                    return false;
                }
                return true;
            }
        });
        fieldMapping.put("D", "firstLetter").setCellCheck(new ExcelReadCellCheck() {
            @Override
            public boolean check(ExcelReadContext<?> context, ExcelCellValue cellValue, ExcelMessage excelMessage) {
                String value = cellValue.getStringValue();
                char c = value.charAt(0);
                if (c < 'A' || c >= 'Z') {
                    excelMessage.setMessage("品牌首字母是一个大写的字母,"+value+"不是一个大写字母");
                    return false;
                } else {
                    return true;
                }
            }
        });
        fieldMapping.put("E", "brandAlias");
        fieldMapping.put("F", "brandLogoUrl");
        fieldMapping.put("G", "brandDescribe");
        fieldMapping.put("H", "brandCreateTime");
        fieldMapping.put("I", "brandCredenceFile");

        fieldMapping.put("J", "brandType");

        return fieldMapping;
    }

    /**
     * 导入的实际操作
     *
     * @param list
     */
    @Override
    protected void importProcss(List<EsgccBrandPo> list) {
        if (!CollectionUtils.isEmpty(list)) {
            esgccBrandService.saveBrands(list);
        }
    }




}
