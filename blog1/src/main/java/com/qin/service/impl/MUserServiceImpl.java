package com.qin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qin.entity.MUser;
import com.qin.mapper.MUserMapper;
import com.qin.service.IMUserService;
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
public class MUserServiceImpl extends ServiceImpl<MUserMapper, MUser> implements IMUserService {

}
