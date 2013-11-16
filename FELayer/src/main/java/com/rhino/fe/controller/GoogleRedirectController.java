package com.rhino.fe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.rhino.fe.google.GoogleAuthHelper;

@Controller
@RequestMapping("/userGoogleReg")
public class GoogleRedirectController {

	private GoogleAuthHelper googleAuthHelper = new GoogleAuthHelper();
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView processSubmit(ModelMap model){
		String url = googleAuthHelper.buildLoginUrl();
		return new ModelAndView(new RedirectView(url, true));
	}

}
