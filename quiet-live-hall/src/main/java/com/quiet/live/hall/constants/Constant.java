package com.quiet.live.hall.constants;
public interface Constant {
    interface Redis{
    	String CACHE_NAME = "foo";
        String ACCESS_TOKEN = "access.";
        String PASSWORD = "password.";
        String PASSWORD_UPDATE = "password.update.";
        String ACCESS_TOKEN_AUTH = "access.auth";
        String ACCESS_TRYS = "access.trys.";
        String SYS_HOMEPAGE = "homepage";
        String ACCESS_EMAIL_SEND = "send.email.";
        String ACCESS_IP_SEND = "send.ip.";
    }

    interface CommonCode {
        String SUCCESS = "00000000";
        String DATA_CF = "00000009";
        String DATA_PO_ERROR = "00000010";
        String SUCCESS_MSG = "成功";
        String SYS_ERROR = "00000001";
        String SYS_ERROR_MSG = "系统异常";
        String SETTEMT_ERROR = "02030001";
        String SETTEMT_SUCCESS = "02030000";
        String NOT_LOGIN = "00000002";//未登录
        String NOT_LOGIN_MSG = "未登录";
        String NOT_TOKEN = "00000003";//TOKEN失效
        String NOT_TOKEN_MSG = "TOKEN失效";
        String NOT_REFERER = "00000004";//TOKEN失效
        String NOT_REFERER_MSG = "非法访问";
        String NOT_REFERER_MEUN = "00000005";//没有权限访问
        String NOT_REFERER_MSG_MEUN = "无权限访问";
    }
    
    
    interface BASE_STATUS {
        String ENABLE = "1";
        String DISABLE = "0";
    }
    
    interface WX_TEMP_FLEAD {
    	String FIRST = "first";
    	String REMARK = "remark";
        String KEYWORD1 = "keyword1";
        String KEYWORD2 = "keyword2";
        String KEYWORD3 = "keyword3";
        String KEYWORD4 = "keyword4";
        String KEYWORD5 = "keyword5";
        String KEYWORD6 = "keyword6";
        String URL = "url";
    }
    
    interface WX_TEMP_MSG {
    	String JH_TX = "JH_TX"; //排队叫号提醒
    	String ZF_TX = "ZF_TX"; //服务支付提醒
        String ZF_SUCCESS_TZ = "ZF_SUCCESS_TZ"; //支付成功通知
        String DAI_ZF_TX = "DAI_ZF_TX"; //待支付提醒
        String PD_SUCCESS_TX = "PD_SUCCESS_TX"; //排队成功通知
        String CANCLE_ORDER = "CANCLE_ORDER"; // 取消订单
    }
}