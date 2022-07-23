
package com.aojun.user.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liu jian
 * @date 2020-05-26 13:03:22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("oauth_client_details")
public class OauthClientDetails extends Model<OauthClientDetails> {

    /**
     *
     */
    @TableId(value = "client_id",type = IdType.ASSIGN_ID)
    private String clientId;
    /**
     *
     */
    private String resourceIds;
    /**
     *
     */
    private String clientSecret;
    /**
     *
     */
    private String scope;
    /**
     *
     */
    private String authorizedGrantTypes;
    /**
     *
     */
    private String webServerRedirectUri;
    /**
     *
     */
    private String authorities;
    /**
     *
     */
    private Integer accessTokenValidity;
    /**
     *
     */
    private Integer refreshTokenValidity;
    /**
     *
     */
    private String additionalInformation;
    /**
     *
     */
    private String autoapprove;

}
