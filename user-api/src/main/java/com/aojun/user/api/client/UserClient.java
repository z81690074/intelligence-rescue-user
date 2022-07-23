package com.aojun.user.api.client;

import com.aojun.user.api.dto.UserInfo;
import com.aojun.common.base.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;



@FeignClient(name = "user-server", fallback = UserClient.UserServiceFallbackImpl.class)
public interface UserClient {

    /**
     * 通过用户名查询用户、角色信息
     *
     * @param username 用户名
     * @return UserVo
     */
    @GetMapping(value = "/rpc/user/getByName/{username}")
    Result<UserInfo> findUserByUsername(@PathVariable("username") String username);

    /**
     * 验证用户名是否唯一
     */
    @GetMapping("/rpc/user/validateUsername/{username}")
    boolean validateUsername(@PathVariable("username") String username);

    @Component
    @Slf4j
    class UserServiceFallbackImpl implements UserClient {

        @Override
        public Result<UserInfo> findUserByUsername(String username) {
            log.error("通过用户名查询用户异常:{}", username);
            return null;
        }

        @Override
        public boolean validateUsername(String username) {
            log.error("判断用户名是否存在，服务降级:{}",username);
            return false;
        }
    }


}
