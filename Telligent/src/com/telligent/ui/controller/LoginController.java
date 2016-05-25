package com.telligent.ui.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.telligent.common.handlers.MessageHandler;
import com.telligent.common.user.TelligentUser;
import com.telligent.model.daos.ILoginDAO;
import com.telligent.model.dtos.ChangePasswordDTO;
import com.telligent.model.dtos.User;
import com.telligent.util.TelligentUtility;

@Controller
public class LoginController {

	@Autowired
	MessageHandler messageHandler;
	
	@Autowired
	TelligentUtility telligentUtility;
	
	private ILoginDAO loginDAO;
	
	public final Logger logger = Logger.getLogger(LoginController.class);
	
	@Autowired
	public LoginController(ILoginDAO loginDAO){
		this.loginDAO = loginDAO;
	}
	
	/**
	 * Method to show Login page
	 * 
	 * @param req
	 * @param res
	 * @param mav
	 * @return
	 */
	
	@RequestMapping(value="/login.htm", method = RequestMethod.GET)
	public ModelAndView showLoginForm(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		logger.info("In login form");
		String ipAddress = req.getRemoteAddr();
		/*if(req.getParameter("login_error")!=null && req.getParameter("login_error").toString().equalsIgnoreCase("1")){
			mav.addObject("message", messageHandler.getMessage("login.error"));
		}*/
		mav.setViewName("loginForm");
		return mav;
	}
	@RequestMapping(value="/login.htm", method = RequestMethod.POST)
	public ModelAndView showLoginFormPost(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		logger.info("In login form");
		mav.setViewName("loginForm");
		return mav;
	}
	
	/**
	 * 
	 * Method to show dashboard based on roles
	 * 
	 * @param req
	 * @param res
	 * @param mav
	 * @return mav
	 */
	
	@RequestMapping(value="/dashboard.htm", method = RequestMethod.GET)
	public ModelAndView showDashBoard(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		logger.info("In showDashboard");
		TelligentUser user = telligentUtility.getTelligentUser();
		loginDAO.resetBadLoginCount(user.getUserName());
		if(user.isChangePassword())
			mav.setViewName("redirect:changePassword.htm");
		/*else if(user.getAuthorities().contains(new GrantedAuthorityImpl("admin")))
			mav.setViewName("admindashboard");
		else if(user.getAuthorities().contains(new GrantedAuthorityImpl("sysAdmin")))
			mav.setViewName("userdashboard");*/
		else
			mav.setViewName("userdashboard");
		return mav;
	}
	
	/**
	 * method to show change password page
	 * 
	 * @param req
	 * @param res
	 * @param mav
	 * @return
	 */
	
	@RequestMapping(method=RequestMethod.GET, value="/changePassword.htm")
	public ModelAndView changePasswordGet(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("changePassword");
		logger.info("in changePassword");
		try{
			mav.addObject("changePassword",new ChangePasswordDTO());
		}catch(Exception e){
			logger.error("Exception in changePassword "+e.getMessage());
		}
		return mav;
	}
	
	/**
	 * Method to change password
	 * 
	 * @param req
	 * @param res
	 * @param mav
	 * @param changePasswordDTO
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, value="/changePassword.htm")
	public ModelAndView changePassword(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,ChangePasswordDTO changePasswordDTO){
		mav.setViewName("changePassword");
		logger.info("in changePassword");
		TelligentUser telligentUser = telligentUtility.getTelligentUser();
		try{
			if(loginDAO.changePassword(telligentUser, changePasswordDTO.getNewPassword())){
				// if message changed here check in changepassword.jsp file also
				mav.addObject("message", messageHandler.getMessage("label.passswordChangeSuccess"));
			}
			else
				mav.addObject("message", messageHandler.getMessage("label.passswordChangeError"));
			mav.addObject("changePassword",changePasswordDTO);
		}catch(Exception e){
			logger.error("Exception in changePassword "+e.getMessage());
		}
		return mav;
	}
	@RequestMapping(value="/test.htm", method = RequestMethod.GET)
	public @ResponseBody JSONArray test(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		logger.info("In showDashboard");
		User user = new User();
		ArrayList<User> list = new ArrayList<User>();
		int[] retArr = getStartAndEndNo(Integer.parseInt(req.getParameter("page")), Integer.parseInt(req.getParameter("rows")));
		for(int i=retArr[0];i<=retArr[1];i++){
			user = new User();
			user.setEmployeeId(i+"");
			user.setPassword("test"+i);
			user.setRole("role"+i);
			user.setUserName("username"+i);
			list.add(user);
		}
		JSONArray obj = (JSONArray) JSONSerializer.toJSON(list);
		return obj;
	}
	public int[] getStartAndEndNo(int page,int noOfRecords){
		int[] retArr = new int[2];
		int start = 0,end=0;
		if(page == 1){
			start =1;
			end = noOfRecords;
		}else{
			start = (page*noOfRecords)+1;
			end = (page*noOfRecords)+10;
		}
		retArr[0] = start;
		retArr[1] = end;
		return retArr;
	}
	
	/**
	 * Method to show Login page
	 * 
	 * @param req
	 * @param res
	 * @param mav
	 * @return
	 */
	
	@RequestMapping(value="/forgotPassword.htm", method = RequestMethod.GET)
	public ModelAndView forgotPassword(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		logger.info("In forgotPassword form");
		if(req.getParameter("login_error")!=null && req.getParameter("login_error").toString().equalsIgnoreCase("1")){
			mav.addObject("message", messageHandler.getMessage("login.error"));
		}
		mav.addObject("user", new User());
		mav.setViewName("forgotPassword");
		return mav;
	}
	
	@RequestMapping(value="/forgotPassword.htm", method = RequestMethod.POST)
	public ModelAndView forgotPasswordPost(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,User user){
		logger.info("In forgotPassword post form");
		if(loginDAO.forgotPassword(user.getUserName()))
			mav.addObject("message", messageHandler.getMessage("label.forgotPasswordSuccess"));
		else
			mav.addObject("message", messageHandler.getMessage("label.forgotPasswordError"));
		mav.addObject("user", new User());
		mav.setViewName("forgotPassword");
		return mav;
	}
}
