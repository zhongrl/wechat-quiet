package com.quiet.live.hall.constants;
public interface SysConstant {
    interface Redis{
    	String LOGIN_MENU = "'menu_user_'";//登录菜单
    	String LOGIN_MENU_MAP = "'menu_user_map";//登录菜单
    	String LOGIN_MENU_AUTH = "'menu_user_auth";//登录菜单
    	String LOGIN_MENU_CACHENAME = "login_menu_user";//登录菜单
        String USER_CACHE="sys_user";
        String USER_INFO="'_userinfo_'";
    }
    
    interface ErrorCode
    {
    	String LOGIN_NOT_ACCOUNT = "001000001";//账号不存在
    	String LOGIN_ACCOUNT_PASSWORD_ERROR = "001000002";//密码错误
    	String LOGIN_ACCOUNT_DISABLE = "001000003";//账号无效
    	
       	String ORG_UNIQUE_ERROR = "001000004";//orgCode重复
       	
       	String ROLE_NAME_UNIQUE_ERROR = "002000001";//名称重复
       	String ROLE_CODE_UNIQUE_ERROR = "002000002";//角色重复
       	String ROLE_CODE_NAME_UNIQUE_ERROR = "002000003";//角色名称与编码重复
       	
       	String ACCOUNT_UNIQUE_ERROR = "003000001";//账号重复
       	
       	String SEND_EMAIL_ERROR = "004000001"; //邮箱错误
       	String SEND_NOTEMAIL_ERROR = "004000002"; //邮箱不存在
       	String SEND_VERIFICATION_NULL_ERROR = "004000003"; //验证码不能为空
       	String NULL_ACCOUNT = "004000004";//账号不能为空
       	String VERIFICATION_ERROR = "004000005"; //验证码错误
       	String EMAIL_NOTNULL_ERROR = "004000006"; //邮箱不能为空
       	String SEND_EMAIL_CF = "004000007"; //邮箱重复发送
    }

    interface ErrorInfo
    {
    	String ORG_NAME_UNIQUE_ERROR = "004000001";//组织名称重复
       	String ORG_CODE_UNIQUE_ERROR = "004000002";//组织编码重复
       	String ORG_CODE_NAME_UNIQUE_ERROR = "004000003";//组织编码与名称重复
       	
       	String USER_CODE_ERROR = "005000001";//admin用户不能修改删除禁用
       	String USER_PASSWORD_ERROR = "005000002";//原密码
       	String USER_NEW_PASSWORD_ERROR = "005000003";//原密码
    }
}