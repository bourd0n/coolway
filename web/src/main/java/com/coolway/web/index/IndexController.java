package com.coolway.web.index;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping(value="/")
	public String index(HttpServletRequest request, Model model) {
		model.addAttribute("hello", "coolway");
		return "/website/index";
	}
}
