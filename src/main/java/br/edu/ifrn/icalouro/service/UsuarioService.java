package br.edu.ifrn.icalouro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.ifrn.icalouro.dominio.Usuario;
import br.edu.ifrn.icalouro.repository.UsuarioRepository;



/** Esta classe é a responsável por prestar um serviço para o sistema de login.*/
@Service
public class UsuarioService implements UserDetailsService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  /** Este método verifica se os dados fornecidos no formulário de login batem com os de algum usuário inserido no banco de dados.*/
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Usuario usuario = usuarioRepository.findByEmail(username)
    .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado"));

    return new User(
      usuario.getEmail(),
      usuario.getSenha(),
      AuthorityUtils.createAuthorityList(usuario.getPerfil())
    );

  }

}