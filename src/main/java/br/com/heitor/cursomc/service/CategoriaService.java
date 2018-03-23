package br.com.heitor.cursomc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.heitor.cursomc.domain.Categoria;
import br.com.heitor.cursomc.repository.CategoriaRepository;
import br.com.heitor.cursomc.service.exceptions.CategoriaInexistenteOuInativaException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria buscar(Long id) {
		Optional<Categoria> categoriaExistente = categoriaRepository.findById(id); 
		return categoriaExistente.orElseThrow(()-> new CategoriaInexistenteOuInativaException());
	}

	public Categoria salvar(Categoria categoria) {
		return categoriaRepository.saveAndFlush(categoria);		
	}

	public Categoria atualizar(Categoria categoria) {
		return categoriaRepository.saveAndFlush(categoria);	
	}

	public void apagar(Long id) {
		categoriaRepository.deleteById(id);
		
	}

	public List<Categoria> buscarTodos() {
		return categoriaRepository.findAll();
	}

	@SuppressWarnings("deprecation")
	public Page<Categoria>buscarPagina(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		return categoriaRepository.findAll(pageRequest);
	}
}
