//package com.quiet.live.hall.config;
//
//import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import io.swagger.annotations.ApiOperation;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
///**
// * SwaggerConfig
// */
//@Configuration
//@EnableSwagger2
//@ConditionalOnExpression("${swagger.enable:true}")
//public class Swagger2Configuration implements WebMvcConfigurer {
//
//    /**
//     * 
//     * @return
//     */
//    @Bean
//    public Docket accessHello() {
//        return new Docket(DocumentationType.SWAGGER_2).groupName("客户端静享生活馆")// 定义组
//                .select() // 选择那些路径和api会生成document
//                .apis(RequestHandlerSelectors.basePackage("com.quiet.live.hall.custom.rest")) // 拦截的包路径
//                .paths(PathSelectors.any())
//                .build() // 创建
//                .apiInfo(apiInfo()); // 配置说明
//    }
//    
//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2).groupName("BI端接口")
//                .apiInfo(apiBi())
//                .select().paths(PathSelectors.any())
//                .apis(RequestHandlerSelectors.basePackage("com.quiet.live.hall.rest"))                         //这里采用包含注解的方式来确定要显示的接口
//                .build();
//    }
//    
//    @Bean
//    public Docket createRestApiJS() {
//        return new Docket(DocumentationType.SWAGGER_2).groupName("技师端端接口")
//                .apiInfo(apiJs())
//                .select().paths(PathSelectors.any())
//                .apis(RequestHandlerSelectors.basePackage("com.quiet.live.hall.js.rest"))                         //这里采用包含注解的方式来确定要显示的接口
//                .build();
//    }
//
////    @Bean
////    public Docket createRestApiCustom() {
////        return new Docket(DocumentationType.SWAGGER_2)
////        		.apiInfo(apiInfo())
////                .select().paths(PathSelectors.regex("/custom/。*"))
////                .apis(RequestHandlerSelectors.basePackage("com.quiet.live.hall.rest"))                         //这里采用包含注解的方式来确定要显示的接口
////                .build();
////    }
//    
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("客户端静享生活馆")
//                .description("客户端静享生活馆")
//                .termsOfServiceUrl("http://git.oschina.net/naan1993/guns")
//                .version("2.0")
//                .build();
//    }
//
//    private ApiInfo apiBi() {
//        return new ApiInfoBuilder()
//                .title("BI端")
//                .description("BI端接口")
//                .termsOfServiceUrl("http://git.oschina.net/naan1993/guns")
//                .version("2.0")
//                .build();
//    }
//    
//    private ApiInfo apiJs() {
//        return new ApiInfoBuilder()
//                .title("技师端")
//                .description("技师端端口")
//                .termsOfServiceUrl("http://git.oschina.net/naan1993/guns")
//                .version("2.0")
//                .build();
//    }
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**")
//                .addResourceLocations("classpath:/static/");
//
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//        registry.addResourceHandler("/js");
//        registry.addResourceHandler("/custom");
//        registry.addResourceHandler("/bi");
//    }
//
//}
//
