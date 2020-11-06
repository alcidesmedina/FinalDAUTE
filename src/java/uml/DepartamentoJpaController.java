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
 * @author andie
 */
public class DepartamentoJpaController implements Serializable {

    public DepartamentoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    
    public DepartamentoJpaController() throws NamingException {
        this.emf = Persistence.createEntityManagerFactory("Examen_Final_DAUTEPU");
        this.utx = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
    }
    
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Departamento departamento) throws RollbackFailureException, Exception {
        if (departamento.getEmpleadoList() == null) {
            departamento.setEmpleadoList(new ArrayList<Empleado>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Telefono codTels = departamento.getCodTels();
            if (codTels != null) {
                codTels = em.getReference(codTels.getClass(), codTels.getCodTels());
                departamento.setCodTels(codTels);
            }
            List<Empleado> attachedEmpleadoList = new ArrayList<Empleado>();
            for (Empleado empleadoListEmpleadoToAttach : departamento.getEmpleadoList()) {
                empleadoListEmpleadoToAttach = em.getReference(empleadoListEmpleadoToAttach.getClass(), empleadoListEmpleadoToAttach.getCodEmpleado());
                attachedEmpleadoList.add(empleadoListEmpleadoToAttach);
            }
            departamento.setEmpleadoList(attachedEmpleadoList);
            em.persist(departamento);
            if (codTels != null) {
                codTels.getDepartamentoList().add(departamento);
                codTels = em.merge(codTels);
            }
            for (Empleado empleadoListEmpleado : departamento.getEmpleadoList()) {
                Departamento oldIdDepartamentoOfEmpleadoListEmpleado = empleadoListEmpleado.getIdDepartamento();
                empleadoListEmpleado.setIdDepartamento(departamento);
                empleadoListEmpleado = em.merge(empleadoListEmpleado);
                if (oldIdDepartamentoOfEmpleadoListEmpleado != null) {
                    oldIdDepartamentoOfEmpleadoListEmpleado.getEmpleadoList().remove(empleadoListEmpleado);
                    oldIdDepartamentoOfEmpleadoListEmpleado = em.merge(oldIdDepartamentoOfEmpleadoListEmpleado);
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

    public void edit(Departamento departamento) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Departamento persistentDepartamento = em.find(Departamento.class, departamento.getIdDepartamento());
            Telefono codTelsOld = persistentDepartamento.getCodTels();
            Telefono codTelsNew = departamento.getCodTels();
            List<Empleado> empleadoListOld = persistentDepartamento.getEmpleadoList();
            List<Empleado> empleadoListNew = departamento.getEmpleadoList();
            if (codTelsNew != null) {
                codTelsNew = em.getReference(codTelsNew.getClass(), codTelsNew.getCodTels());
                departamento.setCodTels(codTelsNew);
            }
            List<Empleado> attachedEmpleadoListNew = new ArrayList<Empleado>();
            for (Empleado empleadoListNewEmpleadoToAttach : empleadoListNew) {
                empleadoListNewEmpleadoToAttach = em.getReference(empleadoListNewEmpleadoToAttach.getClass(), empleadoListNewEmpleadoToAttach.getCodEmpleado());
                attachedEmpleadoListNew.add(empleadoListNewEmpleadoToAttach);
            }
            empleadoListNew = attachedEmpleadoListNew;
            departamento.setEmpleadoList(empleadoListNew);
            departamento = em.merge(departamento);
            if (codTelsOld != null && !codTelsOld.equals(codTelsNew)) {
                codTelsOld.getDepartamentoList().remove(departamento);
                codTelsOld = em.merge(codTelsOld);
            }
            if (codTelsNew != null && !codTelsNew.equals(codTelsOld)) {
                codTelsNew.getDepartamentoList().add(departamento);
                codTelsNew = em.merge(codTelsNew);
            }
            for (Empleado empleadoListOldEmpleado : empleadoListOld) {
                if (!empleadoListNew.contains(empleadoListOldEmpleado)) {
                    empleadoListOldEmpleado.setIdDepartamento(null);
                    empleadoListOldEmpleado = em.merge(empleadoListOldEmpleado);
                }
            }
            for (Empleado empleadoListNewEmpleado : empleadoListNew) {
                if (!empleadoListOld.contains(empleadoListNewEmpleado)) {
                    Departamento oldIdDepartamentoOfEmpleadoListNewEmpleado = empleadoListNewEmpleado.getIdDepartamento();
                    empleadoListNewEmpleado.setIdDepartamento(departamento);
                    empleadoListNewEmpleado = em.merge(empleadoListNewEmpleado);
                    if (oldIdDepartamentoOfEmpleadoListNewEmpleado != null && !oldIdDepartamentoOfEmpleadoListNewEmpleado.equals(departamento)) {
                        oldIdDepartamentoOfEmpleadoListNewEmpleado.getEmpleadoList().remove(empleadoListNewEmpleado);
                        oldIdDepartamentoOfEmpleadoListNewEmpleado = em.merge(oldIdDepartamentoOfEmpleadoListNewEmpleado);
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
                Integer id = departamento.getIdDepartamento();
                if (findDepartamento(id) == null) {
                    throw new NonexistentEntityException("The departamento with id " + id + " no longer exists.");
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
            Departamento departamento;
            try {
                departamento = em.getReference(Departamento.class, id);
                departamento.getIdDepartamento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The departamento with id " + id + " no longer exists.", enfe);
            }
            Telefono codTels = departamento.getCodTels();
            if (codTels != null) {
                codTels.getDepartamentoList().remove(departamento);
                codTels = em.merge(codTels);
            }
            List<Empleado> empleadoList = departamento.getEmpleadoList();
            for (Empleado empleadoListEmpleado : empleadoList) {
                empleadoListEmpleado.setIdDepartamento(null);
                empleadoListEmpleado = em.merge(empleadoListEmpleado);
            }
            em.remove(departamento);
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

    public List<Departamento> findDepartamentoEntities() {
        return findDepartamentoEntities(true, -1, -1);
    }

    public List<Departamento> findDepartamentoEntities(int maxResults, int firstResult) {
        return findDepartamentoEntities(false, maxResults, firstResult);
    }

    private List<Departamento> findDepartamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Departamento.class));
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

    public Departamento findDepartamento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Departamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getDepartamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Departamento> rt = cq.from(Departamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
