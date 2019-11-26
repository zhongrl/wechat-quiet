package com.quiet.live.hall.wx.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.quiet.live.hall.config.RedisComponent;
import com.quiet.live.hall.entity.JUser;
import com.quiet.live.hall.service.JUserService;
import com.quiet.live.hall.utils.string.StringUtil;
import com.quiet.live.hall.wx.vo.InputMessage;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;

@Slf4j
@Component
public class WechatUtils {

	public static final String TOKEN = "8ZN3zEee8DgxoMpp";

	@Autowired
	WxMpService wxMpService;
	
	@Autowired
	JUserService jUserService;
	
	@Autowired
 	RedisComponent redisComponent;
	
	/**
	 * @Title: checkWeChat @Description: 校验微信是验证 @param @param
	 * request @param @param response @param @return @return boolean @throws
	 */
	public boolean checkWeChat(HttpServletRequest request, HttpServletResponse response) {
		String signature = request.getParameter("signature");
		if (StringUtils.isEmpty(signature)) {
			return false;
		}
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		boolean boo = checkSignature(TOKEN, signature, timestamp, nonce, echostr, response) == 0 ? true : false;
		return boo;
	}

	/**
	 * @Title: checkSignature @Description: 微信检验接口的校验 @param @param
	 * token @param @param signature @param @param timestamp @param @param
	 * nonce @param @param echostr @param @param response @param @return @return
	 * int @throws
	 */
	public int checkSignature(String token, String signature, String timestamp, String nonce, String echostr,
			HttpServletResponse response) {
		int iResult = 0;
		try {

			log.debug("----------checkSignature-----------token=" + token + ",signature" + signature + ",timestamp="
					+ timestamp + ",nonce=" + nonce + ",echostr" + echostr);
			String finallyString = "";
			String[] s = new String[] { token, timestamp, nonce };
			s = stringSort(s);
			for (int i = 0; i < s.length; i++) {
				finallyString = finallyString + s[i];
			}

			String signatureLocal = new com.quiet.live.hall.wx.utils.SHA1Utils().getDigestOfString(finallyString.getBytes());
			log.debug("--------signatureLocal=" + signatureLocal);
			if (signature.toLowerCase().equals(signatureLocal.toLowerCase())) {
				if (echostr != null && echostr.length() > 0) {
					OutputStream out = response.getOutputStream();
					out.write(echostr.getBytes());
					out.flush();
					out.close();
				}
				log.debug("signature eq signatureLocal");
			} else {
				iResult = 1;
				log.debug("signature not eq signatureLocal");
			}
		} catch (Exception e) {
			iResult = 1;
			log.warn("checkSignature error", e);
		}

		return iResult;
	}

	private static String[] stringSort(String[] s) {
		List<String> list = new ArrayList<String>(s.length);
		for (int i = 0; i < s.length; i++) {
			list.add(s[i]);
		}
		Collections.sort(list);
		return list.toArray(s);
	}

	/** 
	* @Title: acceptMessage 
	* @Description: 微信发送图文消息
	* @param @param request
	* @param @param response
	* @param @throws IOException   
	* @return void   
	* @throws 
	*/
	public  void acceptMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {  
        // 处理接收消息  
        ServletInputStream in = request.getInputStream();  
        // 将POST流转换为XStream对象  
//        XStream xs = SerializeXmlUtil.createXstream();  
//        xs.processAnnotations(InputMessage.class);  
//        xs.processAnnotations(OutputMessage.class);  
//        // 将指定节点下的xml节点数据映射为对象  
//        xs.alias("xml", InputMessage.class);  
        // 将流转换为字符串  
        StringBuilder xmlMsg = new StringBuilder();  
        byte[] b = new byte[4096];  
        for (int n; (n = in.read(b)) != -1;) {  
            xmlMsg.append(new String(b, 0, n, "UTF-8"));  
        } 
        // 明文传输的消息
	     WxMpXmlMessage inMessage =WxMpXmlMessage.fromXml(xmlMsg.toString());
	     log.info(JSON.toJSONString(inMessage));
	      try {
    	     
	    	  if("subscribe".equals(inMessage.getEvent())){
	    		  log.info("获取授权unionId开始");
	    		  String unionId = getUnionid(wxMpService.getAccessToken(),inMessage.getFromUser());
	    		  log.info("获取授权unionId = " + unionId);
	    		  JUser jUs = new JUser();
	    		  jUs.setUnionid(unionId);
	    		  jUs.setUserType("4");
	    		  JUser jUse = jUserService.selectOne(new EntityWrapper<JUser>(jUs));
	    		  JUser jUser = new JUser();
	    		  if(null == jUse){
		    		  jUser.setId(StringUtil.getUUID());
		    		  jUser.setCreateTime(new Date());
		    		  jUser.setOpenId(inMessage.getFromUser());
		    		  jUser.setUnionid(unionId);
		    		  jUser.setUserType("4");
		    		  jUserService.insert(jUser);
	    		  }else{
	    			  jUser.setOpenId(inMessage.getFromUser());
	    			  jUser.setId(jUse.getId());
	    			  jUserService.updateById(jUser);
	    		  }
	    		  log.info("发送消息开始");
	    		  //消息处理
    	       	  String fromUser=inMessage.getFromUser();
    	       	  String touser=inMessage.getToUser();
    	       	  String content="Hi  欢迎来到静享生活馆 😊\r\n静享一刻,奖励下忙碌的自己。\r\n赶紧预约吧～\r\nSavor journey";
	    		  WxMpXmlOutTextMessage text=WxMpXmlOutTextMessage.TEXT().toUser(fromUser).fromUser(touser).content(content).build();
	    	      log.info("发送消息开始1" + JSON.toJSONString(text));
	    	      if(null != text){
	    	    	  log.info("发送成功！");
	    	    	  response.getWriter().write(text.toXml().toString());
	    	      }else{
	    	    	  log.error("发送消息异常"); 
	    	      }
	    	  }else if("unsubscribe".equals(inMessage.getEvent())){
	    		  redisComponent.del(inMessage.getFromUser());
	    	  }
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	/**
	 * 推送默认文本消息
	 * @param inputMsg
	 * @param response
	 */
	@SuppressWarnings("unused")
	private static void pushText(InputMessage inputMsg,HttpServletResponse response){
		inputMsg.setMsgType("text");
		inputMsg.setContent("你好，欢迎关注静享生活体验馆，新用户有优惠券哟！");
		text(inputMsg,response);//关注推送消息
	}
	
	/** 
	* @Title: text 
	* @Description: 发送文本
	* @param @param inputMsg
	* @param @param response   
	* @return void   
	* @throws 
	*/
	private static void text(InputMessage inputMsg,HttpServletResponse response){
		 try {
			 
            Long returnTime = Calendar.getInstance().getTimeInMillis() / 1000;// 返回时间  
    		// 文本消息  
            StringBuffer str = new StringBuffer();  
            str.append("<xml>");  
            str.append("<ToUserName><![CDATA[" + inputMsg.getFromUserName() + "]]></ToUserName>");  
            str.append("<FromUserName><![CDATA[" + inputMsg.getToUserName() + "]]></FromUserName>");  
            str.append("<CreateTime>" + returnTime + "</CreateTime>");  
            str.append("<MsgType><![CDATA[" + inputMsg.getMsgType() + "]]></MsgType>");  
            str.append("<Content><![CDATA[" + inputMsg.getContent() + "]]></Content>");  
            str.append("</xml>");  
			response.getWriter().write(str.toString());
			log.info("text = xml转换：/n" + str);
		} catch (IOException e) {
			log.warn("text 回复消息异常" , e);
		} 
	}
	
	/**
     * 根据openid 获取Unionid
     *
     * @param token
     * @param openid
     * @return
     */
    public String getUnionid(String token, String openid) {
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + token + "&openid=" + openid + "&lang=zh_CN";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        log.info("获取unionid = " + result);
        if (StringUtils.isNotBlank(result)) {
            return JSONObject.parseObject(result).getString("unionid");
        }
        return null;
    }

    /**
     * 根据openid 获取Unionid
     *
     * @param token
     * @param openid
     * @return
     */
    public static String getUnionidLogin(String token, String openid) {
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + token + "&openid=" + openid + "&lang=zh_CN";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        log.info("获取unionid = " + result);
        if (StringUtils.isNotBlank(result)) {
            return JSONObject.parseObject(result).getString("unionid");
        }
        return null;
    }
}
