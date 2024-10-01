package com.ayang.usercenter.aop;

import com.ayang.usercenter.annotaion.AuthCheck;
import com.ayang.usercenter.common.ErrorCode;
import com.ayang.usercenter.exception.ThrowUtil;
import com.ayang.usercenter.model.entity.User;
import com.ayang.usercenter.model.enums.UserRoleEnum;
import com.ayang.usercenter.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 权限校验 aop
 *
 * @author <a href="https://github.com/AyangCodeLib">阿洋努力学习</a>
 */
@Aspect
@Component
public class AuthInterceptor {

    @Resource
    private UserService userService;

    /**
     * 执行拦截
     *
     * @param joinPoint
     * @param authCheck
     * @return
     * @throws Throwable
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        String mustRole = authCheck.mustRole();
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        // 当前登录用户
        User currentUser = userService.getCurrentUser(request);
        UserRoleEnum mustRoleEnum = UserRoleEnum.getEnumByValue(mustRole);
        // 不需要权限，放行
        if (mustRoleEnum == null) {
            return joinPoint.proceed();
        }
        // 必须有该权限才能通过
        UserRoleEnum userRoleEnum = UserRoleEnum.getEnumByValue(currentUser.getUserRole());
        ThrowUtil.throwIf(userRoleEnum == null, ErrorCode.NO_AUTH_ERROR);
        // 被封号，直接拒绝
        ThrowUtil.throwIf(Objects.equals(UserRoleEnum.BAN, userRoleEnum), ErrorCode.NO_AUTH_ERROR);
        // 必须有管理员权限
        if (Objects.equals(UserRoleEnum.ADMIN, mustRoleEnum)) {
            // 必须有该权限才能通过
            ThrowUtil.throwIf(!Objects.equals(UserRoleEnum.ADMIN, userRoleEnum), ErrorCode.NO_AUTH_ERROR);
        }
        // 权限通过
        return joinPoint.proceed();
    }
}
