package com.arthur.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * Created by Administrator on 1/4/2016.
 */
public class CustomRealm extends AuthorizingRealm {
    /**
     * ????????????????
     * principals  ???
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String)principalCollection.fromRealm(getName()).iterator().next();

         /*这些代码应该是动态从数据库中取出的，此处写死*/
        if(username!=null&&username.equals("admin")){
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            info.addRole("admin");//添加一个角色，不是配置意义上的添加，而是证明该用户拥有admin角色
            info.addStringPermission("admin:manage");//添加权限
            return info;
        }
        return null;
    }

    /**
     * ????
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //令牌——基于用户名和密码的令牌
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //令牌中可以取出用户名密码
        String accountName = token.getUsername();

        return new SimpleAuthenticationInfo("admin","admin123",getName());
    }
}
