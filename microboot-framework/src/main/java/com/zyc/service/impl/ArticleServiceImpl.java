package com.zyc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyc.constants.SystemConstants;
import com.zyc.domain.ResponseResult;
import com.zyc.domain.entity.Article;
import com.zyc.domain.entity.Category;
import com.zyc.domain.vo.ArticleListVo;
import com.zyc.domain.vo.HotArticleVo;
import com.zyc.domain.vo.PageVo;
import com.zyc.mapper.ArticleMapper;
import com.zyc.service.ArticleService;
import com.zyc.service.CategoryService;
import com.zyc.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CategoryService categoryService;
    @Override
    public ResponseResult hotArticleList() {
        //创建条件构造器
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper();
        //条件，必须为正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //条件，必须降序
        queryWrapper.orderByDesc(Article::getViewCount);
        //进行分页查询，只返回十条热门文章
        Page<Article> page = new Page(1,10);
        page(page,queryWrapper);
        //取出结果
        List<Article> list = page.getRecords();
        //bean拷贝
        List<HotArticleVo> voList = BeanCopyUtils.copyBeanList(list, HotArticleVo.class);
        //返回结果
        return ResponseResult.okResult(voList);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper();
        //如果有categoryId，查询时要和传入的相同
        queryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);
        //状态是正式发布的
        queryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        //对isTop进行降序
        queryWrapper.orderByDesc(Article::getIsTop);
        //分页查询
        Page<Article> page = new Page<>();
        page(page,queryWrapper);
        //查询categoryName
        List<Article> articles = page.getRecords();
        articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList())
        ;
//        for (Article article : articles) {
//            Category category = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        }
        //进行vo封装
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
}
