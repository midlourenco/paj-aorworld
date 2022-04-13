package pt.uc.dei.proj5.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

@TransactionAttribute(TransactionAttributeType.REQUIRED)
public abstract class AbstractDao<T extends Serializable> implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Class<T> clazz;

	@PersistenceContext(unitName = "persistBackendProj5") // nome do projecto em si. nome da aplicaçao
	protected EntityManager em;

	public AbstractDao(Class<T> clazz) {
		this.clazz = clazz;
	}

	public T find(Object id) {
		return em.find(clazz, id);
	}

	public void persist(final T entity) {
		System.out.println("entrei no persist e trouxe: " + entity);
		em.persist(entity);
	}

	public void merge(final T entity) {
		em.merge(entity);
	}

	public void delete(final T entity) {

		em.remove(em.contains(entity) ? entity : em.merge(entity));

	}

	public void deleteById(final Object entityId) {
		final T entity = find(entityId);
		delete(entity);
	}

	public List<T> findAll() {
		final CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(clazz);
		criteriaQuery.select(criteriaQuery.from(clazz));
		return em.createQuery(criteriaQuery).getResultList();
	}

	public List<T> findAllNonDeleted() {

		final CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(clazz);

		Root<T> c = criteriaQuery.from(clazz);

		criteriaQuery.select(c).where(em.getCriteriaBuilder().equal(c.get("deleted"), false));

		return em.createQuery(criteriaQuery).getResultList();
	}

	public List<T> findAllMarkedAsDeleted() {

		final CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(clazz);

		Root<T> c = criteriaQuery.from(clazz);

		criteriaQuery.select(c).where(em.getCriteriaBuilder().equal(c.get("deleted"), true));

		return em.createQuery(criteriaQuery).getResultList();
	}

	public void deleteAll() {

		final CriteriaDelete<T> criteriaDelete = em.getCriteriaBuilder().createCriteriaDelete(clazz);
		criteriaDelete.from(clazz);
		em.createQuery(criteriaDelete).executeUpdate();
	}

//	public void searchValueByCol(String colName, String value) {
//		final CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(clazz);
//		Root<T> c= criteriaQuery.from(clazz);
//		criteriaQuery.select(c).where(em.getCriteriaBuilder().like(c.get(colName),value));
//	}
//	
//	public void updateValueByCol(String colName, String value) {
//		final CriteriaUpdate<T> criteriaUpdate = em.getCriteriaBuilder().createCriteriaUpdate(clazz);
//		Root<T> c= criteriaUpdate.from(clazz);
//		criteriaUpdate.set(colName,value);
//	//	criteriaUpdate.where(em.getCriteriaBuilder.equal);
//	}

	public String getIdColumnName() {
		return "id"; // porque o id é o mais comum. -> este metodo deve ser implementado override
						// onde nao for id
	}

	public T findEntityIfNonDelete(Object id) {

		try {

			final CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(clazz);

			Root<T> c = criteriaQuery.from(clazz);

			criteriaQuery.select(c)
					.where(em.getCriteriaBuilder().and(em.getCriteriaBuilder().equal(c.get(getIdColumnName()), id),
							em.getCriteriaBuilder().equal(c.get("deleted"), false)));

			return em.createQuery(criteriaQuery).getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public T findEntity(Object id) {

		try {

			final CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(clazz);

			Root<T> c = criteriaQuery.from(clazz);

			criteriaQuery.select(c)
					.where(em.getCriteriaBuilder().and(em.getCriteriaBuilder().equal(c.get(getIdColumnName()), id)
							));

			return em.createQuery(criteriaQuery).getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
//	public T findEntityIfDelete(Object id) {
//		try {
//		final CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(clazz);
//		Root<T> c= criteriaQuery.from(clazz);
//		criteriaQuery.select(c).where(em.getCriteriaBuilder().and(
//				em.getCriteriaBuilder().equal(c.get(getIdColumnName()),id),
//				em.getCriteriaBuilder().equal(c.get("deleted"),true)));
//		return em.createQuery(criteriaQuery).getSingleResult();
//		}catch(Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}

	public void markedAsDeleted(Object id) {
		try {
			System.out.println("A coluna ID deste objecto é: " + getIdColumnName());
			System.out.println("o id é :" + id);

			final CriteriaUpdate<T> criteriaUpdate = em.getCriteriaBuilder().createCriteriaUpdate(clazz);

			Root<T> c = criteriaUpdate.from(clazz);

			criteriaUpdate.set("deleted", true);
			criteriaUpdate.where(em.getCriteriaBuilder().equal(c.get(getIdColumnName()), id));

			em.createQuery(criteriaUpdate).executeUpdate();

		} catch (EJBException e) {
			e.printStackTrace();
		}

	}

	public void markedAsNonDeleted(Object id) {
		try {
			System.out.println("A coluna ID deste objecto é: " + getIdColumnName());
			System.out.println("o id é :" + id);

			final CriteriaUpdate<T> criteriaUpdate = em.getCriteriaBuilder().createCriteriaUpdate(clazz);
			Root<T> c = criteriaUpdate.from(clazz);
			criteriaUpdate.set("deleted", false);
			criteriaUpdate.where(em.getCriteriaBuilder().equal(c.get(getIdColumnName()), id));
			em.createQuery(criteriaUpdate).executeUpdate();

		} catch (EJBException e) {
			e.printStackTrace();
		}

	}

}
