package br.edu.ifrn.icalouro.controller;


import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import br.edu.ifrn.icalouro.dominio.Usuario;
import br.edu.ifrn.icalouro.repository.UsuarioRepository;

/** Esta classe armazena os métodos para cadastrar e editar um usuário.*/
@Controller
@RequestMapping("/usuario")
public class CadastroUsuarioController {

  @Autowired
  private UsuarioRepository usuarioRepository;

  /** Este método é para entrar cadastro.*/
  @GetMapping("/cadastro")
  public String entrarCadastro(ModelMap model) {
    model.addAttribute("usuario", new Usuario());
    return "usuario/cadastro";
  }
  

  /** Este método é para salvar o usuário no BD.*/
  @PostMapping("/salvar")
  @Transactional(readOnly = false)
  public String salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attr) {
   
	  if (result.hasErrors()) {
		  return "usuario/cadastro";
	  }
	 
	  String senhaCriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
	  
      usuario.setSenha(senhaCriptografada);
      
      usuarioRepository.save(usuario);
      
      attr.addFlashAttribute("msgSucesso", "Operação realizada com sucesso!");
	  
   
    return "redirect:/usuario/cadastro";
  }

}