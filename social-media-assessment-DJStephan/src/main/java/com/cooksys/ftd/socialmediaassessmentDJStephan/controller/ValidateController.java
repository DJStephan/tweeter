package com.cooksys.ftd.socialmediaassessmentDJStephan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.ftd.socialmediaassessmentDJStephan.services.ValidateService;

@RestController
public class ValidateController {

	private ValidateService validateService;

	@Autowired
	public ValidateController(ValidateService validateService) {
		this.setValidateService(validateService);
	}

	public ValidateService getValidateService() {
		return validateService;
	}

	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}

	@GetMapping("validate/tag/exist/{label}")
	public boolean validateTag(@RequestParam("label") String label) {
		return this.validateService.validateTag(label);
	}

	@GetMapping("validate/username/exists/@{username}")
	public boolean validateUserName(@RequestParam("username") String userName) {
		return validateService.validateUserName(userName);
	}

	@GetMapping("validate/username/available/@{username}")
	public boolean validateUserNameAvailable(@RequestParam("username") String userName) {
		return !(validateUserName(userName));
	}

}
