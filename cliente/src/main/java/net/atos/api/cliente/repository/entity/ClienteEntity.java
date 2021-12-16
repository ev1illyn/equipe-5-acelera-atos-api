package net.atos.api.cliente.repository.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "TB_CLIENTE")
public class ClienteEntity implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_CLIENTE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_cliente")
	@SequenceGenerator(name = "sq_cliente",sequenceName = "sq_cliente",
    allocationSize = 1,
    initialValue = 1)
	private Long id;

	@Column(name = "NOME_CLIENTE")
	@NotNull(message = "Campo nome não pode ser nulo")
	private String nome;

	@Column(name = "CPF_CLIENTE")
	@NotNull(message = "Campo cpf não pode ser nulo")
	//@CPF(message = "CPF inválido")
	private String cpf;

	@Column(name = "RG_CLIENTE")
	@NotNull(message = "Campo rg não pode ser nulo")
	private String rg;

	@Column(name = "NASCIMENTO_CLIENTE")
	@NotNull(message = "Campo nascimento não pode ser nulo")
	private LocalDate nascimento;

	@Column(name = "EMAIL_CLIENTE")
	@NotNull(message = "Campo email não pode ser nulo")
	private String email;

	@Column(name = "CELULAR_CLIENTE")
	@NotNull(message = "Campo celular não pode ser nulo")
	private Long celular;

	@Column(name = "ENDERECOS_CLIENTE")
	@NotNull(message = "Campo endereço não pode ser nulo")
	@Size(min = 1, message = "Campo endereço deve ter pelo menos um item")
	@Valid
	@OneToMany(mappedBy = "id.cliente", cascade = CascadeType.ALL)
	private List<EnderecoEntity> enderecos;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public LocalDate getNascimento() {
		return nascimento;
	}
	public void setNascimento(LocalDate nascimento) {
		this.nascimento = nascimento;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getCelular() {
		return celular;
	}
	public void setCelular(Long celular) {
		this.celular = celular;
	}
	public List<EnderecoEntity> getEnderecos() {
		return enderecos;
	}
	public void setEnderecos(List<EnderecoEntity> enderecos) {
		this.enderecos = enderecos;
	}
	
	public void add(EnderecoEntity endereco) {
		List<EnderecoEntity> enderecos = 
				Optional.ofNullable(this.getEnderecos()).orElseGet(()->new ArrayList());
		
		enderecos.add(endereco);
		
		this.enderecos = enderecos;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ClienteEntity other = (ClienteEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
