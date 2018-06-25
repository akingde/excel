package com.example.demo.service;


import com.example.demo.bean.EsgccBrandPo;

import java.util.List;

/**
 * 品牌
 *
 * @author panda
 * @date 2018-03-23 15:04:11
 */
public interface EsgccBrandService {
    /**
     * 保存品牌
     *
     * @param esgccBrandPo
     * @return
     */
    String saveBrands(List<EsgccBrandPo> esgccBrandPo);

    /**
     * 查询品牌列表
     *
     * @param esgccBrandPo 查询品牌入参
     * @return
     */
    List<EsgccBrandPo> selectBrandList(EsgccBrandPo esgccBrandPo);

}
