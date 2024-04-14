package com.zyc.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zyc.domain.ResponseResult;
import com.zyc.domain.entity.Category;

/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2024-04-14 14:57:48
 */
public interface CategoryService extends IService<Category> {
    ResponseResult getCategoryList();
}

