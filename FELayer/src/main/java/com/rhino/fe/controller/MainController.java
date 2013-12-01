package com.rhino.fe.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rhino.mailParser.data.UserData;
import com.rhino.mailParser.data.UserDataDAO;

@Controller
@RequestMapping("/hello")
public class MainController {

	private SessionFactory sessionFactory;
	
	@Autowired
	public MainController(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String printHello(ModelMap model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userID = auth.getName(); // get logged in username

		model.addAttribute("message", ""+ userID+" report");
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		tx.begin();
		UserDataDAO userDataDAO = new UserDataDAO();
		userDataDAO.setSession(session);
		List<UserData> events = userDataDAO.getByUserID(userID);
		HashMap<UserData,UserData> data = new HashMap<UserData,UserData>();
		float total = 0;
		for(UserData e: events){
			UserData tmp = data.get(e);
			if(tmp == null){
				data.put(e, e);
				total += e.getAmount();
			}else{
				tmp.setDuplicationCounter(tmp.getDuplicationCounter()+1);
			}
			
		}
		model.addAttribute("report", data.keySet());
		model.addAttribute("total", total);
		tx.commit();
		session.close();

		return "hello";
	}

}
