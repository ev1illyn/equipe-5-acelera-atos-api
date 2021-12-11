package net.atos.api.cliente.service;

import javax.validation.Validator;

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
	
	public Object persistir(ClienteVO cliente) {
		return null;
	}

}
