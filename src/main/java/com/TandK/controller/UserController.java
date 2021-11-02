package com.TandK.controller;

import com.TandK.core.annotation.IgnoreToken;
import com.TandK.core.cache.RedisCache;
import com.TandK.core.support.http.HttpResponseSupport;
import com.TandK.model.vo.LoginVO;
import com.TandK.model.vo.UserVO;
import com.TandK.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.TandK.core.support.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author TandK
 * @since 2021/9/3 0:19
 */
@Api(tags = "用户")
@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "登录", response = String.class)
    @IgnoreToken
    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginVO loginVO){
        return userService.login(loginVO);
    }


    @ApiOperation(value = "获取用户信息", response = UserVO.class)
    @GetMapping()
    public ResponseEntity<Object> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("page")
    @ApiImplicitParam(paramType = "query", dataType = "int", name = "pageIndex", value = "页数", required = true)
    public ResponseEntity<Object> getPage(int pageIndex){
        return null;
    }

    @PostMapping()
    public ResponseEntity<Object> addUser(@Valid @RequestBody UserVO userVO){
        return userService.addUser(userVO);
    }

    @ApiOperation(value = "获取我的信息", response = UserVO.class)
    @GetMapping("/mine")
    public ResponseEntity<Object> getMyInfo(){
        return HttpResponseSupport.success(userService.getMyInfo());
    }

    @IgnoreToken
    @ApiOperation(value = "获取指定用户信息", response = UserVO.class)
    @GetMapping("/{uuid}")
    public ResponseEntity<Object> getUser(@PathVariable("uuid") String uuid){
        return HttpResponseSupport.success(userService.getUser(uuid));
    }
}
