package com.wonders.controller;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wonders.service.ShiroService;

/**
 * 
 * @author CX
 * @Date 2019/7/17	
 */
@Controller
@RequestMapping("/shiro")
public class ShiroController {
	
	@Autowired
    private ShiroService shiroService;

    @RequestMapping("/testShiroAnnotation")
    public String testShiroAnnotation(HttpSession session){
        session.setAttribute("key","value123");
        shiroService.testMethod();
        return "redirect:/list.jsp";
    }
	
	@RequestMapping("/login")
	public String login(@RequestParam("username") String username,
            @RequestParam("password") String password) {
		Subject currentUser = SecurityUtils.getSubject();	
		if(!currentUser.isAuthenticated()) {
			// 把用户名 和密码封装为UsernamePasswordToken对象
            UsernamePasswordToken token = new UsernamePasswordToken(username,password);
            // remember me 
            token.setRememberMe(true);
            
            try{
                //执行登陆
                currentUser.login(token);
            }catch (AuthenticationException ae){
                System.out.println("登陆失败:"+ae.getMessage());
            }
		}
		if (username.equals("admin")){
	            return "redirect:../admin.jsp";
	   }
		return "redirect:../list.jsp";
		
	}
	
}
