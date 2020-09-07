package br.com.Dao;

import java.util.List;

import javax.persistence.Query;

import br.com.model.UsuarioPessoa;

public class DaoUsuario<E> extends DaoGeneric<E>{

	public void RemoverUsuario(UsuarioPessoa pessoa) {
		try {
			getEntityManager().getTransaction().begin();
			String sqldeletefone = "delete from telefoneuser where usuariopessoa_id = " + pessoa.getId();			
			getEntityManager().createNativeQuery(sqldeletefone).executeUpdate();
			
			String sqldeleteemail = "delete from emailuser where usuariopessoa_id = " + pessoa.getId();
			getEntityManager().createNativeQuery(sqldeleteemail).executeUpdate();
			
			getEntityManager().getTransaction().commit();
			
			super.deletarId2(pessoa);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void RemoverUsuario2(UsuarioPessoa pessoa) {
		try {
			getEntityManager().getTransaction().begin();
			getEntityManager().remove(pessoa);  /*ISSO SO FUNIONAR SE TIVER ASSIM MAS ENTIDADES cascade = CascadeType.REMOVE, orphanRemoval = true*/
			getEntityManager().getTransaction().commit();			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public List<UsuarioPessoa> Consulta(String campoPesquisa) {
		Query query = super.getEntityManager().createQuery("from UsuarioPessoa where nome like '%" + campoPesquisa + "%' ");
		return query.getResultList();
	}
}
