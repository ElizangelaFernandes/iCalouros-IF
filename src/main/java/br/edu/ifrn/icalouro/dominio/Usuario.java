package br.edu.ifrn.icalouro.dominio;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


/** Esta classe contém os atributos e métodos de um usuário.*/
@Entity
public class Usuario {
	
	public static final String ADMIN = "ADMIN";
	public static final String USUARIO_COMUM = "COMUM";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	@NotBlank(message = "O campo nome é obrigatório.")
	@Size(min = 2, message = "Um nome deve ter pelo menos dois caracteres.")
	private String nome;

	@NotBlank(message = "O campo email é obrigatório.")
	private String email;

	@NotBlank(message = "O campo endereço é obrigatório.")
	private String endereco;
	
	@NotBlank(message = "O campo senha é obrigatório.")
	@Size(min = 8, message = "O campo senha deve ter no mínimo 8 caracteres.")
	private String senha;

	@NotBlank(message = "O campo sexo é obrigatório.")
	private String sexo;
	
	@Column(nullable = false)
	private String perfil = USUARIO_COMUM;
	
	@OneToMany
	private List<Postagem> postagens;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getPerfil() {
		return perfil;
	}
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public List<Postagem> getPostagens() {
		return postagens;
	}
	public void setPostagens(List<Postagem> postagens) {
		this.postagens = postagens;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
