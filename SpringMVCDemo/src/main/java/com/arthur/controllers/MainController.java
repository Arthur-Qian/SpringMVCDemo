package com.arthur.controllers;

import com.arthur.dao.UserService;
import com.arthur.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by Administrator on 12/8/2015.
 */
@Controller
@Scope("prototype")
public class MainController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "login";
    }

    @RequestMapping(value="ui/users", method= RequestMethod.GET)
    public String getUser(ModelMap modelMap){
        // 找到user表里的所有记录
        List<UserEntity> userList = userService.getAllUser();

        // 将所有记录传递给返回的jsp页面
        modelMap.addAttribute("userList", userList);

        // 返回 pages 目录下的 userManage.jsp 页面
        return "pages/userManage";
    }

    // 添加用户 页面
    @RequestMapping(value = "ui/addUser", method = RequestMethod.GET)
    public String addUser() {
        return "pages/addUser";
    }

    // 添加用户 处理
    @RequestMapping(value = "ui/addUserPost", method = RequestMethod.POST)
    public String addUserPost(@ModelAttribute("user") UserEntity userEntity) {

        // 数据库中添加一个用户
        //userRepository.save(userEntity);

        // 数据库中添加一个用户，并立即刷新
        /*userRepository.saveAndFlush(userEntity);*/
        userService.saveUser(userEntity);

        // 返回重定向 到 /users 请求
        return "redirect:ui/users";
    }

    // 查看用户详情
// @PathVariable可以收集url中的变量，需匹配的变量用{}括起来
// 例如：访问 localhost:8080/showUser/1 ，将匹配 userId = 1
    @RequestMapping(value = "ui/showUser/{userId}", method = RequestMethod.GET)
    public String showUser(@PathVariable("userId") Integer userId, ModelMap modelMap) {

        // 找到userId所表示的用户
        //UserEntity userEntity = userRepository.findOne(userId);
        UserEntity userEntity = userService.getUserByID(userId);

        // 传递给请求页面
        modelMap.addAttribute("user", userEntity);
        return "pages/userDetail";
    }

    // 更新用户信息 页面
    @RequestMapping(value = "ui/updateUser/{userId}", method = RequestMethod.GET)
    public String updateUser(@PathVariable("userId") Integer userId, ModelMap modelMap) {

        // 找到userId所表示的用户
       /* UserEntity userEntity = userRepository.findOne(userId);*/
        UserEntity userEntity = userService.getUserByID(userId);

        // 传递给请求页面
        modelMap.addAttribute("user", userEntity);
        return "pages/updateUser";
    }

    // 更新用户信息 操作
    @RequestMapping(value = "ui/updateUserPost", method = RequestMethod.POST)
    public String updateUserPost(@ModelAttribute("user") UserEntity userEntity) {

        // 更新用户信息
       /* userRepository.updateUser(userEntity.getFirstName(), userEntity.getLastName(),
                userEntity.getPassword(), userEntity.getId());*/
        userService.updateUser(userEntity);
        return "redirect:ui/users";
    }

    // 删除用户
    @RequestMapping(value = "ui/deleteUser/{userId}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("userId") Integer userId) {

        // 删除id为userId的用户
        //userRepository.delete(userId);
        // 立即刷新
        //userRepository.flush();

        userService.deleteUser(userId);
        return "redirect:ui/users";
    }
}
