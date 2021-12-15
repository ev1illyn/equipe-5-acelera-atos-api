package net.atos.api.cliente.service;

import javax.ws.rs.NotFoundException;

/*import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;*/
import org.springframework.stereotype.Service;

import net.atos.api.cliente.domain.ClienteVO;
import net.atos.api.cliente.factory.ClienteFactory;
import net.atos.api.cliente.repository.ClienteRepository;
import net.atos.api.cliente.repository.entity.ClienteEntity;

@Service
public class BuscaClienteService {
	
	private ClienteRepository clienteRepository;

	public BuscaClienteService(ClienteRepository repository) {
		this.clienteRepository = repository;
	}

	public ClienteEntity recuperarPorId(long id) {
		return this.clienteRepository.findById(id)
				.orElseThrow(()-> new NotFoundException("Cliente " + id + " não encontrado"));		
	}

	public ClienteVO recuperarPorIdVO(long id) {
		ClienteEntity clienteEntity = this.clienteRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Cliente = " + id + "não encontrado"));

		return new ClienteFactory(clienteEntity).toVO();
		
		
	}
	
	/*
	public Page<ClienteVO> recuperarTodosVO(Pageable pageable) {
		
		Page<ClienteEntity> clientesEncontrados = this.clienteRepository.findAll(pageable);
		
		if (clientesEncontrados.isEmpty()) {
				throw new NotFoundException("Nenhum cliente encontrado");
		}
		
		return new PageImpl<>(clientesEncontrados.getContent().stream()
				.map(ClienteFactory::new)
				.map(ClienteFactory::toVO)
				.collect(Collectors.toList()), 
				clientesEncontrados.getPageable(),
				clientesEncontrados.getPageable().getOffset());
		 
	}
	*/

}
