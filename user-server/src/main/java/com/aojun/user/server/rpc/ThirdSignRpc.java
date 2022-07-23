package com.aojun.user.server.rpc;

import com.aojun.user.api.entity.SysThirdSign;
import com.aojun.user.server.mapper.SysThirdSignMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 第三方认证签名rpc
 **/
@RestController
@RequestMapping("/rpc/sign")
public class ThirdSignRpc {

    @Autowired
    private SysThirdSignMapper sysThirdSignMapper;

    @GetMapping("/getThirdSignInfo")
    public SysThirdSign getThirdSignInfo(@RequestParam("thirdType") String thirdType) {
        return sysThirdSignMapper.selectOne(new QueryWrapper<SysThirdSign>().eq("third_type", thirdType));
    }

}
