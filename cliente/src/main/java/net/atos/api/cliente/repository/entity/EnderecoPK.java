package net.atos.api.cliente.repository.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

public class EnderecoPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "CD_ENDERECO")
	@NotNull(message = "Campo id não pode ser nulo")
	private Long codigoEndereco;
	
	@ManyToOne
	@JoinColumn(name="ID_CLIENTE")
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((codigoEndereco == null) ? 0 : codigoEndereco.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EnderecoPK other = (EnderecoPK) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (codigoEndereco == null) {
			if (other.codigoEndereco != null)
				return false;
		} else if (!codigoEndereco.equals(other.codigoEndereco))
			return false;
		return true;
	}
	
	
}
