package com.quiet.live.hall.bi.rest;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author william zhong123
 * @since 2019-08-07
 */

@Api(value="BI用户",tags={"用户操作接口"})
@RestController
@RequestMapping("/jUser")
@CrossOrigin
public class JUserController {

}

