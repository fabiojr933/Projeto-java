package br.com.projetohibernate;

import java.util.List;

import org.junit.Test;

import br.com.Dao.DaoGeneric;
import br.com.model.TelefoneUser;
import br.com.model.UsuarioPessoa;

public class Teste {

	@Test
	public void Salvar() {

		// Metedo dao generico para salvar no banco de dados
		DaoGeneric<UsuarioPessoa> daoPessoa = new DaoGeneric<UsuarioPessoa>();
		UsuarioPessoa pessoa = new UsuarioPessoa();
		pessoa.setIdade(13);
		pessoa.setLogin("fabiojr");
		pessoa.setNome("fabio pereira");
		pessoa.setSenha("1022");
		pessoa.setSobreNome("da silva");
		pessoa.setTeste("teste 123");
		daoPessoa.salvar(pessoa);

	}

	@Test
	public void Consultar() {
		DaoGeneric<UsuarioPessoa> daoGneric = new DaoGeneric<UsuarioPessoa>();
		UsuarioPessoa pessoa = new UsuarioPessoa();
		pessoa.setId(1L);
		pessoa = daoGneric.Consulta(pessoa);
		System.out.println(pessoa);
	}

	@Test
	public void Update() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		UsuarioPessoa pessoa = daoGeneric.Consulta2(1L, UsuarioPessoa.class);
		pessoa.setNome("fabio alterado");
		pessoa.setIdade(100);
		pessoa = daoGeneric.Update(pessoa);
		System.out.println(pessoa);
	}

	@Test
	public void delete() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		UsuarioPessoa pessoa = daoGeneric.Consulta2(2L, UsuarioPessoa.class);
		daoGeneric.deletarId(pessoa);
	}

	@Test
	public void ListaUsuario() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list = daoGeneric.lista(UsuarioPessoa.class);
		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
			System.out.println("---------------");
		}

	}

	@SuppressWarnings("unchecked")
	@Test
	public void TesteQuery() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list = daoGeneric.getEntityManager().createQuery("from UsuarioPessoa where nome ='fabio'")
				.getResultList();
		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
		}
	}

	// GENERICO PASSADO PARAMETROS
	@Test
	@SuppressWarnings("unchecked")
	public void TesteQueryParamentro() {
		DaoGeneric<UsuarioPessoa> daoGenerico = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> lista = daoGenerico.getEntityManager().createQuery("from UsuarioPessoa where nome = :nome")
				.setParameter("nome", "fabio").getResultList();

		for (UsuarioPessoa usuarioPessoa : lista) {
			System.out.println(usuarioPessoa);
		}
	}

	// Resultando a media de todas as idades
	@Test
	public void testeSomaIdade() {
		DaoGeneric<UsuarioPessoa> daoGenerico = new DaoGeneric<UsuarioPessoa>();
		Long somaIdade = (Long) daoGenerico.getEntityManager().createQuery("select sum(u.Idade) from UsuarioPessoa u ")
				.getSingleResult();
		System.out.println("a soma de todas as idade é ==> " + somaIdade);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void TesteGravaTelefone() {
		DaoGeneric daoGeneric = new DaoGeneric();
		UsuarioPessoa pessoa = (UsuarioPessoa) daoGeneric.Consulta2(1L, UsuarioPessoa.class);

		TelefoneUser tel = new TelefoneUser();
		tel.setTelfone("99539490");
		tel.setTipo("celular");
		tel.setUsuarioPessoa(pessoa);

		daoGeneric.salvar(tel);
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void ConsultaTel() {
		DaoGeneric dao = new DaoGeneric();
		@SuppressWarnings("unchecked")
		UsuarioPessoa pessoa = (UsuarioPessoa) dao.Consulta2(1L, UsuarioPessoa.class);

		for (TelefoneUser fone : pessoa.getTelefoneUser()) {
			System.out.println(fone.getUsuarioPessoa().getNome());
			System.out.println(fone.getTelfone());
			System.out.println(fone.getTipo());
			System.out.println(fone.getId());
			System.out.println();
		}
	}

}
