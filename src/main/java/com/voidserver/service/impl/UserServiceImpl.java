package com.voidserver.service.impl;

import com.voidserver.entity.User;
import com.voidserver.mapper.UserMapper;
import com.voidserver.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author void: ZHENHAO CHEN
 * @since 2021-04-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
