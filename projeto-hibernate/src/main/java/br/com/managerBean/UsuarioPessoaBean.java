package br.com.managerBean;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.xml.bind.DatatypeConverter;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.google.gson.Gson;

import br.com.Dao.DaoEmail;
import br.com.Dao.DaoGeneric;
import br.com.Dao.DaoUsuario;
import br.com.model.EmailUser;
import br.com.model.UsuarioPessoa;

@ManagedBean(name = "usuarioPessoaBean")
@ViewScoped
public class UsuarioPessoaBean {

	private UsuarioPessoa usuarioPessoa = new UsuarioPessoa();
	private List<UsuarioPessoa> list = new ArrayList<UsuarioPessoa>();
	private DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<>();
	private DaoUsuario<UsuarioPessoa> daoUser = new DaoUsuario<>();
	private DaoEmail<EmailUser> daoEmail = new DaoEmail<EmailUser>();
	private BarChartModel barCharModel = new BarChartModel();
	private EmailUser emailUser = new EmailUser();
	private String campoPesquisa;

	/* CRIANDO GRAFICO */
	@PostConstruct
	public void init() {
		barCharModel = new BarChartModel();
		list = daoGeneric.lista(UsuarioPessoa.class);
		ChartSeries userSalarios = new ChartSeries();/* GRUPO DE PESSOA */
		userSalarios.setLabel("Users");
		for (UsuarioPessoa usuarioPessoa : list) {/* ADICIONA O SALARIO PARA O GRUPO */
			userSalarios.set(usuarioPessoa.getNome(), usuarioPessoa.getSalario()); /* ADICIONA O SALARIO */
		}
		barCharModel.addSeries(userSalarios); /* ADICIONAO GRUPO */
		barCharModel.setTitle("Grafico de Salarios");
	}

	/* METEDO SALVAR */
	public String Salvar() {
		daoGeneric.salvar(usuarioPessoa);
		usuarioPessoa = new UsuarioPessoa();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", " Salvo com Sucesso"));
		Novo();
		init();
		return "";
	}
	
	/*PESQUISAR*/
	public void pesquisarNome() {
		list = daoUser.Consulta(campoPesquisa);
	}

	/* Remover */
	public String Remover() {
		DaoUsuario<UsuarioPessoa> dao = new DaoUsuario<UsuarioPessoa>();
		dao.RemoverUsuario(usuarioPessoa);
		usuarioPessoa = new UsuarioPessoa();
		init();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", " Removido com Sucesso"));
		return "";

	}

	/* METEDO NOVO */
	public String Novo() {
		usuarioPessoa = new UsuarioPessoa();
		return "";
	}

	/* METEDO LISTAR TODOS */
	public UsuarioPessoa getUsuarioPessoa() {
		return usuarioPessoa;
	}

	/* pesquisaCep */
	public void pesquisaCep(AjaxBehaviorEvent event) {
		try {
			URL url = new URL("https://viacep.com.br/ws/" + usuarioPessoa.getCep() + "/json/");
			URLConnection connection = url.openConnection();
			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String cep = "";
			StringBuilder jsonCep = new StringBuilder();
			while ((cep = br.readLine()) != null) {
				jsonCep.append(cep);
			}
			UsuarioPessoa userCep = new Gson().fromJson(jsonCep.toString(), UsuarioPessoa.class);
			usuarioPessoa.setCep(userCep.getCep());
			usuarioPessoa.setLogradouro(userCep.getLogradouro());
			usuarioPessoa.setComplemento(userCep.getComplemento());
			usuarioPessoa.setBairro(userCep.getBairro());
			usuarioPessoa.setLocalidade(userCep.getLocalidade());
			usuarioPessoa.setUf(userCep.getUf());
			usuarioPessoa.setIbge(userCep.getIbge());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* addEmail */
	public void addEmail() {
		emailUser.setUsuarioPessoa(usuarioPessoa);
		emailUser = daoEmail.Update(emailUser);
		usuarioPessoa.getEmails().add(emailUser);
		emailUser = new EmailUser();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Detalhe", "Salvo com sucesso"));
	}

	/* removerEmail */
	public void removerEmail() {
		String codigoEmail = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("codigoEmail");
		EmailUser email = new EmailUser();
		email.setId(Long.parseLong(codigoEmail));
		daoEmail.deletarId(email); // aqui deleta do banco
		usuarioPessoa.getEmails().remove(email); // aqui deleta doda menoria do datatable
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Detalhe", "Removido com sucesso"));

	}
	
	/*UPLOAD DE IMAGEM*/
	public void uploadImage(FileUploadEvent image) {
		String imagem = "data:image/png:base64," + DatatypeConverter.printBase64Binary(image.getFile().getContent());
		usuarioPessoa.setImagem(imagem);
	}

	public void setUsuarioPessoa(UsuarioPessoa usuarioPessoa) {
		this.usuarioPessoa = usuarioPessoa;
	}

	public List<UsuarioPessoa> getList() {		
		if(campoPesquisa == null) {
			list = daoGeneric.lista(UsuarioPessoa.class);
		}
		return list;
	}

	public void setList(List<UsuarioPessoa> list) {
		this.list = list;
	}

	public BarChartModel getBarCharModel() {
		return barCharModel;
	}

	public void setBarCharModel(BarChartModel barCharModel) {
		this.barCharModel = barCharModel;
	}

	public EmailUser getEmailUser() {
		return emailUser;
	}

	public void setEmailUser(EmailUser emailUser) {
		this.emailUser = emailUser;
	}

	public String getCampoPesquisa() {
		return campoPesquisa;
	}

	public void setCampoPesquisa(String campoPesquisa) {
		this.campoPesquisa = campoPesquisa;
	}

}
