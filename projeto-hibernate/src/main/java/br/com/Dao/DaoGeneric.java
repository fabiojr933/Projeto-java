package br.com.Dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.model.UsuarioPessoa;
import br.com.projetohibernate.HibernateUtil;
import net.bytebuddy.implementation.bind.annotation.Super;

public class DaoGeneric<E> {

	private EntityManager entityManager = HibernateUtil.getEntityManager();

	// GENERICO SALVAR
	public void salvar(E entidade) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(entidade);
		transaction.commit();
	}

	// GENERICO CONSULTAR
	@SuppressWarnings("unchecked")
	public E Consulta(E entidade) {
		Object id = HibernateUtil.getPrimaryKey(entidade);
		E e = (E) entityManager.find(entidade.getClass(), id);
		return e;
	}

	// GENERICO CONSULTAR2
	public E Consulta3(Long id, Class<E> entidade) {
		E e = (E) entityManager.find(entidade, id);
		return e;
	}
	// GENERICO CONSULTAR3
	@SuppressWarnings("unchecked")
	public E Consulta2(Long id, Class<E> entidade) {
		entityManager.clear();
		E e = (E) entityManager.createQuery("from " + entidade.getName() + " where id = " + id).getSingleResult();
		return e;
	}


	// GENERICO SALVAR OU ATUALIZAR
	public E Update(E entidade) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		E update = entityManager.merge(entidade);
		transaction.commit();
		return update;
	}

	// GENERICO DELETAR POR ID
	public void deletarId(E entidade) {
		Object id = HibernateUtil.getPrimaryKey(entidade);
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager
				.createNativeQuery(
						"delete from " + entidade.getClass().getSimpleName().toLowerCase() + " where id = " + id)
				.executeUpdate();
		transaction.commit();

	}
	
	// GENERICO DELETAR POR ID
	public void deletarId2(UsuarioPessoa pessoa) {
		Object id = HibernateUtil.getPrimaryKey(pessoa);
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager
				.createNativeQuery(
						"delete from " + pessoa.getClass().getSimpleName().toLowerCase() + " where id = " + id)
				.executeUpdate();
		transaction.commit();

	}


	// GENERICO CONSULTAR TODOS
	@SuppressWarnings("unchecked")
	public List<E> lista(Class<E> entidade) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		List<E> lista = entityManager.createQuery("from " + entidade.getName()).getResultList();
		transaction.commit();
		return lista;
	}
	
	
	//GENERICO PASSADO PARAMETROS
	public void TesteQueryParamentro() {
		
	}
	
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	


}
