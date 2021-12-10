package br.edu.ifrn.icalouro.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import br.edu.ifrn.icalouro.dominio.Usuario;

/** Esta interface é o repositório da classe Usuario. Ela busca usuários no BD por nome, senha e e-mail. */
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	@Query("select u from Usuario u where u.nome like %:nome%")
	List<Usuario> findByNome(@Param("nome") String nome);
	List<Usuario> findByNomeAndSenha(@Param("nome") String nome, @Param("senha") String senha);
	
	@Query("select u from Usuario u where u.email like %:email%")
	Optional<Usuario> findByEmail(@Param("email") String email);
}
