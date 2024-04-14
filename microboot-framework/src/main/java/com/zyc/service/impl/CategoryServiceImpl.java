package com.zyc.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyc.constants.SystemConstants;
import com.zyc.domain.ResponseResult;
import com.zyc.domain.entity.Article;
import com.zyc.domain.entity.Category;
import com.zyc.domain.vo.CategoryVo;
import com.zyc.mapper.ArticleMapper;
import com.zyc.mapper.CategoryMapper;
import com.zyc.service.ArticleService;
import com.zyc.service.CategoryService;
import com.zyc.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2024-04-14 14:57:49
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper , Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;
    @Override
    public ResponseResult getCategoryList() {
        //查询文章表，状态已经发布的
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus , SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list();
        //获取文章的分类id，并且去重
        Set<Long> collect = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());
        //查询分类表
        List<Category> categoryList = listByIds(collect);
        List<Category> categories = categoryList.stream()
                .filter(category -> SystemConstants.CATEGORY_STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装成vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }
}

