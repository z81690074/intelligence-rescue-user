package com.aojun.user.api.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(name = "user-server", fallback = UserRoleClient.SysUserRoleClientFallback.class)
public interface UserRoleClient {

    /**
     * 根据角色ID，获取菜单ID列表
     */
    @GetMapping("/rpc/role/queryMenuIdListByRoleIds")
    List<String> queryMenuIdListByRoleIds(@RequestParam("roleIds") List<Integer> roleIds);

    @Component
    @Slf4j
    class SysUserRoleClientFallback implements UserRoleClient {

        @Override
        public List<String> queryMenuIdListByRoleIds(List<Integer> roleIds) {
            log.error("调用服务: {}, 资源: {}异常", "system-module", "/rpc/role/queryMenuIdListByRoleIds");
            return null;
        }
    }
}
