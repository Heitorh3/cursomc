package br.com.heitor.cursomc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
}
