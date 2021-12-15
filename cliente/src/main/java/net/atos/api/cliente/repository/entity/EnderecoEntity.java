package net.atos.api.cliente.repository.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import net.atos.api.cliente.domain.TipoEndereco;

@Entity
@Table(name = "TB_CLIENTE_ENDERECO")
public class EnderecoEntity implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EnderecoPK id;

	@Column(name = "RUA_ENDERECO")
	@NotNull(message = "Campo rua não pode ser nulo")
	private String rua;

	@Column(name = "NUMERO_ENDERECO")
	@NotNull(message = "Campo número não pode ser nulo")
	private String numero;

	@Column(name = "BAIRRO_ENDERECO")
	@NotNull(message = "Campo bairro não pode ser nulo")
	private String bairro;

	@Column(name = "CIDADE_ENDERECO")
	@NotNull(message = "Campo cidade não pode ser nulo")
	private String cidade;

	@Column(name = "UF_ENDERECO")
	@NotNull(message = "Campo UF não pode ser nulo")
	private String UF;

	@Column(name = "PAIS_ENDERECO")
	@NotNull(message = "Campo país não pode ser nulo")
	private String pais;

	@Column(name = "CEP_ENDERECO")
	@NotNull(message = "Campo cep não pode ser nulo")
	private Integer cep;

	@Column(name = "TELEFONE_FIXO_ENDERECO")
	@NotNull(message = "Campo telefone fixo não pode ser nulo")
	private Integer telefone_fixo;

	@Column(name = "TIPO_ENDERECO_ENDERECO")
	@NotNull(message = "Campo tipo Endereco não pode ser nulo")
	@Enumerated(EnumType.STRING)
	private TipoEndereco tipoEndereco;
	//private TipoEndereco tipoEndereco = TipoEndereco.COMERCIAL;

	public String getRua() {
		return rua;
	}

	public EnderecoPK getId() {
		return id;
	}

	public void setId(EnderecoPK id) {
		this.id = id;
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
