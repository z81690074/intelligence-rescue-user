package com.aojun.user.server.service.impl;

import com.aojun.user.server.service.OauthClientDetailsService;
import com.aojun.user.api.entity.OauthClientDetails;
import com.aojun.user.server.mapper.OauthClientDetailsMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails> implements OauthClientDetailsService {


}
