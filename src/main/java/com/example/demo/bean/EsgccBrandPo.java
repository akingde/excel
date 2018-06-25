package com.example.demo.bean;


import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * 品牌表
 *
 * @author panda
 * @date 2018-03-26 11:25:14
 */
@Data
@Table(name = "esgcc_brand")
public class EsgccBrandPo {


    /**
     * ID
     */
    @Id
    private String id;

    /**
     * 品牌编号
     */
    private String brandNumber;

    /**
     * 品牌的英文名称
     */
    private String englishName;

    /**
     * 品牌的首字母
     */
    private String firstLetter;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 品牌描述
     */
    private String brandDescribe;

    /**
     * 品牌LOGO地址
     */
    private String brandLogoUrl;

    /**
     * 品牌链接地址
     */
    private String brandHrefUrl;

    /**
     * 品牌类型:1是国内品牌,2是国际品牌
     */
    private Integer brandType;

    /**
     * 状态 0-不可用 1-可用
     */
    private Integer brandStatus;

    /**
     * 类别名称
     */
    private String brandClass;

    /**
     * 品牌图片
     */
    private String brandPic;

    /**
     * 排序
     */
    private Integer brandSort;

    /**
     * 推荐，0为否，1为是，默认为0
     */
    private Integer brandRecommend;

    /**
     * 品牌申请，0为申请中，1为通过，默认为1，申请功能是会员使用，系统后台默认为1
     */
    private Integer brandApply;

    /**
     * 品牌别名
     */
    private String brandAlias;
    /**
     * 品牌凭证文件
     */
    private String brandCredenceFile;
    /**
     * 品牌创建时间
     */
    private String brandCreateTime;


    /**
     * 店铺ID
     */
    private String storeId;


    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 修改人
     */
    private String updateUser;


}
