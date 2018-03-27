package br.com.heitor.cursomc.resource;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import br.com.heitor.cursomc.dto.ClienteNewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.heitor.cursomc.domain.Cliente;
import br.com.heitor.cursomc.dto.ClienteDTO;
import br.com.heitor.cursomc.service.ClienteService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;
	
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscar(@PathVariable Long id){
		Cliente cliente = service.buscar(id);
		
		return ResponseEntity.ok().body(cliente);
	}
	
	@GetMapping()
	public ResponseEntity<List<ClienteDTO>> buscarTodos(){
		List<Cliente> clientes = service.buscarTodos();
		
		List<ClienteDTO> clienteDTO = clientes.stream().map(i -> new ClienteDTO(i)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(clienteDTO);
	}
	
	@GetMapping("/page")
	public ResponseEntity<Page<ClienteDTO>> buscarPagina(
			@RequestParam(value="page", defaultValue = "0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue = "24")Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue = "nome")String orderBy,
			@RequestParam(value="direction", defaultValue = "DESC")String direction){
		Page<Cliente> clientes = service.buscarPagina(page, linesPerPage, orderBy, direction);
		
		Page<ClienteDTO> clienteDTO = clientes.map(i -> new ClienteDTO(i));
		
		return ResponseEntity.ok().body(clienteDTO);
	}

	@PostMapping
	public ResponseEntity<Void>salvar(@Valid @RequestBody ClienteNewDTO clienteNewDTO){
		Cliente obj = service.fromDTO(clienteNewDTO);
		Cliente cliente = service.salvar(obj);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> atualizar(@Valid @RequestBody ClienteDTO clienteDTO){
		Cliente obj = service.fromDTO(clienteDTO);
		
		service.atualizar(obj);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> apagar(@PathVariable Long id){
		service.apagar(id);
		return ResponseEntity.noContent().build();
	}
}
