package com.example.demo.controller.base;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.utils.excel.ExcelProcessController;
import com.example.demo.utils.excel.ExcelType;
import com.example.demo.utils.excel.ExcelUtils;
import com.example.demo.utils.excel.read.*;
import com.example.demo.utils.excel.write.ExcelWriteContext;
import com.example.demo.utils.excel.write.ExcelWriteException;
import com.example.demo.utils.excel.write.ExcelWriteFieldMapping;
import com.example.demo.utils.excel.write.ExcelWriteSheetProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.ParameterizedType;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * @desctiption: 导入controller基类
 * @author: panda
 * @date: 2018/5/17 0:30
 */
@Slf4j
public abstract class BaseExcelController<T> {


    /**
     * 跳转到excel操作页面
     *
     * @return
     */
    final String IMPORT_PAGE = "common/import";

    @RequestMapping("importPage")
    public ModelAndView toPage() {
        return new ModelAndView(this.IMPORT_PAGE);
    }


    /**
     * 下载excel模版
     *
     * @param response
     */
    @RequestMapping("downloadTemplate")
    public void downloadTemplate(HttpServletResponse response) {
        try {
            ExcelWriteFieldMapping fieldMapping = getTemplateHeaders(setTemplateHeaders());
            wirteExcel(getTemplateName(), response, fieldMapping, null);
        } catch (Exception e) {
            log.error("导出模板失败", e);
        }
    }

    /**
     * 将数据导出到excel文件
     *
     * @param t
     * @param response
     */
    @RequestMapping("doExport")
    public void doExport(T t, HttpServletResponse response) {
        try {
            List dataList = getExportDateList(t);
            wirteExcel(getExportName(), response, setExportFieldMapping(), dataList);
        } catch (Exception e) {
            log.error("导出失败", e);
        }
    }

    /**
     * 写出excel
     *
     * @param fileName
     * @param response
     * @param fieldMapping
     * @param dataList
     * @throws IOException
     */
    private void wirteExcel(String fileName, HttpServletResponse response, ExcelWriteFieldMapping fieldMapping, List dataList) throws IOException {
        ExcelWriteSheetProcessor<T> excelWriteSheetProcessor = new ExcelWriteSheetProcessor<T>() {
            @Override
            public void beforeProcess(ExcelWriteContext<T> context) {
            }

            @Override
            public void onException(ExcelWriteContext<T> context, ExcelWriteException e) {
                log.error("导出异常", e);
            }

            @Override
            public void afterProcess(ExcelWriteContext<T> context) {

            }
        };
        excelWriteSheetProcessor.setSheetIndex(0);
        excelWriteSheetProcessor.setHeadRowIndex(0);
        excelWriteSheetProcessor.setStartRowIndex(1);
        excelWriteSheetProcessor.setFieldMapping(fieldMapping);
        excelWriteSheetProcessor.setDataList(dataList);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ExcelUtils.write(ExcelType.XLSX, os, excelWriteSheetProcessor);

        fileName += ".xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO-8859-1"));

        OutputStream outStream = response.getOutputStream();
        outStream.write(os.toByteArray());
        os.close();
        outStream.flush();
        outStream.close();
    }


    /**
     * 设置表头
     *
     * @param headers
     * @return
     */
    private ExcelWriteFieldMapping getTemplateHeaders(Map<String, String> headers) {
        ExcelWriteFieldMapping fieldMapping = new ExcelWriteFieldMapping();
        headers.forEach((k, v) -> {
            fieldMapping.put(k, v).setHead(v);
        });
        return fieldMapping;
    }

    /**
     * @desctiption: 获取导出的数据
     * @param:
     * @return:
     * @author: panda
     * @date: 2018/5/22 15:41
     */
    protected abstract List getExportDateList(T t);


    /**
     * 导入操作
     *
     * @param file
     * @return
     */
    @RequestMapping("doImport")
    @ResponseBody
    public JSONObject doImport(MultipartFile file) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 1);
        jsonObject.put("msg", "导入成功");
        InputStream in = null;
        try {
            in = file.getInputStream();
        } catch (IOException e) {
            log.error("导入，读取文件失败", e);
            jsonObject.put("code", 0);
            jsonObject.put("msg", "导入，读取文件失败");
        }
        ExcelReadSheetProcessor<T> sheetProcessor = new ExcelReadSheetProcessor<T>() {


            @Override
            public void beforeProcess(ExcelReadContext<T> context) {
            }

            /**
             * 导入的实际操作
             * @param context
             * @param list
             */
            @Override
            public void process(ExcelReadContext<T> context, List<T> list) {
                if (context.getFailRowCount() > 0) {
                    return;
                }
                importProcss(list);
            }

            /**
             * 发生异常的处理
             * @param context
             * @param e
             */
            @Override
            public void onException(ExcelReadContext<T> context, ExcelReadException e) {


            }

            /**
             * 数据的返回
             * @param context
             */
            @Override
            public void afterProcess(ExcelReadContext<T> context) {
                jsonObject.put("totalMsg", MessageFormat.format("成功{0}，失败{1}条", context.getSuccessRowCount(), context.getFailRowCount()));
                if (context.isHasError()) {
                    jsonObject.put("code", 0);
                    jsonObject.put("msg", "导入失败");
                    jsonObject.put("data", context.getErrorMessageList());
                }
            }
        };
        sheetProcessor.setFieldMapping(this.setImportFieldMapping());
        sheetProcessor.setRowProcessor(this.setRowProcessor());
        sheetProcessor.setSheetIndex(0);
        sheetProcessor.setHeadRowIndex(this.setHeadRowIndex());
        sheetProcessor.setStartRowIndex(this.setStartRowIndex());
        sheetProcessor.setPageSize(this.setPageSize());
        sheetProcessor.setTargetClass(this.setTargetClass());
        sheetProcessor.setTrimSpace(this.setTrimSpace());
        sheetProcessor.setDenyAll(this.setDenyAll());
        try {
            ExcelUtils.read(in, sheetProcessor);
        } catch (Exception e) {
            log.error("导入失败", e);
            jsonObject.put("code", 0);
            jsonObject.put("msg", "导入失败");
            jsonObject.put("data", e.getMessage());
        } finally {

        }
        return jsonObject;
    }

    /**
     * 导入实际操作
     *
     * @param list
     */
    protected abstract void importProcss(List<T> list);

    /**
     * @desctiption: 设置模板表头
     * @param:
     * @return:
     * @author: panda
     * @date: 2018/5/22 11:29
     */
    protected abstract Map<String, String> setTemplateHeaders();

    /**
     * @desctiption:设置导出表头
     * @param:
     * @return:
     * @author: panda
     * @date: 2018/5/22 15:42
     */
    protected abstract ExcelWriteFieldMapping setExportFieldMapping();

    protected abstract ExcelReadFieldMapping setImportFieldMapping();

    protected ExcelReadRowProcessor setRowProcessor() {
        return new ExcelReadRowProcessor<T>() {
            @Override
            public T process(ExcelProcessController controller, ExcelReadContext<T> context, Row row, T t) {
                return t;
            }
        };
    }

    protected Integer setPageSize() {
        return null;
    }

    protected Integer setHeadRowIndex() {
        return null;
    }

    protected Integer setStartRowIndex() {
        return 1;
    }

    protected boolean setTrimSpace() {
        return true;
    }

    protected Class setTargetClass() {
        Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }

    /**
     * 获取模版的名称
     *
     * @return
     */
    protected String getTemplateName() {
        return "模板.xls";
    }

    /**
     * 获取导出excel名称
     *
     * @return
     */
    protected String getExportName() {
        return "导出数据.xls";
    }

    /**
     * 是否需要验证全部数据
     *
     * @return
     */
    protected boolean setDenyAll() {
        return false;
    }

}
