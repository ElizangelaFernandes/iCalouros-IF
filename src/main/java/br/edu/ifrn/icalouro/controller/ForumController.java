package br.edu.ifrn.icalouro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/** Esta classe contém o método para entrar no fórum do site. */
@Controller
public class ForumController {
	
	@GetMapping("/forum")
	public String forum() {
		return "forum/forum";
	}
	
}
