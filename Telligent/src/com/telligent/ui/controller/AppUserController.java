package com.telligent.ui.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.telligent.common.handlers.MessageHandler;
import com.telligent.common.user.TelligentUser;
import com.telligent.model.daos.ILoginDAO;
import com.telligent.model.daos.impl.AppUserDAO;
import com.telligent.model.daos.impl.ReferenceTablesDAO;
import com.telligent.model.dtos.AppUserDTO;
import com.telligent.model.dtos.AppUserListDTO;
import com.telligent.model.dtos.MapDTO;
import com.telligent.model.dtos.SecurityGroupDTO;
import com.telligent.model.dtos.TeamDTO;
import com.telligent.util.TelligentUtility;

@Controller
public class AppUserController {

	
	public final Logger logger = Logger.getLogger(AppUserController.class); 

	@Autowired
	MessageHandler messageHandler;
	
	@Autowired
	TelligentUtility telligentUtility;
	
	private AppUserDAO appUserDAO;
	
	private ILoginDAO loginDAO;
	
	@Autowired
	public AppUserController(AppUserDAO appUserDAO,ILoginDAO loginDAO) {
		this.appUserDAO = appUserDAO;
		this.loginDAO = loginDAO;
	}
	public AppUserController() {
	}
	
	@RequestMapping(value="/appUser.htm", method = RequestMethod.POST)
	public ModelAndView showAppUser(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		AppUserDTO dto =  new AppUserDTO();
		ArrayList<AppUserListDTO> appUserList = new ArrayList<AppUserListDTO>();
		for(int i=0;i<10;i++){
			appUserList.add(new AppUserListDTO());
		}
		ReferenceTablesDAO refDAO = new ReferenceTablesDAO();
		ArrayList<MapDTO> performanceAdminRoleList = refDAO.getDetails("Performance_Admin_Role");
		ArrayList<MapDTO> bonusAdminRoleList = refDAO.getDetails("Bonus_Admin_Role");
		ArrayList<MapDTO> successionRoleList = refDAO.getDetails("Succession_Role");
		dto.setAppUserList(appUserList);
		ArrayList<TeamDTO>  teamList =appUserDAO.getTeamList();
		ArrayList<SecurityGroupDTO>  securityGroupList =appUserDAO.getSecurityGroupList();
		mav.addObject("teamList",teamList);
		mav.addObject("securityGroupList",securityGroupList);
		mav.addObject("performanceAdminRoleList",performanceAdminRoleList);
		mav.addObject("bonusAdminRoleList",bonusAdminRoleList);
		mav.addObject("successionRoleList",successionRoleList);
		mav.addObject("appUser", dto);
		mav.setViewName("appUser");
		return mav;
	}
	@RequestMapping(value="/saveAppUserDetails.htm", method = RequestMethod.POST)
	public @ResponseBody String saveAppUserDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute("appUser") AppUserDTO appUserDTO){
		logger.info("in saveAppUserDetails");
		TelligentUser user = telligentUtility.getTelligentUser();
		return appUserDAO.saveAppUserDetails(appUserDTO,user);
	}
	@RequestMapping(value="/getUserDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getUserDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("empId") String empId){
		AppUserDTO dto = appUserDAO.getUserDetails(empId,req.getParameter("userId")+"");
		return (JSONObject) JSONSerializer.toJSON(dto);
	}
	@RequestMapping(value="/resetPassword.htm", method = RequestMethod.POST)
	public @ResponseBody String resetPassword(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("userId") String userId){
		boolean flag = loginDAO.forgotPassword(userId);
		if(flag){
			return "success";
		}else{
			return "error";
		}
	}
	@RequestMapping(value="/getAppUserDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getAppUserDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(appUserDAO.getAppUserDetails());
	}
	@RequestMapping(value="/searchUserId.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray searchUserId(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		ArrayList<MapDTO> list = new ArrayList<MapDTO>();
		if(req.getParameter("q") !=null && !req.getParameter("q").equalsIgnoreCase(""))
			list.addAll(appUserDAO.searchUserId(req.getParameter("q")));
		JSONArray obj = (JSONArray) JSONSerializer.toJSON(list);
		return obj;
	}
	@RequestMapping(value="/searchTeamEmployeesAppUser.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray searchTeamEmployeesAppUser(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		ArrayList<MapDTO> list = new ArrayList<MapDTO>();
		if(req.getParameter("q") !=null && !req.getParameter("q").equalsIgnoreCase(""))
			list.addAll(appUserDAO.searchTeamEmployeesAppUser(req.getParameter("q")));
		JSONArray obj = (JSONArray) JSONSerializer.toJSON(list);
		return obj;
	}
	
}
