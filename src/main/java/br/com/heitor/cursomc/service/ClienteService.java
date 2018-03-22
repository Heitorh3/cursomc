package br.com.heitor.cursomc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.heitor.cursomc.domain.Cliente;
import br.com.heitor.cursomc.repository.ClienteRepository;
import br.com.heitor.cursomc.service.exceptions.ClienteInexistenteOuInativaException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository categoriaRepository;
	
	public Cliente buscar(Long id) {
		Optional<Cliente> categoriaExistente = categoriaRepository.findById(id); 
		return categoriaExistente.orElseThrow(()-> new ClienteInexistenteOuInativaException());
	}
}
