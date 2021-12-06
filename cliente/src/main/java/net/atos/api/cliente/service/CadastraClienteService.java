package net.atos.api.cliente.service;

import java.time.LocalDate;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import net.atos.api.cliente.domain.ClienteVO;
import net.atos.api.cliente.domain.EnderecoVO;
import net.atos.api.cliente.repository.ClienteRepository;
import net.atos.api.cliente.repository.entity.ClienteEntity;import net.atos.api.cliente.repository.entity.EnderecoEntity;
import net.atos.api.cliente.repository.entity.EnderecoPK;

@Service
public class CadastraClienteService {

	private Validator validator;
	
	private ClienteRepository clienteRepository;
	
	public CadastraClienteService(Validator validator, ClienteRepository repository) {
		this.validator = validator;
		this.clienteRepository = repository;
	}
	
	public ClienteVO persistir(@NotNull(message = "Cliente não pode ser nulo") ClienteVO cliente) {
		
		Set<ConstraintViolation<ClienteVO>>
			validate = this.validator.validate(cliente);
		
		if(!validate.isEmpty()) {
			throw new ConstraintViolationException("Cliente Inválido", validate);
		}
		
		ClienteEntity clienteEntity = new ClienteEntity();

		cliente.setNome("Loki da Silva Oliveira");
		cliente.setCpf("05362695860");
		cliente.setRg("20556585221");
		cliente.setNascimento(LocalDate.now());
		cliente.setEmail("loki@gmail.com");
		cliente.setAtivo(true);
		cliente.setCelular(899554415l);
		
		AtomicLong codigoEndereco = new AtomicLong();
		
		cliente.getEnderecos().stream().forEach(endereco ->
				this.construirEndereco(clienteEntity, codigoEndereco, endereco));
		
		clienteRepository.save(clienteEntity);
		cliente.setId(clienteEntity.getId());
		return cliente;
		
	}
	
	private void construirEndereco(ClienteEntity clienteEntity, AtomicLong codigoEndereco, EnderecoVO endereco) {
		EnderecoEntity enderecoEntity = new EnderecoEntity();
		
		enderecoEntity.setId(new EnderecoPK());	
		enderecoEntity.getId().setCodigoEndereco(codigoEndereco.incrementAndGet());
		enderecoEntity.getId().setCliente(clienteEntity);
		
		enderecoEntity.setRua(endereco.getRua());
		enderecoEntity.setNumero(endereco.getNumero());
		enderecoEntity.setBairro(endereco.getBairro());
		enderecoEntity.setCidade(endereco.getCidade());
		enderecoEntity.setUF(endereco.getUF());
		enderecoEntity.setPais(endereco.getPais());
		enderecoEntity.setCep(endereco.getCep());
		enderecoEntity.setTelefone_fixo(endereco.getTelefone_fixo());
		enderecoEntity.setTipoEndereco(endereco.getTipoEndereco());
	}

}
	
