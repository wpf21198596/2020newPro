package com.example.system.config;

import com.example.demo.entity.SysPermission;
import com.example.demo.entity.SysRole;
import com.example.demo.entity.UserInfo;
import com.example.demo.service.UserInfoService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserInfoService userInfoService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        UserInfo userInfo  = (UserInfo)principals.getPrimaryPrincipal();
        for(SysRole role:userInfo.getRoleList()){
            authorizationInfo.addRole(role.getRole());
            for(SysPermission p:role.getPermissions()){
                authorizationInfo.addStringPermission(p.getPermission());
            }
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
         //获取输入的用户账号，并通过账号获取相关信息
         String username = token.getPrincipal().toString();
         UserInfo user = userInfoService.findByUsername(username);
         if (user != null) {
             //将查询到的用户账号和密码存放到 authenticationInfo用于后面的权限判断。第三个参数传入用户输入的用户名。
             SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
             //设置盐，用来核对密码
             authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(user.getSalt()));
             return authenticationInfo;
         } else {
             return null;
         }
//        //获取用户的输入的账号.
//        String username = (String)token.getPrincipal();
//        Object credentials = token.getCredentials();
//        System.out.println(token.getCredentials());
//        //通过username从数据库中查找 User对象，如果找到，没找到.
//        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
//        UserInfo userInfo = userInfoService.findByUsername(username);
//        System.out.println("----->>userInfo="+userInfo);
//        if(userInfo == null){
//            return null;
//        }
//        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
//                userInfo.getUsername(), //用户名
//                userInfo.getPassword(), //密码
//                ByteSource.Util.bytes(userInfo.getSalt()),//salt=username+salt
////                ByteSource.Util.bytes(userInfo.getCredentialsSalt()),//salt=username+salt
//                getName()  //realm name
//        );
//        return authenticationInfo;
    }
}
