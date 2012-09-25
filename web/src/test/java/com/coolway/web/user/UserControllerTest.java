package com.coolway.web.user;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.coolway.biz.user.UserLoginService;
import com.coolway.common.model.ResultModel;
import com.coolway.web.controller.UserController;

/**
 * 用户接口测试.
 * 
 * @author chengdong
 */
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

	private UserController controller;

	@Mock
	private UserLoginService loginService;

	String name = "chengdong";
	String password = "chengdong";

	@Before
	public void setUp() {

		controller = new UserController();
		controller.setLoginService(loginService);

	}

	@Test
	public void loginSuccess() {

		when(loginService.login(name, password)).thenReturn(true);
		ResultModel result = controller.login(name, password);
		Assert.assertTrue(result.isSuccess());
		verify(loginService).login(name, password);
	}

	@Test
	public void loginException() {
		when(loginService.login(name, password)).thenThrow(
				new RuntimeException("visit db error"));
		ResultModel result = controller.login(name, password);
		Assert.assertEquals(result.getResultCode(), ResultModel.SERVER_ERROR);
		verify(loginService).login(name, password);
	}

	@Test
	public void loginFailed() {
		when(loginService.login(name, password)).thenReturn(false);
		ResultModel result = controller.login(name, password);
		Assert.assertFalse(result.isSuccess());
		verify(loginService).login(name, password);
	}

}
