package com.aojun.user.api.client;

import com.aojun.user.api.entity.SysThirdSign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "user-server", fallback = ThirdSignClient.ThirdSignFallbackImpl.class)
public interface ThirdSignClient {

    @GetMapping("/rpc/sign/getThirdSignInfo")
    SysThirdSign getThirdSignInfo(@RequestParam("thirdType") String thirdType);

    @Component
    @Slf4j
    class ThirdSignFallbackImpl implements ThirdSignClient {

        @Override
        public SysThirdSign getThirdSignInfo(String thirdType) {
            log.error("获取第三方对接秘钥服务降级:{}", thirdType);
            return null;
        }
    }
}
