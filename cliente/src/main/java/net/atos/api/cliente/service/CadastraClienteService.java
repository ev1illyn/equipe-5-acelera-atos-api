package net.atos.api.cliente.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.ws.rs.NotFoundException;

import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;

import net.atos.api.cliente.domain.ClienteVO;
import net.atos.api.cliente.domain.EnderecoVO;
import net.atos.api.cliente.domain.TipoEndereco;
import net.atos.api.cliente.factory.ClienteFactory;
import net.atos.api.cliente.repository.ClienteRepository;
import net.atos.api.cliente.repository.entity.ClienteEntity;

@Service
public class CadastraClienteService {

	private Validator validator;
	
	private ClienteRepository clienteRepository;
	
	public CadastraClienteService(Validator validator, ClienteRepository repository) {
		this.validator = validator;
		this.clienteRepository = repository;
	}
	
	@Transactional
	public ClienteVO persistir(@NotNull(message = "Cliente não pode ser nulo") ClienteVO cliente) {
        
		Set<ConstraintViolation<ClienteVO>>
			validate = this.validator.validate(cliente);
		
		if(!validate.isEmpty()) {
			throw new ConstraintViolationException("Cliente Inválido", validate);
		}
		
		ClienteEntity clienteEntity = new ClienteFactory(cliente).toEntity();
		
		//clienteRepository.save(clienteEntity);
		clienteEntity = clienteRepository.save(clienteEntity);
		
		cliente.setId(clienteEntity.getId());

		return cliente;
		
	}
	
	public ClienteEntity recuperarPorId(Long id) {
		return this.clienteRepository.findById(id)
				.orElseThrow(()-> new NotFoundException("Cliente "+ id +" não encontrado"));
	}
	
}
	
