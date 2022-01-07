package br.edu.ifrn.icalouro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/** Esta classe contém os métodos para entrar em algumas páginas do site. */
@Controller
public class HomeController {

	@GetMapping("/")
	public String inicio() {
		return "home/index";
	}
	
	@GetMapping("/sobre")
	public String sobre() {
		return "home/sobre";
	}
	
	@GetMapping("/login")
	public String login() {
	    return "usuario/login";
	}

	@GetMapping("/login-error")
    public String loginError(ModelMap model) {
	    model.addAttribute("msgErro", "Login ou senha incorreto. Tente novamente!");
	    return "usuario/login";
	}
	 
}
