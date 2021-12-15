package net.atos.api.cliente.service;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;

import net.atos.api.cliente.domain.ClienteVO;
import net.atos.api.cliente.repository.ClienteRepository;

@Service
public class ExcluiClienteService {

	private Validator validator;
	private ClienteRepository clienteRepository;
	private BuscaClienteService buscaClienteService;

	public ExcluiClienteService(Validator validator, ClienteRepository clienteRepository,
			BuscaClienteService buscaClienteService) {
		this.validator = validator;
		this.clienteRepository = clienteRepository;
		this.buscaClienteService = buscaClienteService;
	}

	@Transactional
	public ClienteVO remover(@NotNull(message = "Id de exclusão inválido") Long clienteId) {

		Optional.ofNullable(clienteId) 
			.orElseThrow(()->new BadRequestException("Identificador de exclusão do cliente inválido"));
		
		ClienteVO clienteDeletado = buscaClienteService.recuperarPorIdVO(clienteId);

		this.clienteRepository.deleteById(clienteId);

		return clienteDeletado;
		
		
	}
}
