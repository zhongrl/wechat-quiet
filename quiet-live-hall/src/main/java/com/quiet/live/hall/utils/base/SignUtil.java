package com.quiet.live.hall.utils.base;

import java.io.UnsupportedEncodingException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Description: [签名工具]
 * </p>
 * 
 * @since 1.0
 */
public class SignUtil {
	//记录日志
	private static Logger log = LoggerFactory.getLogger(SignUtil.class);

	/**
	 * <p>
	 * Discription:[获取签名]
	 * </p>
	 *
	 * @return
	 */
	public static String getSign(Map<String, String> param, String key, String input_charset) {
		// 获取待签名字符串
		String text = createLinkString(param);
		text = text + key;
		String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));
		return mysign;

	}

	/**
	 * <p>
	 * Discription:[获取签名]
	 * </p>
	 *
	 * @return
	 */
	public static String getSign(Map<String, String> param, String key) {
		return getSign(param, key, "UTF-8");
	}
	/**
	 * <p>Discription:[生成签名吧]</p> 
	 *@param datas
	 *@param key
	 *@return
	 */
	public static String getSign(Object[] datas, String key) {
		if (!(ArrayUtils.isEmpty(datas))) {
			Map<String, String> param = new HashMap<String, String>();
			for (Object data : datas) {
				param.put(data.toString(), data.toString());
			}
			return getSign(param, key);
		}
		return "";
	}

	/**
	 * 签名字符串
	 * 
	 * @param text 需要签名的字符串
	 * @param sign 签名结果
	 * @param key 密钥
	 * @param input_charset 编码格式
	 * @return 签名结果
	 */
	public static boolean verify(Map<String, String> param, String sign, String key, String input_charset) {
		String text = createLinkString(param);
		text = text + key;
		String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));
		if (mysign.equals(sign)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * @param content
	 * @param charset
	 * @return
	 * @throws SignatureException
	 * @throws UnsupportedEncodingException
	 */
	private static byte[] getContentBytes(String content, String charset) {
		if (charset == null || "".equals(charset)) {
			return content.getBytes();
		}
		try {
			return content.getBytes(charset);
		}
		catch (UnsupportedEncodingException e) {
			log.error("getContentBytes error", e);
			throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
		}
	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params 需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, String> params) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);

			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			}
			else {
				prestr = prestr + key + "=" + value + "&";
			}
		}
		return prestr;
	}

}
