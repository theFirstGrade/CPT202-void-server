package com.voidserver.service.impl;

import com.voidserver.entity.Application;
import com.voidserver.mapper.ApplicationMapper;
import com.voidserver.service.ApplicationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author void: ZHENHAO CHEN
 * @since 2021-04-06
 */
@Service
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application> implements ApplicationService {

}
