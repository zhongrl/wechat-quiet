package com.quiet.live.hall.vo;

public class SwaggerResource  extends springfox.documentation.swagger.web.SwaggerResource{
	 
		/**
		 * 域名端口
		 */
		private String host;
		
		/**
		 * 远程资源路径
		 */
		private String path;
		
		/**
		 * 本地资源
		 */
		private String fileName;
	 
		public String getHost() {
			return host;
		}
	 
		public void setHost(String host) {
			this.host = host;
		}
	 
		public String getPath() {
			return path;
		}
	 
		public void setPath(String path) {
			this.path = path;
		}
	 
		public String getFileName() {
			return fileName;
		}
	 
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		
}
