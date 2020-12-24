package com.qin.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qin.entity.MBlog;
import com.qin.mapper.MBlogMapper;
import com.qin.service.IMBlogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qin
 * @since 2020-11-17
 */
@Service
public class MBlogServiceImpl extends ServiceImpl<MBlogMapper, MBlog> implements IMBlogService {

}
