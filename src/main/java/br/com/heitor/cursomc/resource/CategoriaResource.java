package br.com.heitor.cursomc.resource;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@GetMapping("/page")
	public ResponseEntity<Page<CategoriaDTO>> buscarPagina(
			@RequestParam(value="page", defaultValue = "0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue = "24")Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue = "nome")String orderBy,
			@RequestParam(value="direction", defaultValue = "DESC")String direction){
		Page<Categoria> categorias = service.buscarPagina(page, linesPerPage, orderBy, direction);
		
		Page<CategoriaDTO> categoriaDTO = categorias.map(i -> new CategoriaDTO(i));
		
		return ResponseEntity.ok().body(categoriaDTO);
	}
	
	@PostMapping
	public ResponseEntity<Void>salvar(@Valid @RequestBody CategoriaDTO categoriaDTO){
		Categoria obj = service.fromDTO(categoriaDTO); 
		Categoria categoria = service.salvar(obj);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> atualizar(@Valid @RequestBody CategoriaDTO categoriaDTO, @PathVariable Long id){
		Categoria obj = service.fromDTO(categoriaDTO); 
		
		service.atualizar(obj);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> apagar(@PathVariable Long id){
		service.apagar(id);
		return ResponseEntity.noContent().build();
	}
}
