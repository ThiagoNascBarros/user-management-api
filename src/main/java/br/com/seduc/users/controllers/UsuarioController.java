package br.com.seduc.users.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import br.com.seduc.users.dto.UsuarioDto;
import br.com.seduc.users.entidade.Usuario;
import br.com.seduc.users.repository.UsuarioRepository;

@RestController
@RequestMapping("usuario")
public class UsuarioController {
	
	@Autowired
	UsuarioRepository repo;
	
	@GetMapping
	public List<Usuario> getAll(){
		List<Usuario> user = repo.findAll();
		return user;
	}

	@PostMapping
	public ResponseEntity<Map<String, String>> save(@Valid @RequestBody UsuarioDto dto, BindingResult result) {
		if(result.hasErrors()){
			Map<String, String> erros = new HashMap<>();

			for(org.springframework.validation.FieldError error : result.getFieldErrors()){
				erros.put(error.getField(), error.getDefaultMessage());
			}
			return ResponseEntity.badRequest().body(erros);
		}

		Usuario user = new Usuario(dto);
		repo.save(user);
		Map<String, String> response = new HashMap<>();
		response.put("message", "Usuário criado com sucesso.");
		return ResponseEntity.status(201).body(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UsuarioDto dto){
		Usuario user = repo.findById(id).orElse(null);

		if (user == null) {
			// Criando uma mensagem personalizada de erro
			Map<String, String> response = new HashMap<>();
			response.put("message", "Usuário com ID " + id + " não encontrado.");

			return ResponseEntity.status(404).body(response);
		}

		user.setNome(dto.nome());
		user.setTelefone(dto.telefone());
		user.setEmail(dto.email());
		user.setSenha(dto.senha());

		repo.save(user);

		return ResponseEntity.ok(dto);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Map<String, String>> delete(@PathVariable Long id){
		Usuario user = repo.findById(id).orElse(null);

		if(user == null){
			Map<String, String> response = new HashMap<>();
			response.put("message", "Usuário com o ID " + id + " não encontrado");
			return ResponseEntity.status(404).body(response);
		}

		repo.delete(user);

		Map<String, String> response = new HashMap<>();
		response.put("message", "Usuário com ID " + id + " com o nome " + user.getNome() + " foi excluído com sucesso.");

		return ResponseEntity.ok(response);
	}
	
}
