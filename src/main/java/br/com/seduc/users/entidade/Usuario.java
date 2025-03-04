package br.com.seduc.users.entidade;

import br.com.seduc.users.dto.UsuarioDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Nome é obrigatório.")
	@Size(min = 3, max = 100, message = "Nome deve conter entre 3 á 100 caracteres")
	private String nome;
	@NotBlank(message = "Telefone é obrigatório.")
	private String telefone;
	@NotBlank(message = "Email é obrigatório.")
	@Email(message = "Email deve ser válido")
	private String email;
	@NotBlank
	@Size(min = 7, message = "A senha deve conter pelo menos 8 caracteres.")
	private String senha;
	
	public Usuario() {
		
	}
	
	public Usuario(UsuarioDto dto){
		this.nome = dto.nome();
		this.telefone = dto.telefone();
		this.email = dto.email();
		this.senha = dto.senha();
	}
	
	public Usuario(Long id, String nome, String telefone, String email, String senha) {
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		this.senha = senha;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
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
	
}
