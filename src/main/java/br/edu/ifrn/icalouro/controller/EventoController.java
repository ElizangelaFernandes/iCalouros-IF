package br.edu.ifrn.icalouro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/** Esta classe contém os métodos para entrar nas páginas de evento. */
@Controller
@RequestMapping("/eventos")
public class EventoController {
	
	@GetMapping("/codegirl")
	public String codegirl() {
		return "eventos/codegirl";
	}
	
	@GetMapping("/expotec")
	public String expotec() {
		return "eventos/expotec";
	}
	
	@GetMapping("/semadec")
	public String semadec() {
		return "eventos/semadec";
	}
	
}
