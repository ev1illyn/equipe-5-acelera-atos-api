package net.atos.api.cliente.factory;

import java.util.concurrent.atomic.AtomicLong;

import net.atos.api.cliente.domain.ClienteVO;
import net.atos.api.cliente.domain.EnderecoVO;
import net.atos.api.cliente.repository.entity.ClienteEntity;
import net.atos.api.cliente.repository.entity.EnderecoEntity;
import net.atos.api.cliente.repository.entity.EnderecoPK;

public class ClienteFactory {

	private ClienteVO vo;
	private ClienteEntity entity;
	
	public ClienteFactory(ClienteVO vo) {
		this.entity = this.transformaEntity(vo);
	}
	
	public ClienteFactory(ClienteEntity entity) {
		throw new IllegalAccessError();
	}
	
	private ClienteEntity transformaEntity(ClienteVO cliente) {

		ClienteEntity clienteEntity = new ClienteEntity();

		clienteEntity.setNome(cliente.getNome());
		clienteEntity.setCpf(cliente.getCpf());
		clienteEntity.setRg(cliente.getRg());
		clienteEntity.setNascimento(cliente.getNascimento());
		clienteEntity.setEmail(cliente.getEmail());
		clienteEntity.setCelular(cliente.getCelular());
		
		AtomicLong codigoEndereco = new AtomicLong();
		
		cliente.getEnderecos().stream().forEach(endereco ->
				this.construirEnderecoEntity(clienteEntity, codigoEndereco, endereco));
		
		return clienteEntity;
		
	}
	
	private void construirEnderecoEntity(ClienteEntity clienteEntity, AtomicLong codigoEndereco, EnderecoVO endereco) {
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

	public ClienteEntity toEntity() {
		return this.entity;
	}
	
}
