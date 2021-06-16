package com.example.system.uitl;

import com.example.system.entity.UserDo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ShiroUtil {
    @Autowired
    private static SessionDAO sessionDAO;
    public static Subject getSubjct() {
        return SecurityUtils.getSubject();
    }
    public static UserDo getUser() {
        Object object = getSubjct().getPrincipal();
        return (UserDo)object;
    }
    public static Long getUserId() {
        return getUser().getId();
    }
    public static void logout() {
        getSubjct().logout();
    }

    public static List<Principal> getPrinciples() {
        List<Principal> principals = null;
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        return principals;
    }

    /**
     * 判断登陆用户是否拥有某个角色权限
     */
    public static boolean hasManagerRole(){
        List<String> roleIdList = new ArrayList<>();
        roleIdList.add("1");
        roleIdList.add("60");
        roleIdList.add("61");
        getSubjct().checkPermission("orderIn:orderIn:manager");
        return getSubjct().hasRole("orderIn:orderIn:manager");
//       return getSubjct().hasAllRoles(roleIdList);
    }
}