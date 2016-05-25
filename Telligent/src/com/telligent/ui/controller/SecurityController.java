package com.telligent.ui.controller;

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
import com.telligent.model.daos.impl.SecurityGroupDAO;
import com.telligent.model.dtos.MapDTO;
import com.telligent.model.dtos.SecurityGroupDTO;
import com.telligent.util.TelligentUtility;

@Controller
public class SecurityController {

	
	public final Logger logger = Logger.getLogger(SecurityController.class); 

	@Autowired
	MessageHandler messageHandler;
	
	@Autowired
	TelligentUtility telligentUtility;
	
	private SecurityGroupDAO securityGroupDAO;
	
	@Autowired
	public SecurityController(SecurityGroupDAO securityGroupDAO) {
		this.securityGroupDAO = securityGroupDAO;
	}
	public SecurityController() {
	}
	
	@RequestMapping(value="/securityGroup.htm", method = RequestMethod.POST)
	public ModelAndView securityGroup(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("securityGroup");
		SecurityGroupDTO dto = new SecurityGroupDTO();
		dto.setOperation("save");
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		mav.addObject("securityGroup", dto);
		return mav;
	}
	@RequestMapping(value="/saveSecurityGroup.htm", method = RequestMethod.POST)
	public @ResponseBody String saveSecurityGroup(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,SecurityGroupDTO dto){
		logger.info("In saveSecurityGroup");
		TelligentUser user = telligentUtility.getTelligentUser();
		return securityGroupDAO.saveSecurityGroup(dto,user);
	}
	@RequestMapping(value="/getSecurityGroupDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getSecurityGroupDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(securityGroupDAO.getSecurityGroupDetails());
	}
	@RequestMapping(value="/getSecurityGroupDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getSecurityGroupDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(securityGroupDAO.getSecurityGroupDetailsId(id));
	}
}
