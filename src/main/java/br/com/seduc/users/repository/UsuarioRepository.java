package br.com.seduc.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.seduc.users.entidade.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
}
