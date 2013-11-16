package com.rhino.fe.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.rhino.fe.model.GUIUserModel;
import com.rhino.fe.validetor.UserNoEmailValidator;
import com.rhino.userAttributesServic.UserManagerInterface;
import com.rhino.userAttributesServic.data.UserAttributeType;
import com.rhino.userAttributesServic.data.UserDataImpl;
import com.rhino.userAttributesServic.data.UserDataInterface;

@Controller
@RequestMapping("/userRegNoEmail")
public class UserNoEmailController {

	private static Logger logger = Logger.getLogger(UserNoEmailController.class);
	UserNoEmailValidator userValidator;
	UserManagerInterface userManagerInterface;

	@Autowired
	public UserNoEmailController(UserNoEmailValidator userValidator,
			UserManagerInterface userManagerInterface) {
		this.userValidator = userValidator;
		this.userManagerInterface = userManagerInterface;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute("userRegNoEmail") GUIUserModel user,
			BindingResult result, SessionStatus status) {
		if (user == null) {
			return "userFormNoEmail";
		}
		userValidator.validate(user, result);
		GUIUserModel u = (GUIUserModel) user;
		if (u.isFirstTime()) {
			return "userFormNoEmail";
		}
		if (result.hasErrors()) {
			// if validator failed
			return "userFormNoEmail";
		}
		UserDataInterface tmpUser = userManagerInterface.getUser(u.getUserName());
		if(tmpUser != null){
			((Errors)result).rejectValue("userName", "userName.exist");
			return "userFormNoEmail";
		}
		status.setComplete();
		// form success
		UserDataImpl dbUser = new UserDataImpl();
		dbUser.setID(u.getUserName());
		Map<UserAttributeType, String> map = dbUser.getUserAttributes();
		map.put(UserAttributeType.MAIL, u.getAddress());
		map.put(UserAttributeType.MAIL_TOKEN, u.getSecretValue());
		map.put(UserAttributeType.MAIL_USERNAME, u.getAddress());
		String path = System.getProperty("rhino.home");
		logger.info("The path for user "+u.getUserName()+" is "+path);
		map.put(UserAttributeType.FOLDER_PATH, path);
		map.put(UserAttributeType.MAIL_HOST, "imap.gmail.com");
		map.put(UserAttributeType.DATE, ""
				+ (System.currentTimeMillis() - (24 * 60 * 60 * 1000 * 365)));
		map.put(UserAttributeType.USER_PASSWORD, u.getPassword());
		userManagerInterface.addUser(dbUser);

		return "userSuccess";

	}

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(HttpServletRequest request,ModelMap model) {

		String email = request.getParameter("email");
		String token = request.getParameter("token");
		System.out.println("EMAIL>"+email);
		System.out.println("EMAIL>"+token);
		GUIUserModel user = new GUIUserModel();

		// initilize a hidden value
		user.setSecretValue(token);
		user.setAddress(email);
		
		// command object
		model.addAttribute("userRegNoEmail", user);
		
		// return form view
		return "userFormNoEmail";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));

	}
}
