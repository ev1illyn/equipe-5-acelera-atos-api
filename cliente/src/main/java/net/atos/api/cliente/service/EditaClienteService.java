package net.atos.api.cliente.service;

import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.istack.NotNull;

import net.atos.api.cliente.domain.ClienteVO;
import net.atos.api.cliente.factory.ClienteFactory;
import net.atos.api.cliente.repository.ClienteRepository;
import net.atos.api.cliente.repository.entity.ClienteEntity;

@Service
public class EditaClienteService {
	
	private Validator validator;
	private ClienteRepository clienteRepository;
	private BuscaClienteService buscaClienteService;

	public EditaClienteService(Validator validator, ClienteRepository clienteRepository, 
			BuscaClienteService buscaClienteService) {
		this.validator = validator;
		this.clienteRepository = clienteRepository;
		this.buscaClienteService = buscaClienteService;
	}

	@Transactional
	public ClienteVO persistir(@Valid Long clienteId, @NotNull ClienteVO cliente) {

		Set<ConstraintViolation<ClienteVO>>
			validate = this.validator.validate(cliente);
		
		if(!validate.isEmpty()) {
			throw new ConstraintViolationException("Cliente Inválido", validate);
		}
		
		cliente.setId(clienteId);
		
		Optional.ofNullable(clienteId) 
			.orElseThrow(()->new BadRequestException("Identificador de cliente inválido"));
		
		buscaClienteService.recuperarPorId(clienteId);
		
		ClienteEntity clienteEntity = new ClienteFactory(cliente).toEntity();
			
		clienteRepository.save(clienteEntity);
				
		return cliente;
	}

}
