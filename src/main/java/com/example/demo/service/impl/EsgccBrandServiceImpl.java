package com.example.demo.service.impl;


import com.example.demo.bean.EsgccBrandPo;
import com.example.demo.mapper.EsgccBrandMapper;
import com.example.demo.service.EsgccBrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


/**
 * 品牌表
 *
 * @author panda
 * @date 2018-03-23 15:04:11
 */
@Service
@Slf4j
public class EsgccBrandServiceImpl implements EsgccBrandService {
    /**
     * 品牌主表
     */
    @Autowired
    private EsgccBrandMapper esgccBrandMapper;


    /**
     * 保存品牌
     *
     * @param list
     * @return
     */
    @Override
    public String saveBrands(List<EsgccBrandPo> list) {
        for (EsgccBrandPo po : list) {
            po.setId(UUID.randomUUID().toString().substring(0,31));
            esgccBrandMapper.insert(po);

        }
        return "id";
    }


    /**
     * 查询品牌列表
     *
     * @param esgccBrandPo
     * @return
     */
    @Override
    public List<EsgccBrandPo> selectBrandList(EsgccBrandPo esgccBrandPo) {
        return esgccBrandMapper.select(esgccBrandPo);

    }


}

