package net.atos.api.cliente.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ClienteVO {

	private Long id;
	
	@NotNull(message = "Campo nome não pode ser nulo")
	private String nome;
	
	@NotNull(message = "Campo cpf não pode ser nulo")
	//@CPF(message = "CPF inválido")
	private String cpf;
	
	@NotNull(message = "Campo rg não pode ser nulo")
	private String rg;
	
	@NotNull(message = "Campo nascimento não pode ser nulo")
	private LocalDate nascimento;
	
	@NotNull(message = "Campo email não pode ser nulo")
	private String email;
	
	@NotNull(message = "Campo ativo não pode ser nulo")
	private Boolean ativo;
	
	@NotNull(message = "Campo celular não pode ser nulo")
	private Long celular;
	
	@NotNull(message = "Campo endereço não pode ser nulo")
	@Size(min = 1, message = "Campo endereço deve ter pelo menos um item")
	@Valid
	private List<EnderecoVO> enderecos;
	
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
	public Boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	public Long getCelular() {
		return celular;
	}
	public void setCelular(Long celular) {
		this.celular = celular;
	}
	public List<EnderecoVO> getEnderecos() {
		return enderecos;
	}
	public void setEnderecos(List<EnderecoVO> enderecos) {
		this.enderecos = enderecos;
	}
	
	public void add(EnderecoVO endereco) {
		List<EnderecoVO> enderecos = 
				Optional.ofNullable(this.getEnderecos()).orElseGet(()->new ArrayList());
		
		enderecos.add(endereco);
		
		this.enderecos = enderecos;
	}
	@Override
	public String toString() {
		return "ClienteVO [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", rg=" + rg + ", nascimento=" + nascimento
				+ ", email=" + email + ", ativo=" + ativo + ", celular=" + celular + ", enderecos=" + enderecos + "]";
	}
	
}
