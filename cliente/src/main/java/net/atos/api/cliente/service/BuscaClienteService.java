package net.atos.api.cliente.service;

import javax.validation.Validator;
import javax.ws.rs.NotFoundException;

import org.springframework.stereotype.Service;

import net.atos.api.cliente.repository.ClienteRepository;
import net.atos.api.cliente.repository.entity.ClienteEntity;

@Service
public class BuscaClienteService {

	private Validator validator;
	
	private ClienteRepository clienteRepository;
	
	public BuscaClienteService(Validator validator, ClienteRepository repository) {
		this.validator = validator;
		this.clienteRepository = repository;
	}
	
	public ClienteEntity recuperarPorId(Long id) {
		return this.clienteRepository.findById(id)
				.orElseThrow(()-> new NotFoundException("Cliente "+ id +" não encontrado"));
	}

}
