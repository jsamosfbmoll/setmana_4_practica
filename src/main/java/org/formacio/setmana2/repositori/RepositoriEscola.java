package org.formacio.setmana2.repositori;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.formacio.setmana2.domini.Alumne;
import org.formacio.setmana2.domini.Curs;
import org.formacio.setmana2.domini.Matricula;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Modifica aquesta classe per tal que sigui un component Spring que realitza les 
 * operacions de persistencia tal com indiquen les firmes dels metodes
 */

@Repository
public class RepositoriEscola {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly = true)
	public Curs carregaCurs(String nom) {
		return em.find(Curs.class, nom);
	}
	
	@Transactional(readOnly = true)
	public Alumne carregaAlumne(String nom) {
		return em.find(Alumne.class, nom);
	}
	
	@Transactional
	public Matricula apunta (String alumne, String curs) throws EdatIncorrecteException {
		Alumne alumneCarregat = this.carregaAlumne(alumne);
		Curs cursCarregat = this.carregaCurs(curs);
		if (alumneCarregat.getEdat() < cursCarregat.getEdatMinima()) throw new EdatIncorrecteException();
		
		Matricula matricula = new Matricula();
		matricula.setAlumne(alumneCarregat);
		matricula.setCurs(cursCarregat);
		
		em.persist(matricula);
		return matricula;
	}
	
	
}
