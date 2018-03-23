package br.com.heitor.cursomc.resource;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.heitor.cursomc.domain.Categoria;
import br.com.heitor.cursomc.dto.CategoriaDTO;
import br.com.heitor.cursomc.service.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;
	
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> buscar(@PathVariable Long id){
		Categoria categoria = service.buscar(id);
		
		return ResponseEntity.ok().body(categoria);
	}
	
	@GetMapping()
	public ResponseEntity<List<CategoriaDTO>> buscarTodos(){
		List<Categoria> categorias = service.buscarTodos();
		
		List<CategoriaDTO> categoriaDTO = categorias.stream().map(i -> new CategoriaDTO(i)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(categoriaDTO);
	}
	
	@PostMapping
	public ResponseEntity<Void>salvar(@RequestBody Categoria categoria){
		categoria = service.salvar(categoria);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> atualizar(@RequestBody Categoria categoria, @PathVariable Long id){
		
		categoria = service.atualizar(categoria);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> apagar(@PathVariable Long id){
		service.apagar(id);
		return ResponseEntity.noContent().build();
	}
}
