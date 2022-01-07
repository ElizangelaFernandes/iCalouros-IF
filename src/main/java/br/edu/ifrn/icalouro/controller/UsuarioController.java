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
import br.edu.ifrn.icalouro.repository.CursoRepository;
import br.edu.ifrn.icalouro.repository.MateriaRepository;
import br.edu.ifrn.icalouro.repository.UsuarioRepository;

/** Esta classe armazena os métodos para cadastrar um usuário. */
@Controller
@RequestMapping("/usuario")
public class UsuarioController {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private CursoRepository cursoRepository;

  @Autowired
  private MateriaRepository materiaRepository;

  @GetMapping("/cadastro")
  public String entrarCadastro(ModelMap model) {
    model.addAttribute("usuario", new Usuario());
    return "usuario/cadastro";
  }

  /**
   * metodo que ira salvar o usuario caso ele tenha digitado todos os campos
   * pedidos corretamente
   * 
   * @author elizangela
   * @return usuario/cadastro
   */
  /**
   * havendo também em seguida a parte da criptografia da senha digitada pelo
   * usuario
   */
  @PostMapping("/salvar")
  @Transactional(readOnly = false)
  public String salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attr) {
    if (result.hasErrors()) {
      return "usuario/cadastro";
    }
    String senhaCriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
    usuario.setSenha(senhaCriptografada);
    usuarioRepository.save(usuario);
    attr.addFlashAttribute("msgSucesso", "O usuário foi cadastrado com sucesso!");
    return "redirect:/usuario/cadastro";
  }

}