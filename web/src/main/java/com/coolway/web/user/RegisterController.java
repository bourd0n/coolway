package com.coolway.web.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coolway.biz.user.UserService;
import com.coolway.common.model.ResultModel;
import com.coolway.common.model.session.SessionConstants;
import com.coolway.common.model.user.UserStatus;
import com.coolway.entity.User;

/**
 * 注册
 */
@Controller
public class RegisterController {

	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/register")
	public String register(){
		
		return "" ;
	}
	
	/**
	 * 检查邮件或用户名是否存在
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/checkEmailExist")
	@ResponseBody
	public Model checkEmailExist(HttpServletRequest request, Model model) {
		int result = 1;
		String email = request.getParameter("email");
		String name = request.getParameter("name");
		User user = (User) request.getSession().getAttribute(SessionConstants.SESSION_USER);
		if (null != user && UserStatus.active.name().equalsIgnoreCase(user.getStatus())) {
			// 0:都不存在 1：email存在 2：昵称存在
			result = ResultModel.USER_NOT_EXIST;
		} else {
			// 0:都不存在 1：email存在 2：昵称存在
			//result = userService.isRegEmailAndNameExist(email, nick);
			result = ResultModel.EMAIL_HAS_EXIST;
		}
		model.addAttribute("code", result);
		return model;
	}

}
