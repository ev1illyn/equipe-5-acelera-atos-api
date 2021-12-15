package net.atos.api.cliente.domain;

import javax.validation.constraints.NotNull;

public class EnderecoVO {
	
	private Long id;
	
	@NotNull(message = "Campo rua não pode ser nulo")
	private String rua;

	@NotNull(message = "Campo número não pode ser nulo")
	private String numero;

	@NotNull(message = "Campo bairro não pode ser nulo")
	private String bairro;

	@NotNull(message = "Campo cidade não pode ser nulo")
	private String cidade;

	@NotNull(message = "Campo UF não pode ser nulo")
	private String UF;

	@NotNull(message = "Campo país não pode ser nulo")
	private String pais;

	@NotNull(message = "Campo cep não pode ser nulo")
	private Integer cep;

	@NotNull(message = "Campo telefone fixo não pode ser nulo")
	private Integer telefone_fixo;

	@NotNull(message = "Campo tipo Endereco não pode ser nulo")
	private TipoEndereco tipoEndereco;
	//private TipoEndereco tipoEndereco = TipoEndereco.COMERCIAL;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUF() {
		return UF;
	}

	public void setUF(String uF) {
		UF = uF;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public Integer getCep() {
		return cep;
	}

	public void setCep(Integer cep) {
		this.cep = cep;
	}

	public Integer getTelefone_fixo() {
		return telefone_fixo;
	}

	public void setTelefone_fixo(Integer telefone_fixo) {
		this.telefone_fixo = telefone_fixo;
	}

	public TipoEndereco getTipoEndereco() {
		return tipoEndereco;
	}

	public void setTipoEndereco(TipoEndereco tipoEndereco) {
		this.tipoEndereco = tipoEndereco;
	}
	
}
