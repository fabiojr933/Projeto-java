package br.com.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class UsuarioPessoa {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String Nome;
	private String SobreNome;
	private String Login;
	private String senha;
	private int Idade;
	private String sexo;
	private Double salario;
	private String teste;
	private String cep;
	private String logradouro;
	private String complemento;
	private String bairro;
	private String localidade;
	private String uf;
	private String ibge;

	@Column(columnDefinition = "text")
	private String imagem;

	@OneToMany(mappedBy = "usuarioPessoa", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<EmailUser> emails = new ArrayList<EmailUser>();

	@OneToMany(mappedBy = "usuarioPessoa", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<TelefoneUser> telefoneUser;

	public List<TelefoneUser> getTelefoneUser() {
		return telefoneUser;
	}

	public List<EmailUser> getEmails() {
		return emails;
	}

	public void setEmails(List<EmailUser> emails) {
		this.emails = emails;
	}

	public void setTelefoneUser(List<TelefoneUser> telefoneUser) {
		this.telefoneUser = telefoneUser;
	}

	public int getIdade() {
		return Idade;
	}

	public void setIdade(int idade) {
		Idade = idade;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return Nome;
	}

	public void setNome(String nome) {
		Nome = nome;
	}

	public String getSobreNome() {
		return SobreNome;
	}

	public void setSobreNome(String sobreNome) {
		SobreNome = sobreNome;
	}

	public String getLogin() {
		return Login;
	}

	public void setLogin(String login) {
		Login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getTeste() {
		return teste;
	}

	public void setTeste(String teste) {
		this.teste = teste;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getIbge() {
		return ibge;
	}

	public void setIbge(String ibge) {
		this.ibge = ibge;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	@Override
	public String toString() {
		return "UsuarioPessoa [id=" + id + ", Nome=" + Nome + ", SobreNome=" + SobreNome + ", Email=" + ", Login="
				+ Login + ", senha=" + senha + ", Idade=" + Idade + ", teste=" + teste + "]";
	}

}
