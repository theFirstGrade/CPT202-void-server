package com.voidserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    /**
     * @param updateWrapper
     * @return
     */
    @Override
    public boolean update(Wrapper<T> updateWrapper) {
        T entity = updateWrapper.getEntity();
        if (null == entity) {
            try {
                entity = this.currentModelClass().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return update(entity, updateWrapper);
    }
}


