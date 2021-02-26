package com.openclassrooms.SafetyNetAlerts.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

	@GetMapping(value = "/")
	public String safetyNet() {
		return "safetyNet is working";
	}

	@PostMapping(value = "/testpost")
	public String safetyNet2() {
		return "safetyNet is working 2";
	}
}
