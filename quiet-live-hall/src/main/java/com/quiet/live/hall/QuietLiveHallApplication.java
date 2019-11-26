package com.quiet.live.hall;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.quiet.live.hall.config.FastJsonHttpMessageConverters;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@EnableScheduling
public class QuietLiveHallApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuietLiveHallApplication.class, args);
	}
	
	 private CorsConfiguration buildConfig() {
	        CorsConfiguration corsConfiguration = new CorsConfiguration();
	        corsConfiguration.addAllowedOrigin("*");
	        corsConfiguration.addAllowedHeader("*");
	        corsConfiguration.addAllowedMethod("*");
	        corsConfiguration.setAllowCredentials(true);//这两句不加不能跨域上传文件，
	        corsConfiguration.setMaxAge(3600l);//加上去就可以了
	        return corsConfiguration;
	    }
	 
	    /**
	     * 跨域过滤器
	     * @return
	     */
	    @Bean
	    public CorsFilter corsFilter() {
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", buildConfig()); // 4
	        return new CorsFilter(source);
	    }

	@Bean
	public HttpMessageConverters loadFastJson(){
		return new FastJsonHttpMessageConverters().fastJsonHttpMessageConverters();
	}
//	
//	/**
//	 * 配置上传文件大小的配置
//	 * @return
//	 */
//	@Bean
//    public MultipartConfigElement multipartConfigElement(){
//        MultipartConfigFactory factory=new MultipartConfigFactory();
//        factory.setMaxFileSize("20MB");
//        factory.setMaxRequestSize("20MB");
//        return factory.createMultipartConfig();
//    }
}
