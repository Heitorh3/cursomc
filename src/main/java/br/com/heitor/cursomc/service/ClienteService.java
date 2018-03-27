package br.com.heitor.cursomc.service;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import br.com.heitor.cursomc.domain.Cidade;
import br.com.heitor.cursomc.domain.Endereco;
import br.com.heitor.cursomc.dto.ClienteNewDTO;
import br.com.heitor.cursomc.repository.CidadeRepository;
import br.com.heitor.cursomc.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.heitor.cursomc.domain.Cliente;
import br.com.heitor.cursomc.dto.ClienteDTO;
import br.com.heitor.cursomc.repository.ClienteRepository;
import br.com.heitor.cursomc.service.exceptions.ClienteInexistenteOuInativaException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	public Cliente buscar(Long id) {
		Optional<Cliente> clienteExistente = clienteRepository.findById(id); 
		return clienteExistente.orElseThrow(()-> new ClienteInexistenteOuInativaException());
	}

	public Cliente salvar(Cliente cliente) {
		Cliente cliente1 = clienteRepository.saveAndFlush(cliente);
		enderecoRepository.saveAll(cliente.getEnderecos());

		return cliente1;
	}
	
	public Cliente atualizar(Cliente clienteDTO) {
		Optional<Cliente> newCliente = clienteRepository.findById(clienteDTO.getId());
		newCliente.get().setNome(clienteDTO.getNome());
		newCliente.get().setEmail(clienteDTO.getEmail());
		
		return clienteRepository.saveAndFlush(newCliente.get());	
	}

	public void apagar(Long id) {
		clienteRepository.deleteById(id);
		
	}

	public List<Cliente> buscarTodos() {
		return clienteRepository.findAll();
	}

	@SuppressWarnings("deprecation")
	public Page<Cliente>buscarPagina(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		return clienteRepository.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO clienteDTO){
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
	}

	public Cliente fromDTO(ClienteNewDTO clienteNewDTO){
		Cliente cli = new Cliente(null, clienteNewDTO.getNome(), clienteNewDTO.getEmail(), clienteNewDTO.getCpfOuCnpj(), clienteNewDTO.getTipoCliente());

		Optional<Cidade> cidade = cidadeRepository.findById(clienteNewDTO.getIdCidade());

		Endereco endereco = new Endereco(null, clienteNewDTO.getLogradouro(),clienteNewDTO.getNumero(),
				clienteNewDTO.getComplemento(),clienteNewDTO.getBairro(), clienteNewDTO.getCep(),cli, cidade.get());

		cli.getEnderecos().add(endereco);
		cli.getTelefones().add(clienteNewDTO.getTelefone1());

		if (clienteNewDTO.getTelefone2() != null){
			cli.getTelefones().add(clienteNewDTO.getTelefone2());
		}
		if (clienteNewDTO.getTelefone3() != null){
			cli.getTelefones().add(clienteNewDTO.getTelefone3());
		}
		return cli;
	}

}
