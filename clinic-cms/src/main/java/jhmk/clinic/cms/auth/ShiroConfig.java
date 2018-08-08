//package jhmk.clinic.cms.auth;
//
//import org.apache.shiro.spring.LifecycleBeanPostProcessor;
//import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.DependsOn;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//
///**
// * @author ziyu.zhou
// * @date 2018/7/9 19:26
// */
//
//@Configuration
//public class ShiroConfig {
//    @Bean
//    public ShiroFilterFactoryBean shirFilter(DefaultWebSecurityManager securityManager) {
//        System.out.println("ShiroConfiguration.shirFilter()");
//        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        shiroFilterFactoryBean.setSecurityManager(securityManager);
//        //拦截器.
//        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
//        //注意过滤器配置顺序 不能颠倒
//        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了，登出后跳转配置的loginUrl
//        filterChainDefinitionMap.put("/logout", "logout");
//        // 配置不会被拦截的链接 顺序判断
//        filterChainDefinitionMap.put("/static/**", "anon");
//        filterChainDefinitionMap.put("/ajaxLogin", "anon");
//        filterChainDefinitionMap.put("/login", "anon");
//        //配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
//        shiroFilterFactoryBean.setLoginUrl("/cms/unauth");
//        filterChainDefinitionMap.put("/cms/**", "anon");
//        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
//        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
//        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
////        filterChainDefinitionMap.put("/clinic/patient/**", "authc,perm[/clinic/patient]");
//        filterChainDefinitionMap.put("/clinic/patient/**", "authc,roles[admin]");
//        filterChainDefinitionMap.put("/**", "authc");
//        //未授权界面;
//        shiroFilterFactoryBean.setUnauthorizedUrl("/cms/401");
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//        return shiroFilterFactoryBean;
//    }
//
//    @Bean
//    public MyShiroRealm myShiroRealm() {
//        MyShiroRealm myShiroRealm = new MyShiroRealm();
//        return myShiroRealm;
//    }
//
//
//    @Bean
//    public DefaultWebSecurityManager securityManager() {
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        securityManager.setRealm(myShiroRealm());
//        return securityManager;
//    }
//
//    /**
//     * 保证实现了Shiro内部lifecycle函数的bean执行
//     */
//    @Bean
//    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
//        return new LifecycleBeanPostProcessor();
//    }
//
//    /**
//     * 启用shrio授权注解拦截方式，AOP式方法级权限检查
//     */
//    @Bean
//    @DependsOn(value = "lifecycleBeanPostProcessor") //依赖其他bean的初始化
//    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
//        return new DefaultAdvisorAutoProxyCreator();
//    }
//
//    /**
//     * 加入注解的使用，不加入这个注解不生效 使用shiro框架提供的切面类，用于创建代理对象
//     * @param securityManager
//     * @return
//     */
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
//        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
//        return authorizationAttributeSourceAdvisor;
//    }
//
//}
