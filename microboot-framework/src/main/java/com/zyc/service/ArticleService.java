package com.zyc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyc.domain.ResponseResult;
import com.zyc.domain.entity.Article;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);
}
