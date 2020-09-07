package br.com.managerBean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.Dao.DaoGeneric;
import br.com.Dao.DaoTelefone;
import br.com.model.TelefoneUser;
import br.com.model.UsuarioPessoa;

@ManagedBean(name = "telefoneBean")
@ViewScoped
public class TelefoneBean {

	private DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<>();
	private TelefoneUser telefone = new TelefoneUser();
	private UsuarioPessoa pessoa = new UsuarioPessoa();
	private DaoTelefone<TelefoneUser> daoTelefone = new DaoTelefone<TelefoneUser>();
	

	/* TROCA DE INFORMAÇÃO ENTRE TELAS */
	/* ESTA PEGANDO O USUARIO SELECIONANDO */
	@PostConstruct
	public void init() {
		String codigoUser = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("codigouser");
		pessoa = daoGeneric.Consulta2(Long.parseLong(codigoUser), UsuarioPessoa.class);
	}

	/* METEDO SALVAR */
	public String Salvar() {
		telefone.setUsuarioPessoa(pessoa);
		daoTelefone.salvar(telefone);
		telefone = new TelefoneUser();
		pessoa = daoGeneric.Consulta2(pessoa.getId(), UsuarioPessoa.class); /*RECARREGAR OS TELEFONES DO BANCO*/
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", "Salvo com Sucesso"));
		return "";
	}

	/* METEDO NOVO */
	public String Novo() {		
		pessoa = daoGeneric.Consulta2(pessoa.getId(), UsuarioPessoa.class); /*RECARREGAR OS TELEFONES DO BANCO*/
		telefone = new TelefoneUser();
		return "";
	}

	/* Remover */
	public String Remover() {
		daoTelefone.deletarId(telefone);
		pessoa = daoGeneric.Consulta2(pessoa.getId(), UsuarioPessoa.class); /*RECARREGAR OS TELEFONES DO BANCO*/
		telefone = new TelefoneUser();		
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", "Removido com Sucesso"));		
		return "";
	}

	public TelefoneUser getTelefone() {
		return telefone;
	}

	public void setTelefone(TelefoneUser telefone) {
		this.telefone = telefone;
	}

	public UsuarioPessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(UsuarioPessoa pessoa) {
		this.pessoa = pessoa;
	}

	public DaoTelefone<TelefoneUser> getDaoTelefone() {
		return daoTelefone;
	}

	public void setDaoTelefone(DaoTelefone<TelefoneUser> daoTelefone) {
		this.daoTelefone = daoTelefone;
	}

	
}
