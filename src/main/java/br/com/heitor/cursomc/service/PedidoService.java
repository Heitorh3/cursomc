package br.com.heitor.cursomc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.heitor.cursomc.domain.Pedido;
import br.com.heitor.cursomc.repository.PedidoRepository;
import br.com.heitor.cursomc.service.exceptions.PedidoInexistenteOuInativaException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository categoriaRepository;
	
	public Pedido buscar(Long id) {
		Optional<Pedido> categoriaExistente = categoriaRepository.findById(id); 
		return categoriaExistente.orElseThrow(()-> new PedidoInexistenteOuInativaException());
	}
}
