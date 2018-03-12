package br.com.heitor.cursomc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.heitor.cursomc.domain.Categoria;
import br.com.heitor.cursomc.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria buscar(Long id) {
		Optional<Categoria> categoriaExistente = categoriaRepository.findById(id); 
		if(categoriaExistente.isPresent()) {
			return categoriaExistente.get();
		}
		
		return null;
	}
}
