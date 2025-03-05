package br.com.seduc.users.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import br.com.seduc.users.dto.UsuarioDto;
import br.com.seduc.users.entidade.Usuario;
import br.com.seduc.users.repository.UsuarioRepository;

@Tag(name = "Usuarios", description = "Gerenciamento de usuários")
@RestController
@RequestMapping("usuario")
public class UsuarioController {
	
	@Autowired
	UsuarioRepository repo;
	private final PasswordEncoder enconder;

    public UsuarioController(PasswordEncoder enconder) {
        this.enconder = enconder;
    }

    @GetMapping
	@Operation(summary = "Listar todos os usuários")
	public List<Usuario> getAll(){
		List<Usuario> user = repo.findAll();
		return user;
	}


	@GetMapping("/{id}")
	@Operation(summary = "Listar um usuário especifico")
	public ResponseEntity<?> getSpecificed(@PathVariable Long id){
		Usuario user = repo.findById(id).orElse(null);

		if(user == null){
			Map<String, String> response = new HashMap<>();
			response.put("message", "Usuário não encontrado");
			return ResponseEntity.status(404).body(response);
		}

		return ResponseEntity.ok(user);
	}

	@PostMapping
	@Operation(summary = "Cria um novo usuário")
	public ResponseEntity<Map<String, String>> save(@Valid @RequestBody UsuarioDto dto, BindingResult result) {
		if(result.hasErrors()){
			Map<String, String> erros = new HashMap<>();

			for(org.springframework.validation.FieldError error : result.getFieldErrors()){
				erros.put(error.getField(), error.getDefaultMessage());
			}
			return ResponseEntity.badRequest().body(erros);
		}

		Usuario user = new Usuario(dto);
		user.setSenha(enconder.encode(user.getSenha()));
		repo.save(user);
		Map<String, String> response = new HashMap<>();
		response.put("message", "Usuário " + user.getNome() + " criado com sucesso.");
		return ResponseEntity.status(201).body(response);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Altera um usuário especifico")
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
		user.setSenha(enconder.encode(dto.senha()));

		repo.save(user);

		Map<String, String> response = new HashMap<>();
		response.put("message", "Usuário com o ID " + user.getId() + " alterado com sucesso");
		return ResponseEntity.status(200).body(response);
	}

	@DeleteMapping("{id}")
	@Operation(summary = "Deleta um usuário")
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
