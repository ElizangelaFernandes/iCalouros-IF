package br.edu.ifrn.icalouro.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import br.edu.ifrn.icalouro.dominio.Materia;

/** Esta interface é o repositório da entidade Matéria. Ela busca matérias no BD por id do curso e nome da matéria. */
public interface MateriaRepository extends JpaRepository<Materia, Integer>{
	@Query("select m from Materia m where m.nome like %:nome%")
	List<Materia> findByNome(@Param("nome") String nome);
	
	@Query("select m from Materia m where m.curso.id = :idCurso")
	List<Materia> findByidCurso(@Param("idCurso") Integer idCurso);
}
