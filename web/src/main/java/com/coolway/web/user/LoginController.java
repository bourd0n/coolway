package com.coolway.web.user;

import static com.coolway.common.model.ResultModel.PARAMETER_ERROR;
import static com.coolway.common.model.ResultModel.SERVER_ERROR;
import static com.coolway.common.model.ResultModel.UNAUTHORIZED;
import static org.apache.commons.lang.StringUtils.isEmpty;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coolway.biz.user.UserLoginService;
import com.coolway.common.logger.LoggerUtils;
import com.coolway.common.model.ResultModel;
import com.guang.prada.common.constants.CommonConstants;
import com.guang.prada.entity.User;

@Controller
public class LoginController {

	@Autowired
	private UserLoginService loginService;

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
	
	
	@RequestMapping("/logout")
    public String logout(HttpServletRequest request,
            HttpServletResponse response, Model model) {
        HttpSession session = request.getSession();
        String url = request.getHeader("Referer");
        logger.info("url=" + url);
        try {
            User user = (User) session
                    .getAttribute(SessionConstants.SESSION_USER);
            user = userService.getUserById(user.getId());
            if (user != null) {
                cookieHandlingService.logout(user, request, response);
            }
            session.removeAttribute(SessionConstants.SESSION_USER);
            session.invalidate();
            // model.addAttribute("code",BaseObject.RESULT_SUCCESS);
        } catch (Exception e) {
            // model.addAttribute("code",BaseObject.RESULT_FAILURE);
            logger.error("用户登出异常：" + e.getMessage(), e);
        }
        /*
         * if (url.indexOf("doLogin") > 0 || url.indexOf("doVerifyEmail") > 0 ||
         * url.indexOf("regActive") > 0 || url.indexOf("doEmailReg") > 0 ||
         * url.indexOf("doUpdateUser") > 0 || url.indexOf("account") > 0) { url
         * = "http://" + request.getServerName() + request.getContextPath() +
         * "/"; }
         */

        // 用户退出后统一跳转到登录页面
        return "redirect:/login";
    }

	

}
