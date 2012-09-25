package com.coolway.web.controller;

import static com.coolway.common.model.ResultModel.PARAMETER_ERROR;
import static com.coolway.common.model.ResultModel.SERVER_ERROR;
import static com.coolway.common.model.ResultModel.UNAUTHORIZED;
import static org.apache.commons.lang.StringUtils.isEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coolway.biz.user.UserInviteService;
import com.coolway.biz.user.UserLoginService;
import com.coolway.common.logger.LoggerUtils;
import com.coolway.common.model.ResultModel;

@Controller
public class UserController {

	@Autowired
	private UserInviteService inviteService;

	@Autowired
	UserLoginService loginService;

	@RequestMapping(value = "/login")
	@ResponseBody
	public ResultModel login(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "password", required = false) String password) {
		ResultModel result = new ResultModel();

		if (isEmpty(name) || isEmpty(password)) {
			result.setResultCode(PARAMETER_ERROR);
			return result;
		}

		try {
			if (!loginService.login(name, password)) {
				result.setResultCode(UNAUTHORIZED);
			}
		} catch (Exception e) {
			LoggerUtils.error("login error", e);
			result.setResultCode(SERVER_ERROR);
		}
		return result;
	}

	@RequestMapping(value = "/account/invite")
	public String handleInvite(
			@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "email", required = false) String email) {
		try {
			int updateCount = inviteService.handleInvite(email, code);
			if (updateCount == 0) {
				return "/applyRegister";
			}
			return "/register";
		} catch (Exception e) {
			LoggerUtils.error("invite user error", e);
			return "/applyRegister";
		}
	}

	public void setLoginService(UserLoginService loginService) {
		this.loginService = loginService;
	}
}
