package net.atos.api.logistica.repository.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "TB_ORDEM_SERVICO",
	uniqueConstraints = @UniqueConstraint(name = "ID_CLIENTE_UNIQUE", columnNames= {"ID_CLIENTE"}))
public class OrdemServicoEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_ORDEM_SERVICO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_ordem")
	@SequenceGenerator(name = "sq_ordem",sequenceName = "sq_ordem",
		allocationSize = 1, initialValue = 1)
	private Long id;
	
	@Column(name = "ID_CLIENTE")
    private Long idCliente;

	@Column(name = "DATA_NASCIMENTO")
	private LocalDate dataNascimento;

	@Column(name = "DATA_EVENTO")
    private LocalDate dataEvento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public LocalDate getDataEvento() {
		return dataEvento;
	}

	public void setDataEvento(LocalDate dataEvento) {
		this.dataEvento = dataEvento;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@PrePersist
	public void atualizaEvento() {
		this.setDataEvento(LocalDate.now());		
	}

}
