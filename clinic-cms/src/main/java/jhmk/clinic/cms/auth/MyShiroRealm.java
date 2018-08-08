//package jhmk.clinic.cms.auth;
//
//import jhmk.clinic.entity.pojo.shiro.SysPermission;
//import jhmk.clinic.entity.pojo.shiro.SysRole;
//import jhmk.clinic.entity.pojo.shiro.UserInfo;
//import jhmk.clinic.entity.pojo.shiro.repository.UserInfoRepository;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationInfo;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.SimpleAuthenticationInfo;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.apache.shiro.subject.SimplePrincipalCollection;
//import org.apache.shiro.util.ByteSource;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// * @author ziyu.zhou
// * @date 2018/7/9 19:43
// */
//
//public class MyShiroRealm extends AuthorizingRealm {
//    @Autowired
//    UserInfoRepository repository;
//
//    /**
//     * 链接权限的实现
//     * <p>
//     * shiro的权限授权是通过继承AuthorizingRealm抽象类，重载doGetAuthorizationInfo();当访问到页面的时候，
//     * 链接配置了相应的权限或者shiro标签才会执行此方法否则不会执行，所以如果只是简单的身份认证没有权限的控制的话，
//     * 那么这个方法可以不进行实现，直接返回null即可。在这个方法中主要是使用类：SimpleAuthorizationInfo进行角色的添加和权限的添加。
//     *
//     * @param principals
//     * @return
//     */
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
//        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        UserInfo userInfo = (UserInfo) principals.getPrimaryPrincipal();
//        for (SysRole role : userInfo.getRoleList()) {
//            authorizationInfo.addRole(role.getRole());
//            for (SysPermission p : role.getPermissions()) {
//                authorizationInfo.addStringPermission(p.getPermission());
//            }
//        }
//        return authorizationInfo;
//    }
//
//
//    /**
//     * \
//     * <p>
//     * 1、检查提交的进行认证的令牌信息
//     * 2、根据令牌信息从数据源(通常为数据库)中获取用户信息
//     * 3、对用户信息进行匹配验证。
//     * 4、验证通过将返回一个封装了用户信息的AuthenticationInfo实例。
//     * 5、验证失败则抛出AuthenticationException异常信息。
//     *
//     * @param token
//     * @return
//     * @throws AuthenticationException
//     */
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
//            throws AuthenticationException {
//        System.out.println("MyShiroRealm.doGetAuthenticationInfo()");
//        //获取用户的输入的账号.
//        String username = (String) token.getPrincipal();
//        System.out.println(token.getCredentials());
//        //通过username从数据库中查找 User对象，如果找到，没找到.
//        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
//        UserInfo userInfo = repository.findByUsername(username);
//        System.out.println("----->>userInfo=" + userInfo);
//        if (userInfo == null) {
//            return null;
//        }
//        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
//                userInfo, //用户名
//                userInfo.getPassword(), //密码
//                ByteSource.Util.bytes(userInfo.getCredentialsSalt()),//salt=username+salt
//                getName()  //realm name
//        );
//        return authenticationInfo;
//    }
//
//    public void removeUserAuthorizationInfoCache(String username) {
//        SimplePrincipalCollection pc = new SimplePrincipalCollection();
//        pc.add(username, super.getName());
//        super.clearCachedAuthorizationInfo(pc);
//    }
//
//}
