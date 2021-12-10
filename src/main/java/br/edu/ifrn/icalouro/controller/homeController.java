package br.edu.ifrn.icalouro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.icalouro.dominio.Postagem;
import br.edu.ifrn.icalouro.repository.PostagemRepository;

/** Esta classe contém os métodos para entrar em algumas páginas do site. */
@Controller
public class homeController {

	@Autowired
	PostagemRepository postagemRepo;
	
	@GetMapping("/")
	public String inicio() {
		return "home/index";
	}
	
	@GetMapping("eventos/codegirl")
	public String codeGirl() {
		return "eventos/codegirl";
	}
	
	@GetMapping("eventos/expotec")
	public String expotec() {
		return "eventos/expotec";
	}
	
	@GetMapping("eventos/semadec")
	public String semadec() {
		return "eventos/semadec";
	}
	
	@GetMapping("curso/informatica")
	public String informatica() {
		return "curso/informatica";
	}
	
	@GetMapping("curso/materias")
	public String materias() {
		return "curso/materias";
	}
	
	@GetMapping("forum")
	public String forum(ModelMap model) {
		model.addAttribute("postagens", postagemRepo.findAll());
		model.addAttribute("postagem", new Postagem());
		return "forum/forum";
	}
	
	@RequestMapping("forum/salvar")
	public String salvar(Postagem postagem, RedirectAttributes attr, @RequestParam(name = "id", required = false) int id, @RequestParam(name = "resposta", required = false) String resposta) {
		if (id > 0) {
			Postagem p = postagemRepo.findById(id).get();
			p.setResposta(resposta);
			postagemRepo.save(p);
		    return "redirect:/forum";
		}
		
	    postagemRepo.save(postagem);
		return "redirect:/forum";
	}
	
	
	@GetMapping("sobre")
	public String sobre() {
		return "sobre/sobre";
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
