package org.example.controller;

import org.example.pojo.Result;
import org.example.pojo.User;
import org.example.service.UserService;
import org.example.utils.JwtUtil;
import org.example.utils.Md5Util;
import org.example.utils.ThreadLocalUtil;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //注册
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$")String password){


            //查询用户
            User u = userService.findByUserName(username);
            if (u == null){
                //没用占用

                //注册
                userService.register(username,password);
                return Result.success();
            }else {
                //占用
                return Result.error("用户名已被占用");
            }
        }

        //登录
        @PostMapping("/login")
    public Result Login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$")String password){
        //根据用户名查询用户
    User loginuser = userService.findByUserName(username);
            //判断用户是否存在
            if (loginuser == null){
                return  Result.error("用户名错误");
            }
            //判断密码是否正确
            if(Md5Util.getMD5String(password).equals(loginuser.getPassword())){
                //登录成功
                Map<String,Object> claims = new HashMap<>();
                claims.put("id",loginuser.getId());
                claims.put("username",loginuser.getUsername());
                String token = JwtUtil.genToken(claims);
                //将token存储到redis中
                ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
                operations.set(token,token,1, TimeUnit.HOURS);

                return Result.success(token);
            }

                return Result.error("密码错误");
        }

        //获取用户信息
        @GetMapping("/userInfo")
        public Result<User> userInfo(/*@RequestHeader(name = "Authorization") String token*/){
        //根据用户名查询
//        Map<String,Object> map = JwtUtil.parseToken(token);
//        String username = (String) map.get("username");
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUserName(username);
        return Result.success(user);
        }

        //更新用户信息
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }

    //更新头像
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    //更新密码
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params,@RequestHeader("Authorization") String token){
        //校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");
        if(!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)){
            return Result.error("缺少必要参数");
        }
        //校验原密码是否正确
        //调用service获取密码
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginuser = userService.findByUserName(username);
        if (!loginuser.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码错误");
        }

        if(!rePwd.equals(newPwd)){
            return Result.error("两次填写的新密码不一致");
        }

        //完成更新
        userService.updatePwd(newPwd);
        //删除对应的token
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success();
    }


}
