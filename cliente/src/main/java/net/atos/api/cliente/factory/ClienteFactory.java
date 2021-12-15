package net.atos.api.cliente.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
		this.vo = vo;
	}
	
	public ClienteFactory(ClienteEntity entity) {
		//throw new IllegalAccessError();
		this.entity = entity;
		this.vo = this.transformaVO(entity);
	}
	
	private ClienteVO transformaVO(ClienteEntity entity) {
		
		ClienteVO clienteVO = new ClienteVO();

		clienteVO.setId(entity.getId());
		clienteVO.setNome(entity.getNome());
		clienteVO.setCpf(entity.getCpf());
		clienteVO.setRg(entity.getRg());
		clienteVO.setNascimento(entity.getNascimento());
		clienteVO.setEmail(entity.getEmail());
		clienteVO.setCelular(entity.getCelular());
		
		AtomicLong codigoEndereco = new AtomicLong();
		
		List<EnderecoEntity> enderecos = Optional.ofNullable(entity.getEnderecos()).orElseGet(ArrayList::new);
		enderecos.stream().forEach(endereco -> 
			this.construirEnderecoVO(clienteVO, codigoEndereco, endereco));
		
		return clienteVO;
		
	}

	private void construirEnderecoVO(ClienteVO clienteVO, AtomicLong codigoEndereco, EnderecoEntity endereco) {
		
		EnderecoVO enderecoVO = new EnderecoVO();
		enderecoVO.setRua(endereco.getRua());
		enderecoVO.setNumero(endereco.getNumero());
		enderecoVO.setBairro(endereco.getBairro());
		enderecoVO.setCidade(endereco.getCidade());
		enderecoVO.setUF(endereco.getUF());
		enderecoVO.setPais(endereco.getPais());
		enderecoVO.setCep(endereco.getCep());
		enderecoVO.setTelefone_fixo(endereco.getTelefone_fixo());
		enderecoVO.setTipoEndereco(endereco.getTipoEndereco());
		
		clienteVO.add(enderecoVO);
		
	}

	private ClienteEntity transformaEntity(ClienteVO cliente) {

		ClienteEntity clienteEntity = new ClienteEntity();

		clienteEntity.setId(cliente.getId());
		clienteEntity.setNome(cliente.getNome());
		clienteEntity.setCpf(cliente.getCpf());
		clienteEntity.setRg(cliente.getRg());
		clienteEntity.setNascimento(cliente.getNascimento());
		clienteEntity.setEmail(cliente.getEmail());
		clienteEntity.setCelular(cliente.getCelular());
		
		AtomicLong codigoEndereco = new AtomicLong();
		
		List<EnderecoVO> enderecos = Optional.ofNullable(cliente.getEnderecos()).orElseGet(ArrayList::new);
		enderecos.stream().forEach(endereco -> 
				this.construirEnderecoEntity(clienteEntity, codigoEndereco, endereco));
		
		/*cliente.getEnderecos().stream().forEach(endereco ->
				this.construirEnderecoEntity(clienteEntity, codigoEndereco, endereco));
		*/
		
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
		
		clienteEntity.add(enderecoEntity);
	}

	public ClienteEntity toEntity() {
		return this.entity;
	}

	public ClienteVO toVO() {
		return this.vo;
	}
	
}
