/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uml;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;
import uml.exceptions.NonexistentEntityException;
import uml.exceptions.RollbackFailureException;

/**
 *
 * @author jdmar
 */
public class TelefonoJpaController implements Serializable {

    public TelefonoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    
    public TelefonoJpaController() throws NamingException {
        this.emf = Persistence.createEntityManagerFactory("Examen_Final_DAUTEPU");
        this.utx = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
    }
    
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Telefono telefono) throws RollbackFailureException, Exception {
        if (telefono.getDepartamentoList() == null) {
            telefono.setDepartamentoList(new ArrayList<Departamento>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Departamento> attachedDepartamentoList = new ArrayList<Departamento>();
            for (Departamento departamentoListDepartamentoToAttach : telefono.getDepartamentoList()) {
                departamentoListDepartamentoToAttach = em.getReference(departamentoListDepartamentoToAttach.getClass(), departamentoListDepartamentoToAttach.getIdDepartamento());
                attachedDepartamentoList.add(departamentoListDepartamentoToAttach);
            }
            telefono.setDepartamentoList(attachedDepartamentoList);
            em.persist(telefono);
            for (Departamento departamentoListDepartamento : telefono.getDepartamentoList()) {
                Telefono oldCodTelsOfDepartamentoListDepartamento = departamentoListDepartamento.getCodTels();
                departamentoListDepartamento.setCodTels(telefono);
                departamentoListDepartamento = em.merge(departamentoListDepartamento);
                if (oldCodTelsOfDepartamentoListDepartamento != null) {
                    oldCodTelsOfDepartamentoListDepartamento.getDepartamentoList().remove(departamentoListDepartamento);
                    oldCodTelsOfDepartamentoListDepartamento = em.merge(oldCodTelsOfDepartamentoListDepartamento);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Telefono telefono) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Telefono persistentTelefono = em.find(Telefono.class, telefono.getCodTels());
            List<Departamento> departamentoListOld = persistentTelefono.getDepartamentoList();
            List<Departamento> departamentoListNew = telefono.getDepartamentoList();
            List<Departamento> attachedDepartamentoListNew = new ArrayList<Departamento>();
            for (Departamento departamentoListNewDepartamentoToAttach : departamentoListNew) {
                departamentoListNewDepartamentoToAttach = em.getReference(departamentoListNewDepartamentoToAttach.getClass(), departamentoListNewDepartamentoToAttach.getIdDepartamento());
                attachedDepartamentoListNew.add(departamentoListNewDepartamentoToAttach);
            }
            departamentoListNew = attachedDepartamentoListNew;
            telefono.setDepartamentoList(departamentoListNew);
            telefono = em.merge(telefono);
            for (Departamento departamentoListOldDepartamento : departamentoListOld) {
                if (!departamentoListNew.contains(departamentoListOldDepartamento)) {
                    departamentoListOldDepartamento.setCodTels(null);
                    departamentoListOldDepartamento = em.merge(departamentoListOldDepartamento);
                }
            }
            for (Departamento departamentoListNewDepartamento : departamentoListNew) {
                if (!departamentoListOld.contains(departamentoListNewDepartamento)) {
                    Telefono oldCodTelsOfDepartamentoListNewDepartamento = departamentoListNewDepartamento.getCodTels();
                    departamentoListNewDepartamento.setCodTels(telefono);
                    departamentoListNewDepartamento = em.merge(departamentoListNewDepartamento);
                    if (oldCodTelsOfDepartamentoListNewDepartamento != null && !oldCodTelsOfDepartamentoListNewDepartamento.equals(telefono)) {
                        oldCodTelsOfDepartamentoListNewDepartamento.getDepartamentoList().remove(departamentoListNewDepartamento);
                        oldCodTelsOfDepartamentoListNewDepartamento = em.merge(oldCodTelsOfDepartamentoListNewDepartamento);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = telefono.getCodTels();
                if (findTelefono(id) == null) {
                    throw new NonexistentEntityException("The telefono with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Telefono telefono;
            try {
                telefono = em.getReference(Telefono.class, id);
                telefono.getCodTels();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The telefono with id " + id + " no longer exists.", enfe);
            }
            List<Departamento> departamentoList = telefono.getDepartamentoList();
            for (Departamento departamentoListDepartamento : departamentoList) {
                departamentoListDepartamento.setCodTels(null);
                departamentoListDepartamento = em.merge(departamentoListDepartamento);
            }
            em.remove(telefono);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Telefono> findTelefonoEntities() {
        return findTelefonoEntities(true, -1, -1);
    }

    public List<Telefono> findTelefonoEntities(int maxResults, int firstResult) {
        return findTelefonoEntities(false, maxResults, firstResult);
    }

    private List<Telefono> findTelefonoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Telefono.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Telefono findTelefono(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Telefono.class, id);
        } finally {
            em.close();
        }
    }

    public int getTelefonoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Telefono> rt = cq.from(Telefono.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
