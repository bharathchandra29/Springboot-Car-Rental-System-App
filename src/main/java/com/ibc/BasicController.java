package com.ibc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/basic")
public class BasicController {
	
	@RequestMapping("/home")
	public String home() {
		return "This is home.";
	}
	
	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome to this simple microservice";
	}

}