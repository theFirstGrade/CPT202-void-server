package com.voidserver.utils;

import org.apache.shiro.SecurityUtils;
import com.voidserver.shiro.AccountProfile;

public class ShiroUtil {

    public static AccountProfile getProfile() {
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }
}
