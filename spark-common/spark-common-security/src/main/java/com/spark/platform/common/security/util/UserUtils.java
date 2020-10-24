package com.spark.platform.common.security.util;

import com.spark.platform.adminapi.dto.UserDTO;
import com.spark.platform.adminapi.entity.user.User;
import com.spark.platform.adminapi.feign.client.UserClient;
import com.spark.platform.common.security.model.LoginUser;
import com.spark.platform.common.base.utils.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author: wangdingfeng
 * @ProjectName: spark-platform
 * @Package: com.spark.platform.common.security.util
 * @ClassName: UserUtils
 * @Description: 用户工具类
 * @Version: 1.0
 */
@Slf4j
public class UserUtils {

    /**
     * 静态内部类，延迟加载，懒汉式，线程安全的单例模式
     */
    private static final class Static {
        private static UserClient userClient = SpringContextHolder.getBean(UserClient.class);
    }

    /**
     * 获取登录用户信息
     * @return
     */
    public static LoginUser getLoginUser() {
        LoginUser loginUser = null;
        try {
            loginUser =(LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            log.info("============当前无登录信息，请注意================");
        }
        return loginUser;
    }

    /**
     * 获取用户信息
     * @return
     */
    public static User getUser(){
        LoginUser loginUser = getLoginUser();
        return  Static.userClient.getUserByUserId(loginUser.getId()).getData();
    }

    /**
     * 通过账号获取详细信息
     * @param username
     * @return
     */
    public static UserDTO getUser(String username){
        return  Static.userClient.getUserByUserName(username).getData();
    }

}
