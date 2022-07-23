package com.aojun.user.server.rpc;

import com.aojun.user.server.mapper.SysRoleMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RestController
@RequestMapping("/rpc/role")
public class SysRoleRpc {
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;


    @GetMapping("/queryMenuIdListByRoleIds")
    List<String> queryMenuIdListByRoleIds(@RequestParam("roleIds") List<Long> roleIds) {
        return sysRoleMenuMapper.queryMenuIdListByRoleIds(roleIds);
    }


}
