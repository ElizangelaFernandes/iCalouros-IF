package br.edu.ifrn.icalouro.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import br.edu.ifrn.icalouro.dominio.Curso;

/** Esta interface é o repositório da entidade Curso. Ela busca cursos no BD por nome e por ID. */
public interface CursoRepository extends JpaRepository<Curso, Integer>{
	@Query("select c from Curso c where c.nome like %:nome%")
	List<Curso> findByNome(@Param("nome") String nome);
}
