package com.cfm.sacc.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String renderHome() {
		return "home/home";
	}
	
}
