package ch.gibm.facade;

import java.io.Serializable;
import java.util.List;


import ch.gibm.dao.EntityManagerHelper;
import ch.gibm.dao.FavoriteColorDAO;
import ch.gibm.dao.LanguageDAO;
import ch.gibm.dao.PersonDAO;
import ch.gibm.entity.FavoriteColor;
import ch.gibm.entity.Language;
import ch.gibm.entity.Person;

public class PersonFacade implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private PersonDAO personDAO = new PersonDAO();
	private LanguageDAO languageDAO = new LanguageDAO();
	private FavoriteColorDAO favoriteColorDAO = new FavoriteColorDAO();

	public void createPerson(Person person) {
		EntityManagerHelper.beginTransaction();
		personDAO.save(person);
		EntityManagerHelper.commitAndCloseTransaction();
	}

	public void updatePerson(Person person) {
		EntityManagerHelper.beginTransaction();
		personDAO.update(person);
		//Person persistedPerson = personDAO.find(person.getId());
		//persistedPerson.setName(person.getName());
		EntityManagerHelper.commitAndCloseTransaction();
	}
	
	public void deletePerson(Person person){
		EntityManagerHelper.beginTransaction();
		Person persistedPersonWithIdOnly = personDAO.findReferenceOnly(person.getId());
		personDAO.delete(persistedPersonWithIdOnly);
		EntityManagerHelper.commitAndCloseTransaction();
		
	}

	public Person findPerson(int personId) {
		EntityManagerHelper.beginTransaction();
		Person person = personDAO.find(personId);
		EntityManagerHelper.commitAndCloseTransaction();
		return person;
	}

	public List<Person> listAll() {
		EntityManagerHelper.beginTransaction();
		List<Person> result = personDAO.findAll();
		EntityManagerHelper.commitAndCloseTransaction();

		return result;
	}

	public Person findPersonWithAllLanguages(int personId) {
		EntityManagerHelper.beginTransaction();
		Person person = personDAO.findPersonWithAllLanguages(personId);
		EntityManagerHelper.commitAndCloseTransaction();
		return person;
	}

	public void addLanguageToPerson(int languageId, int personId) {
		EntityManagerHelper.beginTransaction();
		Language language = languageDAO.find(languageId);
		Person person = personDAO.find(personId);
		person.getLanguages().add(language);
		language.getPersons().add(person);
		EntityManagerHelper.commitAndCloseTransaction();
	}

	public void removeLanguageFromPerson(int languageId, int personId) {
		EntityManagerHelper.beginTransaction();
		Language language = languageDAO.find(languageId);
		Person person = personDAO.find(personId);
		person.getLanguages().remove(language);
		language.getPersons().remove(person);
		EntityManagerHelper.commitAndCloseTransaction();
	}

	public Person findPersonWithAllFavoriteColors(int personId) {
		EntityManagerHelper.beginTransaction();
		Person person = personDAO.findPersonWithAllFavoriteColors(personId);
		EntityManagerHelper.commitAndCloseTransaction();
		return person;
	}

	public void addFavoriteColorToPerson(int favoriteColorId, int personId) {
		EntityManagerHelper.beginTransaction();
		FavoriteColor favoriteColor = favoriteColorDAO.find(favoriteColorId);
		Person person = personDAO.find(personId);
		person.getFavoriteColors().add(favoriteColor);
		favoriteColor.getPersons().add(person);
		EntityManagerHelper.commitAndCloseTransaction();
	}

	public void removeFavoriteColorFromPerson(int favoriteColorId, int personId) {
		EntityManagerHelper.beginTransaction();
		FavoriteColor favoriteColor = favoriteColorDAO.find(favoriteColorId);
		Person person = personDAO.find(personId);
		person.getFavoriteColors().remove(favoriteColor);
		favoriteColor.getPersons().remove(person);
		EntityManagerHelper.commitAndCloseTransaction();
	}
}