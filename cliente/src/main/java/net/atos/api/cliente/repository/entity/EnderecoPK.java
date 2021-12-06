package net.atos.api.cliente.repository.entity;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

public class EnderecoPK {

	@Column(name = "CD_ENDERECO")
	@NotNull(message = "Campo id não pode ser nulo")
	private Long codigoEndereco;
	
	@ManyToOne
	private ClienteEntity cliente;

	public Long getCodigoEndereco() {
		return codigoEndereco;
	}

	public void setCodigoEndereco(Long codigoEndereco) {
		this.codigoEndereco = codigoEndereco;
	}

	public ClienteEntity getCliente() {
		return cliente;
	}

	public void setCliente(ClienteEntity cliente) {
		this.cliente = cliente;
	}
	
}
