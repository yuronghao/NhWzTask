package com.emi.sys.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * 
 * @Title:数据库字段注解
 * @Copyright: Copyright (c) v1.0
 * @Company: 江苏一米智能科技股份有限公司
 * @project name: 一米通讯
 * @author: 朱晓陈
 * @version: V1.0
 * @time: 2014年9月10日 上午11:27:17
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface EmiColumn{
// Types type();
	String name();				//列名
	boolean ID() default false;	//是否是id
	boolean increment() default false;	//是否新增的时候赋值
	boolean hasDefault() default false;//是否有默认值 目前仅支持int、varchar、nvarchar类型，默认值不要带'，以后有需要再拓展或修改
}