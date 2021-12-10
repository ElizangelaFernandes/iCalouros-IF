package br.edu.ifrn.icalouro.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.edu.ifrn.icalouro.service.UsuarioService;

import br.edu.ifrn.icalouro.dominio.Usuario;

/** Esta classe cuida da segurança do site, controlando o acesso às páginas e criptogrando as senhas.*/
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UsuarioService service;

  /** Este método restringe e permite o acesso do usuário a determinadas páginas do site.*/
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().antMatchers("/estilos/**", "/imagens/**", "/eventos/**", "/curso/**", "/sobre","/usuario/cadastro", "/").permitAll()
    .antMatchers("/publico/**", "/usuario/salvar").permitAll()
    .anyRequest().authenticated()
    .and()

    .formLogin()
    .loginPage("/login")
    .defaultSuccessUrl("/", true)
    .failureUrl("/login-error")
    .permitAll()
    .and()
    .logout()
    .logoutSuccessUrl("/");
    
  }

  /** Este método faz parta da criptografia das senhas.*/
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
  }

}