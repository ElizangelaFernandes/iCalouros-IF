package br.edu.ifrn.icalouro.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.ifrn.icalouro.dominio.Postagem;

/** Esta interface é o repositório da classe Postagem. */
public interface PostagemRepository extends JpaRepository<Postagem, Integer>{
}
