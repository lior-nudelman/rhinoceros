package com.rhino.fe.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.rhino.fe.google.GoogleAuthHelper;

@Controller
@RequestMapping("/googleReg")
public class GoogleController {

	private GoogleAuthHelper googleAuthHelper = new GoogleAuthHelper();
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView  getBarChartViewGet(ModelMap model,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return work(model,request,response);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView  getBarChartViewPost(ModelMap model,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return work(model,request,response);
	}
	
	private ModelAndView  work(ModelMap model,HttpServletRequest request,HttpServletResponse response){
		String authCode = request.getParameter("code");
		String token = null;
		String data = null;
		String email = null;
		try {
			String[] ret = googleAuthHelper.getUserToken(authCode);
			token = ret[0];
			data = ret[1];
			String[] pairs = data.split(",\n");
			for(String str:pairs){
				String ss[] = str.split(":");
				ss[0] =ss[0].trim();
				if(ss[0].equals("\"email\"")){
					email = ss[1].trim().substring(1,ss[1].lastIndexOf("\"")-1);
				}
			}
			System.out.println(token);
			System.out.println(email);
			model.addAttribute("token", token);
			model.addAttribute("email", email);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RedirectView  redirect= new RedirectView("/userRegNoEmail.do", true);
		
		ModelAndView retModel = new ModelAndView(redirect);
		retModel.addAllObjects(model);
		System.out.println(retModel.getModelMap());
		return retModel; 
	}
}
