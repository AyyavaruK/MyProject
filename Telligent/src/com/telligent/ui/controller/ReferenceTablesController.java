package com.telligent.ui.controller;

import java.util.ArrayList;
import java.util.HashMap;

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
import com.telligent.model.daos.impl.EmployeeDAO;
import com.telligent.model.daos.impl.ReferenceTablesDAO;
import com.telligent.model.dtos.JobGradeDTO;
import com.telligent.model.dtos.MapDTO;
import com.telligent.model.dtos.MeritAdminGuidelinesDTO;
import com.telligent.model.dtos.PayGroupDTO;
import com.telligent.model.dtos.PrimaryJobDTO;
import com.telligent.model.dtos.TeamDTO;
import com.telligent.util.TelligentUtility;

@Controller
public class ReferenceTablesController {


	public final Logger logger = Logger.getLogger(ReferenceTablesController.class); 

	@Autowired
	MessageHandler messageHandler;

	@Autowired
	TelligentUtility telligentUtility;

	private ReferenceTablesDAO referenceTablesDAO;

	@Autowired
	public ReferenceTablesController(ReferenceTablesDAO referenceTablesDAO) {
		this.referenceTablesDAO = referenceTablesDAO;
	}
	public ReferenceTablesController() {
	}

	@RequestMapping(value="/referenceTables.htm", method = RequestMethod.GET)
	public ModelAndView referenceTables(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("baseRateFreq");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition","0");
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/referenceTables.htm", method = RequestMethod.POST)
	public ModelAndView referenceTablesPost(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return referenceTables(req, res, mav);
	}
	@RequestMapping(value="/saveBaseRateFreq.htm", method = RequestMethod.POST)
	public @ResponseBody String saveEmployeeCompDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto1") MapDTO mapDTO){
		logger.info("In saveBaseRateFreq");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveBaseRateFreq(mapDTO,user);
	}
	@RequestMapping(value="/getBaseRateFrequencyDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getBaseRateFrequencyDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("Base_Rate_Frequency"));
	}
	@RequestMapping(value="/getBaseRateFrequencyDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getBaseRateFrequencyDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("Base_Rate_Frequency",id));
	}

	/* bonus plan*/
	@RequestMapping(value="/bonusPlan.htm", method = RequestMethod.POST)
	public ModelAndView bonusPlan(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("bonusPlan");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveBonusPlan.htm", method = RequestMethod.POST)
	public @ResponseBody String saveBonusPlanDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveBaseRateFreq");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"Bonus_Plan","Bonus Plan");
	}

	@RequestMapping(value="/getBonusPlanDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getBonusPlanDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("Bonus_Plan"));
	}
	@RequestMapping(value="/getBonusPlanDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getBonusPlanDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("Bonus_Plan",id));
	}

	/* bonus plan end*/


	/* classification*/
	@RequestMapping(value="/classification.htm", method = RequestMethod.POST)
	public ModelAndView classification(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("classification");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveClassification.htm", method = RequestMethod.POST)
	public @ResponseBody String saveClassification(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveClassification");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"Classification","classification");
	}

	@RequestMapping(value="/getClassificationDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getClassificationDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("Classification"));
	}
	@RequestMapping(value="/getClassificationDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getClassificationDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("Classification",id));
	}

	/* classification end*/


	/* CompensationAction*/
	@RequestMapping(value="/compensationAction.htm", method = RequestMethod.POST)
	public ModelAndView compensationAction(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("compensationAction");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveCompensationAction.htm", method = RequestMethod.POST)
	public @ResponseBody String saveCompensationAction(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveCompensationAction");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"Compensation_Action","Compensation Action");
	}

	@RequestMapping(value="/getCompensationActionDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getCompensationActionDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("Compensation_Action"));
	}
	@RequestMapping(value="/getCompensationActionDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getgetCompensationActionDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("Compensation_Action",id));
	}

	/* CompensationAction end*/

	/* CompensationActionReason*/
	@RequestMapping(value="/compensationActionReason.htm", method = RequestMethod.POST)
	public ModelAndView compensationActionReason(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("compensationActionReason");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveCompensationActionReason.htm", method = RequestMethod.POST)
	public @ResponseBody String saveCompensationActionReason(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveCompensationActionReason");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"Compensation_Action_Reason","Compensation Action Reason");
	}

	@RequestMapping(value="/getCompensationActionReasonDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getCompensationActionReasonDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("Compensation_Action_Reason"));
	}
	@RequestMapping(value="/getCompensationActionReasonDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getgetCompensationActionReasonDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("Compensation_Action_Reason",id));
	}

	/* CompensationActionReason end*/

	/* DefaultEarningCode*/
	@RequestMapping(value="/defaultEarningCode.htm", method = RequestMethod.POST)
	public ModelAndView defaultEarningCode(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("defaultEarningCode");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveDefaultEarningCode.htm", method = RequestMethod.POST)
	public @ResponseBody String saveDefaultEarningCode(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveDefaultEarningCode");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"Default_Earning_Code","Default Earning Code");
	}

	@RequestMapping(value="/getDefaultEarningCodeDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getDefaultEarningCodeDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("Default_Earning_Code"));
	}
	@RequestMapping(value="/getDefaultEarningCodeDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getgetDefaultEarningCodeDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("Default_Earning_Code",id));
	}

	/* DefaultEarningCode end*/

	/* DefaultHoursFrequency*/
	@RequestMapping(value="/defaultHoursFrequency.htm", method = RequestMethod.POST)
	public ModelAndView defaultHoursFrequency(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("defaultHoursFrequency");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveDefaultHoursFrequency.htm", method = RequestMethod.POST)
	public @ResponseBody String saveDefaultHoursFrequency(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveDefaultHoursFrequency");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"Default_Hours_Frequency","Default Hours Freaquency");
	}

	@RequestMapping(value="/getDefaultHoursFrequencyDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getDefaultHoursFrequencyDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("Default_Hours_Frequency"));
	}
	@RequestMapping(value="/getDefaultHoursFrequencyDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getgetDefaultHoursFrequencyDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("Default_Hours_Frequency",id));
	}

	/* DefaultHoursFrequency end*/

	/* employeeCategory*/
	@RequestMapping(value="/employeeCategory.htm", method = RequestMethod.POST)
	public ModelAndView employeeCategory(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("employeeCategory");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveEmployeeCategory.htm", method = RequestMethod.POST)
	public @ResponseBody String saveEmployeeCategoryDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveEmployeeCategoryDetails");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"Employement_Category","Employement Category");
	}

	@RequestMapping(value="/getEmployeeCategoryDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getEmployeeCategoryDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("Employement_Category"));
	}
	@RequestMapping(value="/getEmployeeCategoryDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getEmployeeCategoryDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("Employement_Category",id));
	}

	/* employeeCategory end*/

	/* ethinicity*/
	@RequestMapping(value="/ethinicity.htm", method = RequestMethod.POST)
	public ModelAndView ethinicity(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("ethinicity");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveEthinicity.htm", method = RequestMethod.POST)
	public @ResponseBody String saveEthinicityDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveBaseRateFreq");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"Ethinicity","Ethinicity");
	}

	@RequestMapping(value="/getEthinicityDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getEthinicityDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("Ethinicity"));
	}
	@RequestMapping(value="/getEthinicityDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getEthinicityDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("Ethinicity",id));
	}

	/* ethinicity end*/

	/* flsaCategory*/
	@RequestMapping(value="/flsaCategory.htm", method = RequestMethod.POST)
	public ModelAndView flsaCategory(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("flsaCategory");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveFlsaCategory.htm", method = RequestMethod.POST)
	public @ResponseBody String saveFlsaCategoryDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveFlsaCategory");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"FLSA_Category","FLSA Category");
	}

	@RequestMapping(value="/getFlsaCategoryDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getFlsaCategoryDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("FLSA_Category"));
	}
	@RequestMapping(value="/getFlsaCategoryDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getFlsaCategoryDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("FLSA_Category",id));
	}

	/* flsaCategory end*/

	/* fullTimeEquivalency*/
	@RequestMapping(value="/fullTimeEquivalency.htm", method = RequestMethod.POST)
	public ModelAndView fullTimeEquivalency(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("fullTimeEquivalency");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveFullTimeEquivalency.htm", method = RequestMethod.POST)
	public @ResponseBody String saveFullTimeEquivalencyDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveFullTimeEquivalency");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"Full_Time_Equivalency","Full Time Equivalency");
	}

	@RequestMapping(value="/getFullTimeEquivalencyDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getFullTimeEquivalencyDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("Full_Time_Equivalency"));
	}
	@RequestMapping(value="/getFullTimeEquivalencyDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getFullTimeEquivalencyDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("Full_Time_Equivalency",id));
	}

	/* fullTimeEquivalency end*/

	/* grade*/
	@RequestMapping(value="/grade.htm", method = RequestMethod.POST)
	public ModelAndView grade(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("grade");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveGrade.htm", method = RequestMethod.POST)
	public @ResponseBody String saveGradeDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveGrade");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"Grade","Grade");
	}

	@RequestMapping(value="/getGradeDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getGradeDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("Grade"));
	}
	@RequestMapping(value="/getGradeDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getGradeDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("Grade",id));
	}

	/* grade end*/

	/* jobGroup*/
	@RequestMapping(value="/jobGroup.htm", method = RequestMethod.POST)
	public ModelAndView jobGroup(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("jobGroup");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveJobGroup.htm", method = RequestMethod.POST)
	public @ResponseBody String saveJobGroupDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveJobGroup");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"Job_Group","Job Group");
	}

	@RequestMapping(value="/getJobGroupDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getJobGroupDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("Job_Group"));
	}
	@RequestMapping(value="/getJobGroupDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getJobGroupDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("Job_Group",id));
	}

	/* jobGroup end*/

	/* leaveStatusCode*/
	@RequestMapping(value="/leaveStatusCode.htm", method = RequestMethod.POST)
	public ModelAndView leaveStatusCode(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("leaveStatusCode");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveLeaveStatusCode.htm", method = RequestMethod.POST)
	public @ResponseBody String saveLeaveStatusCodeDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveLeaveStatusCode");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"Leave_Status_code","Leave Status code");
	}

	@RequestMapping(value="/getLeaveStatusCodeDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getLeaveStatusCodeDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("Leave_Status_code"));
	}
	@RequestMapping(value="/getLeaveStatusCodeDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getLeaveStatusCodeDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("Leave_Status_code",id));
	}

	/* leaveStatusCode end*/

	/* leaveStatusReason*/
	@RequestMapping(value="/leaveStatusReason.htm", method = RequestMethod.POST)
	public ModelAndView leaveStatusReason(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("leaveStatusReason");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveLeaveStatusReason.htm", method = RequestMethod.POST)
	public @ResponseBody String saveLeaveStatusReasonDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveLeaveStatusReason");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"Leave_Status_Reason","Leave Status Reason");
	}

	@RequestMapping(value="/getLeaveStatusReasonDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getLeaveStatusReasonDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("Leave_Status_Reason"));
	}
	@RequestMapping(value="/getLeaveStatusReasonDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getLeaveStatusReasonDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("Leave_Status_Reason",id));
	}

	/* leaveStatusReason end*/

	/* maritalStatus*/
	@RequestMapping(value="/maritalStatus.htm", method = RequestMethod.POST)
	public ModelAndView maritalStatus(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("maritalStatus");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveMaritalStatus.htm", method = RequestMethod.POST)
	public @ResponseBody String saveMaritalStatusDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveMaritalStatusDetails");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"Marital_Status","Marital Status");
	}

	@RequestMapping(value="/getMaritalStatusDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getMaritalStatusDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("Marital_Status"));
	}
	@RequestMapping(value="/getMaritalStatusDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getMaritalStatusDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("Marital_Status",id));
	}

	/* maritalStatus end*/

	/* militaryStatus*/
	@RequestMapping(value="/militaryStatus.htm", method = RequestMethod.POST)
	public ModelAndView militaryStatus(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("militaryStatus");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveMilitaryStatus.htm", method = RequestMethod.POST)
	public @ResponseBody String saveMilitaryStatusDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveMilitaryStatus");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"Military_Status","Military Status");
	}

	@RequestMapping(value="/getMilitaryStatusDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getMilitaryStatusDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("Military_Status"));
	}
	@RequestMapping(value="/getMilitaryStatusDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getMilitaryStatusDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("Military_Status",id));
	}

	/* militaryStatus end*/

	/* org1*/
	@RequestMapping(value="/org1.htm", method = RequestMethod.POST)
	public ModelAndView org1(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("org1");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}
	@RequestMapping(value="/saveOrg1.htm", method = RequestMethod.POST)
	public @ResponseBody String saveOrg1Details(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveOrg1");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"org1","org1");
	}

	@RequestMapping(value="/getOrg1Details.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getOrg1Details(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("org1"));
	}
	@RequestMapping(value="/getOrg1DetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getOrg1DetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("org1",id));
	}

	/* org1 end*/

	/* org2*/
	@RequestMapping(value="/org2.htm", method = RequestMethod.POST)
	public ModelAndView org2(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("org2");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveOrg2.htm", method = RequestMethod.POST)
	public @ResponseBody String saveOrg2Details(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveOrg2");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"org2","org2");
	}

	@RequestMapping(value="/getOrg2Details.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getOrg2Details(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("org2"));
	}
	@RequestMapping(value="/getOrg2DetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getOrg2DetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("org2",id));
	}

	/* org2 end*/
	/* org3*/
	@RequestMapping(value="/org3.htm", method = RequestMethod.POST)
	public ModelAndView org3(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("org3");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveOrg3.htm", method = RequestMethod.POST)
	public @ResponseBody String saveOrg3Details(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveOrg3");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"org3","org3");
	}

	@RequestMapping(value="/getOrg3Details.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getOrg3Details(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("org3"));
	}
	@RequestMapping(value="/getOrg3DetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getOrg3DetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("org3",id));
	}

	/* org3 end*/
	/* org4*/
	@RequestMapping(value="/org4.htm", method = RequestMethod.POST)
	public ModelAndView org4(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("org4");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveOrg4.htm", method = RequestMethod.POST)
	public @ResponseBody String saveOrg4Details(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveOrg4");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"org4","org4");
	}

	@RequestMapping(value="/getOrg4Details.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getOrg4Details(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("org4"));
	}
	@RequestMapping(value="/getOrg4DetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getOrg4DetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("org4",id));
	}

	/* org4 end*/
	/* org5*/
	@RequestMapping(value="/org5.htm", method = RequestMethod.POST)
	public ModelAndView org5(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("org5");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveOrg5.htm", method = RequestMethod.POST)
	public @ResponseBody String saveOrg5Details(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveOrg5");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"org5","org5");
	}

	@RequestMapping(value="/getOrg5Details.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getOrg5Details(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("org5"));
	}
	@RequestMapping(value="/getOrg5DetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getOrg5DetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("org5",id));
	}

	/* org5 end*/
	/* org6*/
	@RequestMapping(value="/org6.htm", method = RequestMethod.POST)
	public ModelAndView org6(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("org6");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveOrg6.htm", method = RequestMethod.POST)
	public @ResponseBody String saveOrg6Details(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveOrg6");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"org6","org6");
	}

	@RequestMapping(value="/getOrg6Details.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getOrg6Details(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("org6"));
	}
	@RequestMapping(value="/getOrg6DetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getOrg6DetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("org6",id));
	}

	/* org6 end*/
	/* org7*/
	@RequestMapping(value="/org7.htm", method = RequestMethod.POST)
	public ModelAndView org7(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("org7");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveOrg7.htm", method = RequestMethod.POST)
	public @ResponseBody String saveOrg7Details(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveOrg7");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"org7","org7");
	}

	@RequestMapping(value="/getOrg7Details.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getOrg7Details(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("org7"));
	}
	@RequestMapping(value="/getOrg7DetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getOrg7DetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("org7",id));
	}

	/* org7 end*/
	/* org8*/
	@RequestMapping(value="/org8.htm", method = RequestMethod.POST)
	public ModelAndView org8(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("org8");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveOrg8.htm", method = RequestMethod.POST)
	public @ResponseBody String saveOrg8Details(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveOrg8");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"org8","org8");
	}

	@RequestMapping(value="/getOrg8Details.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getOrg8Details(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("org8"));
	}
	@RequestMapping(value="/getOrg8DetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getOrg8DetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("org8",id));
	}

	/* org8 end*/
	/* org9*/
	@RequestMapping(value="/org9.htm", method = RequestMethod.POST)
	public ModelAndView org9(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("org9");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveOrg9.htm", method = RequestMethod.POST)
	public @ResponseBody String saveOrg9Details(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveOrg9");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"org9","org9");
	}

	@RequestMapping(value="/getOrg9Details.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getOrg9Details(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("org9"));
	}
	@RequestMapping(value="/getOrg9DetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getOrg9DetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("org9",id));
	}

	/* org9 end*/
	/* org10*/
	@RequestMapping(value="/org10.htm", method = RequestMethod.POST)
	public ModelAndView org10(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("org10");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveOrg10.htm", method = RequestMethod.POST)
	public @ResponseBody String saveOrg10Details(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveOrg10");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"org10","org10");
	}

	@RequestMapping(value="/getOrg10Details.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getOrg10Details(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("org10"));
	}
	@RequestMapping(value="/getOrg10DetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getOrg10DetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("org10",id));
	}

	/* org10 end*/


	/* PayEntity*/
	@RequestMapping(value="/payEntity.htm", method = RequestMethod.POST)
	public ModelAndView payEntity(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("payEntity");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/savePayEntity.htm", method = RequestMethod.POST)
	public @ResponseBody String savePayEntity(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In savePayEntity");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"pay_entity","Pay Entity");
	}

	@RequestMapping(value="/getPayEntityDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getPayEntityDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("pay_entity"));
	}
	@RequestMapping(value="/getPayEntityDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getgetPayEntityDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("pay_entity",id));
	}

	/* PayEntity end*/
	/* PayFrequency*/
	@RequestMapping(value="/payFrequency.htm", method = RequestMethod.POST)
	public ModelAndView payFrequency(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("payFrequency");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/savePayFrequency.htm", method = RequestMethod.POST)
	public @ResponseBody String savePayFrequency(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In savePayFrequency");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"pay_frequency","Pay Frequency");
	}

	@RequestMapping(value="/getPayFrequencyDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getPayFrequencyDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("pay_frequency"));
	}
	@RequestMapping(value="/getPayFrequencyDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getgetPayFrequencyDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("pay_frequency",id));
	}

	/* PayFrequency end*/
	/* PayGroup*/
	@RequestMapping(value="/payGroup.htm", method = RequestMethod.POST)
	public ModelAndView payGroup(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("payGroup");
		ArrayList<MapDTO> list = referenceTablesDAO.getPayFrequency();
		mav.addObject("baseRateFreqList",list);
		PayGroupDTO dto = new PayGroupDTO();
		dto.setOperation("save");
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		mav.addObject("payGroupDto", dto);
		return mav;
	}

	@RequestMapping(value="/savePayGroup.htm", method = RequestMethod.POST)
	public @ResponseBody String savePayGroup(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="payGroupDto") PayGroupDTO payGroupDto){
		logger.info("In savePayGroup");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.savePayGroup(payGroupDto,user);
	}

	@RequestMapping(value="/getPayGroupDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getPayGroupDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){

		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getPayGroupDetails());
	}
	@RequestMapping(value="/getPayGroupDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getgetPayGroupDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getPayGroupDetailsById(id));
	}

	/* PayGroup end*/
	/* PerformanceAdminRole*/
	@RequestMapping(value="/performanceAdminRole.htm", method = RequestMethod.POST)
	public ModelAndView performanceAdminRole(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("performanceAdminRole");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/savePerformanceAdminRole.htm", method = RequestMethod.POST)
	public @ResponseBody String savePerformanceAdminRole(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In savePerformanceAdminRole");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"Performance_Admin_Role","Performance Admin Role");
	}

	@RequestMapping(value="/getPerformanceAdminRoleDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getPerformanceAdminRoleDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("Performance_Admin_Role"));
	}
	@RequestMapping(value="/getPerformanceAdminRoleDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getgetPerformanceAdminRoleDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("Performance_Admin_Role",id));
	}

	/* PerformanceAdminRole end*/
	/* PerformancePlan*/
	@RequestMapping(value="/performancePlan.htm", method = RequestMethod.POST)
	public ModelAndView performancePlan(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("performancePlan");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/savePerformancePlan.htm", method = RequestMethod.POST)
	public @ResponseBody String savePerformancePlan(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In savePerformancePlan");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"Performance_Plan","Performance Plan");
	}

	@RequestMapping(value="/getPerformancePlanDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getPerformancePlanDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("Performance_Plan"));
	}
	@RequestMapping(value="/getPerformancePlanDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getgetPerformancePlanDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("Performance_Plan",id));
	}

	/* PerformancePlan end*/

	/* PositionLevel*/
	@RequestMapping(value="/positionLevel.htm", method = RequestMethod.POST)
	public ModelAndView positionLevel(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("positionLevel");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/savePositionLevel.htm", method = RequestMethod.POST)
	public @ResponseBody String savePositionLevel(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In savePositionLevel");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"position_level","Position Level");
	}

	@RequestMapping(value="/getPositionLevelDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getPositionLevelDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("position_level"));
	}
	@RequestMapping(value="/getPositionLevelDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getgetPositionLevelDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("position_level",id));
	}

	/* PositionLevel end*/

	/* PositionMaster*/
	@RequestMapping(value="/positionMaster.htm", method = RequestMethod.POST)
	public ModelAndView positionMaster(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("positionMaster");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/savePositionMaster.htm", method = RequestMethod.POST)
	public @ResponseBody String savePositionMaster(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In savePositionMaster");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"position_master","Position Master");
	}

	@RequestMapping(value="/getPositionMasterDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getPositionMasterDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("position_master"));
	}
	@RequestMapping(value="/getPositionMasterDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getgetPositionMasterDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("position_master",id));
	}

	/* PositionMaster end*/

	/* PrimaryJob*/
	@RequestMapping(value="/primaryJob.htm", method = RequestMethod.POST)
	public ModelAndView primaryJob(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("primaryJob");
		PrimaryJobDTO dto = new PrimaryJobDTO();
		dto.setOperation("save");
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		mav.addObject("primaryJobDTO", dto);
		return mav;
	}

	@RequestMapping(value="/savePrimaryJob.htm", method = RequestMethod.POST)
	public @ResponseBody String savePrimaryJob(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="primaryJobDTO") PrimaryJobDTO mapDTO){
		logger.info("In savePrimaryJob");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.savePrimaryJobData(mapDTO,user);
	}

	@RequestMapping(value="/getPrimaryJobDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getPrimaryJobDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getPrimaryJobDetails());
	}
	@RequestMapping(value="/getPrimaryJobDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getgetPrimaryJobDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getPrimaryJobDetailsById(id));
	}

	/* PrimaryJob end*/
	/* PrimaryJobLeave*/
	@RequestMapping(value="/primaryJobLeave.htm", method = RequestMethod.POST)
	public ModelAndView primaryJobLeave(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("primaryJobLeave");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/savePrimaryJobLeave.htm", method = RequestMethod.POST)
	public @ResponseBody String savePrimaryJobLeave(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In savePrimaryJobLeave");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"primary_job_leave","Primary Job Leave");
	}

	@RequestMapping(value="/getPrimaryJobLeaveDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getPrimaryJobLeaveDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("primary_job_leave"));
	}
	@RequestMapping(value="/getPrimaryJobLeaveDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getgetPrimaryJobLeaveDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("primary_job_leave",id));
	}

	/* PrimaryJobLeave end*/
	/* Relationship*/
	@RequestMapping(value="/relationship.htm", method = RequestMethod.POST)
	public ModelAndView relationship(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("relationship");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveRelationship.htm", method = RequestMethod.POST)
	public @ResponseBody String saveRelationship(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveRelationship");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"Relationship","Relationship");
	}

	@RequestMapping(value="/getRelationshipDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getRelationshipDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("Relationship"));
	}
	@RequestMapping(value="/getRelationshipDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getgetRelationshipDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("Relationship",id));
	}

	/* Relationship end*/

	/* Status*/
	@RequestMapping(value="/status.htm", method = RequestMethod.POST)
	public ModelAndView status(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("status");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveStatus.htm", method = RequestMethod.POST)
	public @ResponseBody String saveStatus(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveStatus");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"Status","Status");
	}

	@RequestMapping(value="/getStatusDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getStatusDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("Status"));
	}
	@RequestMapping(value="/getStatusDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getgetStatusDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("Status",id));
	}

	/* Status end*/
	/* StatusCode*/
	@RequestMapping(value="/statusCode.htm", method = RequestMethod.POST)
	public ModelAndView statusCode(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("statusCode");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveStatusCode.htm", method = RequestMethod.POST)
	public @ResponseBody String saveStatusCode(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveStatusCode");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"Status_Code","Status Code");
	}

	@RequestMapping(value="/getStatusCodeDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getStatusCodeDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("Status_Code"));
	}
	@RequestMapping(value="/getStatusCodeDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getgetStatusCodeDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("Status_Code",id));
	}

	/* StatusCode end*/

	/* StatusReason*/
	@RequestMapping(value="/statusReason.htm", method = RequestMethod.POST)
	public ModelAndView statusReason(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("statusReason");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveStatusReason.htm", method = RequestMethod.POST)
	public @ResponseBody String saveStatusReason(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveStatusReason");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"Status_Reason","Status Reason");
	}

	@RequestMapping(value="/getStatusReasonDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getStatusReasonDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("Status_Reason"));
	}
	@RequestMapping(value="/getStatusReasonDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getgetStatusReasonDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("Status_Reason",id));
	}

	/* StatusReason end*/

	/* SuccessionRole*/
	@RequestMapping(value="/successionRole.htm", method = RequestMethod.POST)
	public ModelAndView successionRole(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("successionRole");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveSuccessionRole.htm", method = RequestMethod.POST)
	public @ResponseBody String saveSuccessionRole(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveSuccessionRole");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"Succession_Role","Succession Role");
	}

	@RequestMapping(value="/getSuccessionRoleDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getSuccessionRoleDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("Succession_Role"));
	}
	@RequestMapping(value="/getSuccessionRoleDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getgetSuccessionRoleDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("Succession_Role",id));
	}

	/* SuccessionRole end*/
	/* UnionCode*/
	@RequestMapping(value="/unionCode.htm", method = RequestMethod.POST)
	public ModelAndView unionCode(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("unionCode");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveUnionCode.htm", method = RequestMethod.POST)
	public @ResponseBody String saveUnionCode(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveUnionCode");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"union_code","union code");
	}

	@RequestMapping(value="/getUnionCodeDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getUnionCodeDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("union_code"));
	}
	@RequestMapping(value="/getUnionCodeDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getgetUnionCodeDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("union_code",id));
	}

	/* UnionCode end*/

	/* VeteranStatus*/
	@RequestMapping(value="/veteranStatus.htm", method = RequestMethod.POST)
	public ModelAndView veteranStatus(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("veteranStatus");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveVeteranStatus.htm", method = RequestMethod.POST)
	public @ResponseBody String saveVeteranStatus(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveVeteranStatus");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"Veteran_Status","Veteran Status");
	}

	@RequestMapping(value="/getVeteranStatusDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getVeteranStatusDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("Veteran_Status"));
	}
	@RequestMapping(value="/getVeteranStatusDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getgetVeteranStatusDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("Veteran_Status",id));
	}

	/* VeteranStatus end*/

	/* VisaType*/
	@RequestMapping(value="/visaType.htm", method = RequestMethod.POST)
	public ModelAndView visaType(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("visaType");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveVisaType.htm", method = RequestMethod.POST)
	public @ResponseBody String saveVisaType(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveVisaType");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"VISA_Type","VISA Type");
	}

	@RequestMapping(value="/getVisaTypeDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getVisaTypeDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("VISA_Type"));
	}
	@RequestMapping(value="/getVisaTypeDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getgetVisaTypeDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("VISA_Type",id));
	}

	/* VisaType end*/
	/* BonusAdminRole*/
	@RequestMapping(value="/bonusAdminRole.htm", method = RequestMethod.POST)
	public ModelAndView bonusAdminRole(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("bonusAdminRole");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveBonusAdminRole.htm", method = RequestMethod.POST)
	public @ResponseBody String saveBonusAdminRole(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveBonusAdminRole");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"Bonus_Admin_Role","Bonus Admin Role");
	}

	@RequestMapping(value="/getBonusAdminRoleDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getBonusAdminRoleDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("Bonus_Admin_Role"));
	}
	@RequestMapping(value="/getBonusAdminRoleDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getgetBonusAdminRoleDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("Bonus_Admin_Role",id));
	}

	/* BonusAdminRole end*/

	/* MeritAdminGuidelines*/
	@RequestMapping(value="/meritAdminGuidelines.htm", method = RequestMethod.POST)
	public ModelAndView meritAdminGuidelines(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("meritAdminGuidelines");
		MeritAdminGuidelinesDTO dto = new MeritAdminGuidelinesDTO();
		dto.setOperation("save");
		HashMap<String, ArrayList<MeritAdminGuidelinesDTO>> map = referenceTablesDAO.getPayEntityLookup();
		mav.addObject("payEntityList",map.get("pay_entity"));
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		mav.addObject("mapdto", dto);
		return mav;
	}

	@RequestMapping(value="/saveMeritAdminGuidelines.htm", method = RequestMethod.POST)
	public @ResponseBody String saveMeritAdminGuidelines(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MeritAdminGuidelinesDTO mapDTO){
		logger.info("In saveMeritAdminGuidelines");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveMeritAdminGuidelines(mapDTO,user,"merit_admin_guidelines","merit admin guidelines");
	}

	@RequestMapping(value="/getMeritAdminGuidelinesDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getMeritAdminGuidelinesDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getMeritAdminDetails("merit_admin_guidelines"));
	}
	@RequestMapping(value="/getMeritAdminGuidelinesDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getMeritAdminGuidelinesDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("merit_period") String merit_period,@RequestParam("payEntity") String payEntity,@RequestParam("performanceRating") String performanceRating){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getMeritAdminGuidelinesDetailsById("merit_admin_guidelines",payEntity,merit_period,performanceRating));
	}

	/* MeritAdminGuidelines end*/


	//aruna end
	//Siva changes start
	@RequestMapping(value="/employementAction.htm", method = RequestMethod.POST)
	public ModelAndView employementAction(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("employementAction");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}
	@RequestMapping(value="/saveEmployementAction.htm", method = RequestMethod.POST)
	public @ResponseBody String saveEmployementAction(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveEmployementAction");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"Employement_Action","Employement Action");
	}

	@RequestMapping(value="/getEmployementActionDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getEmployementActionDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("Employement_Action"));
	}
	@RequestMapping(value="/getEmployementActionDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getEmployementActionDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("Employement_Action",id));
	}
	@RequestMapping(value="/employementActionReason.htm", method = RequestMethod.POST)
	public ModelAndView employementActionReason(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("employementActionReason");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}
	@RequestMapping(value="/saveEmployementActionReason.htm", method = RequestMethod.POST)
	public @ResponseBody String saveEmployementActionReason(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveEmployementActionReason");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"Employement_Action_Reason","Employement Action Reason");
	}

	@RequestMapping(value="/getEmployementActionReasonDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getEmployementActionReasonDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("Employement_Action_Reason"));
	}
	@RequestMapping(value="/getEmployementActionReasonDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getEmployementActionReasonDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("Employement_Action_Reason",id));
	}
	@RequestMapping(value="/positionAction.htm", method = RequestMethod.POST)
	public ModelAndView positionAction(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("positionAction");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}
	@RequestMapping(value="/savePositionAction.htm", method = RequestMethod.POST)
	public @ResponseBody String savePositionAction(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In savePositionAction");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"Position_Action","Position Action");
	}

	@RequestMapping(value="/getPositionActionDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getPositionActionDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("Position_Action"));
	}
	@RequestMapping(value="/getPositionActionDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getPositionActionDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("Position_Action",id));
	}
	@RequestMapping(value="/positionActionReason.htm", method = RequestMethod.POST)
	public ModelAndView positionActionReason(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("positionActionReason");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}
	@RequestMapping(value="/savePositionActionReason.htm", method = RequestMethod.POST)
	public @ResponseBody String savePositionActionReason(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In savePositionActionReason");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"Position_Action_Reason","Position Action Reason");
	}

	@RequestMapping(value="/getPositionActionReasonDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getPositionActionReasonDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("Position_Action_Reason"));
	}
	@RequestMapping(value="/getPositionActionReasonDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getPositionActionReasonDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("Position_Action_Reason",id));
	}


	@RequestMapping(value="/state.htm", method = RequestMethod.POST)
	public ModelAndView state(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("state");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("mapdto", dto);
		return mav;
	}
	@RequestMapping(value="/saveState.htm", method = RequestMethod.POST)
	public @ResponseBody String saveState(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In savePositionActionReason");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveData(mapDTO,user,"State","State");
	}

	@RequestMapping(value="/getStateDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getStateDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getDetails("State"));
	}
	@RequestMapping(value="/getStateDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getStateDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getDetailsById("State",id));
	}

	@RequestMapping(value="/city.htm", method = RequestMethod.POST)
	public ModelAndView city(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("city");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("stateList", referenceTablesDAO.getDetails("State"));
		mav.addObject("mapdto", dto);
		return mav;
	}
	@RequestMapping(value="/saveCity.htm", method = RequestMethod.POST)
	public @ResponseBody String saveCity(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In savePositionActionReason");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveCityData(mapDTO,user);
	}

	@RequestMapping(value="/getCityDetailsRef.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getCityDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getCityDetails());
	}
	@RequestMapping(value="/getCityDetailsRefById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getCityDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getCityDetailsById(id));
	}

	@RequestMapping(value="/meritPeriodUpdate.htm", method = RequestMethod.POST)
	public ModelAndView meritPeriodUpdate(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("meritPeriodUpdate");
		MapDTO dto = new MapDTO();
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		dto.setOperation("save");
		mav.addObject("currentMeritPeriod", referenceTablesDAO.getCurrentMeritPeriod());
		mav.addObject("mapdto", dto);
		return mav;
	}
	@RequestMapping(value="/saveMeritPeriodUpdate.htm", method = RequestMethod.POST)
	public @ResponseBody String saveMeritPeriodUpdate(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="mapdto") MapDTO mapDTO){
		logger.info("In saveEmployementAction");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveMeritPeriodUpdate(mapDTO,user);
	}

	@RequestMapping(value="/searchTeamName.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray searchTeamName(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		ArrayList<TeamDTO> list = new ArrayList<TeamDTO>();
		if(req.getParameter("q") !=null && !req.getParameter("q").equalsIgnoreCase("")){
			list.addAll(referenceTablesDAO.searchTeamName(req.getParameter("q")));
		}else{
			list.addAll(referenceTablesDAO.searchTeamName("%"));
		}
		JSONArray obj = (JSONArray) JSONSerializer.toJSON(list);
		return obj;
	}
	
	@RequestMapping(value="/searchTeamParent.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray searchTeamNameByParent(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		ArrayList<TeamDTO> list = new ArrayList<TeamDTO>();
		if(req.getParameter("q") !=null && !req.getParameter("q").equalsIgnoreCase("")){
			list.addAll(referenceTablesDAO.searchTeamNameByParent(req.getParameter("q")));
		}else{
			list.addAll(referenceTablesDAO.searchTeamNameByParent("%"));
		}
		JSONArray obj = (JSONArray) JSONSerializer.toJSON(list);
		return obj;
	}

	@RequestMapping(value="/teamUI.htm", method = RequestMethod.GET)
	public ModelAndView teamUI(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("teamUI");
		TeamDTO dto = new TeamDTO();
		dto.setOperation("save");
		mav.addObject("teamUI", dto);
		return mav;
	}
	@RequestMapping(value="/teamUI.htm", method = RequestMethod.POST)
	public ModelAndView teamUIPost(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return teamUI(req, res, mav);
	}
	@RequestMapping(value="/saveTeam.htm", method = RequestMethod.POST)
	public @ResponseBody String saveTeam(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="teamUI") TeamDTO dto){
		logger.info("In saveTeam");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveTeam(dto,user);
	}
	@RequestMapping(value="/getTeamDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getTeamDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("teamId") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getTeamDetailsById(id));
	}
	@RequestMapping(value="/getTeamDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getTeamDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getTeamDetails());
	}
	@RequestMapping(value="/getTeamParentList.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getTeamParentList(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("teamId") String id){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getTeamParentList(id));
	}
	@RequestMapping(value="/viewListOfEmployeesForSelectedFilter.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray viewListOfEmployeesForSelectedFilter(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="teamUI") TeamDTO dto){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.viewListOfEmployeesForSelectedFilter(dto));
	}
	@RequestMapping(value="/doSearchOrgValue.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray doSearchOrgValue(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("tableName") String tableName){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getLookup(tableName.replace("ORG_LEVEL_", "org")));
	}


	@RequestMapping(value="/jobGrade.htm", method = RequestMethod.POST)
	public ModelAndView jobGrade(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		mav.setViewName("jobGrade");
		JobGradeDTO dto = new JobGradeDTO();
		dto.setOperation("save");
		mav.addObject("payFreqList",referenceTablesDAO.getDetails("Base_Rate_Frequency"));
		mav.addObject("primaryJobList", referenceTablesDAO.getPrimaryJobLookup());
		mav.addObject("scrollPosition",req.getParameter("scrollPosition") !=null ? req.getParameter("scrollPosition").toString() : "0");;
		mav.addObject("jobGradeDTO", dto);
		return mav;
	}

	@RequestMapping(value="/saveJobGrade.htm", method = RequestMethod.POST)
	public @ResponseBody String saveJobGrade(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@ModelAttribute(value="jobGradeDTO") JobGradeDTO jobGradeDTO){
		logger.info("In saveJobGrade");
		TelligentUser user = telligentUtility.getTelligentUser();
		return referenceTablesDAO.saveJobGrade(jobGradeDTO,user);
	}

	@RequestMapping(value="/getJobGradeDetails.htm", method = RequestMethod.POST)
	public @ResponseBody JSONArray getJobGradeDetails(HttpServletRequest req,HttpServletResponse res,ModelAndView mav){
		return (JSONArray) JSONSerializer.toJSON(referenceTablesDAO.getJobGradeDetails());
	}
	@RequestMapping(value="/getJobGradeDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody JSONObject getgetJobGradeDetailsById(HttpServletRequest req,HttpServletResponse res,ModelAndView mav,@RequestParam("id") String id){
		return (JSONObject) JSONSerializer.toJSON(referenceTablesDAO.getJobGradeDetailsById(id));
	}
	//Siva Changes End



}
