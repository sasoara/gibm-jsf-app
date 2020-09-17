package ch.gibm.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import ch.gibm.entity.FavoriteColor;
import com.sun.faces.context.flash.ELFlash;

import ch.gibm.entity.Language;
import ch.gibm.entity.Person;
import ch.gibm.facade.PersonFacade;

@ViewScoped
@ManagedBean
public class PersonBean extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String SELECTED_PERSON = "selectedPerson";

	private Language language;
	private FavoriteColor favoriteColor;
	private Person personWithFavoriteColors;
	private Person personWithFavoriteColorsForDetail;
	private Person person;
	private Person personWithLanguages;
	private Person personWithLanguagesForDetail;

	@ManagedProperty(value="#{languageBean}")
	private LanguageBean languageBean;

	@ManagedProperty(value="#{favoriteColorBean}")
	private FavoriteColorBean favoriteColorBean;
	

	private List<Person> persons;
	private PersonFacade personFacade;

	/*
	public PersonBean() {
	super();	
	} */
	
	public void createPerson() {
		try {
			getPersonFacade().createPerson(person);
			closeDialog();
			displayInfoMessageToUser("Created with success");
			loadPersons();
			resetPerson();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("A problem occurred while saving. Try again later");
			e.printStackTrace();
		}
	}

	public void updatePerson() {
		try {
			getPersonFacade().updatePerson(person);
			closeDialog();
			displayInfoMessageToUser("Updated with success");
			loadPersons();
			resetPerson();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("A problem occurred while updating. Try again later");
			e.printStackTrace();
		}
	}

	public void deletePerson() {
		try {
			getPersonFacade().deletePerson(person);
			closeDialog();
			displayInfoMessageToUser("Deleted with success");
			loadPersons();
			resetPerson();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("A problem occurred while removing. Try again later");
			e.printStackTrace();
		}
	}

	public void addLanguageToPerson() {
		try {
			getPersonFacade().addLanguageToPerson(language.getId(), personWithLanguages.getId());
			closeDialog();
			displayInfoMessageToUser("Added with success");
			reloadPersonWithLanguages();
			resetLanguage();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("A problem occurred while saving. Try again later");
			e.printStackTrace();
		}
	}

	public void removeLanguageFromPerson() {
		try {
			getPersonFacade().removeLanguageFromPerson(language.getId(), personWithLanguages.getId());
			closeDialog();
			displayInfoMessageToUser("Removed with success");
			reloadPersonWithLanguages();
			resetLanguage();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("A problem occurred while removing. Try again later");
			e.printStackTrace();
		}
	}

	public Person getPersonWithLanguages() {
		if (personWithLanguages == null) {
			person = (Person) ELFlash.getFlash().get(SELECTED_PERSON);
			personWithLanguages = getPersonFacade().findPersonWithAllLanguages(person.getId());
		}

		return personWithLanguages;
	}

	public void setPersonWithLanguagesForDetail(Person person) {
		personWithLanguagesForDetail = getPersonFacade().findPersonWithAllLanguages(person.getId());
	}

	public Person getPersonWithLanguagesForDetail() {
		if (personWithLanguagesForDetail == null) {
			personWithLanguagesForDetail = new Person();
			personWithLanguagesForDetail.setLanguages(new ArrayList<Language>());
		}

		return personWithLanguagesForDetail;
	}

	public void resetPersonWithLanguagesForDetail() {
		personWithLanguagesForDetail = new Person();
	}

	public String editPersonLanguages() {
		ELFlash.getFlash().put(SELECTED_PERSON, person);
		return "/pages/public/person/personLanguages/personLanguages.xhtml";
	}

	public void addFavoriteColorToPerson() {
		try {
			getPersonFacade().addFavoriteColorToPerson(favoriteColor.getId(), personWithFavoriteColors.getId());
			closeDialog();
			displayInfoMessageToUser("Added with success");
			reloadPersonWithFavoriteColors();
			resetFavoriteColor();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("A problem occurred while saving. Try again later");
			e.printStackTrace();
		}
	}

	public void removeFavoriteColorFromPerson() {
		try {
			getPersonFacade().removeFavoriteColorFromPerson(favoriteColor.getId(), personWithFavoriteColors.getId());
			closeDialog();
			displayInfoMessageToUser("Removed with success");
			reloadPersonWithFavoriteColors();
			resetFavoriteColor();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("A problem occurred while removing. Try again later");
			e.printStackTrace();
		}
	}

	public Person getPersonWithFavoriteColors() {
		if (personWithFavoriteColors == null) {
			person = (Person) ELFlash.getFlash().get(SELECTED_PERSON);
			personWithFavoriteColors = getPersonFacade().findPersonWithAllFavoriteColors(person.getId());
		}

		return personWithFavoriteColors;
	}

	public void setPersonWithFavoriteColorsForDetail(Person person) {
		personWithFavoriteColorsForDetail = getPersonFacade().findPersonWithAllFavoriteColors(person.getId());
	}

	public Person getPersonWithFavoriteColorsForDetail() {
		if (personWithFavoriteColorsForDetail == null) {
			personWithFavoriteColorsForDetail = new Person();
			personWithFavoriteColorsForDetail.setFavoriteColors(new ArrayList<FavoriteColor>());
		}

		return personWithFavoriteColorsForDetail;
	}

	public void resetPersonWithFavoriteColorsForDetail() {
		personWithFavoriteColorsForDetail = new Person();
	}

	public String editPersonFavoriteColors() {
		ELFlash.getFlash().put(SELECTED_PERSON, person);
		return "/pages/public/person/personFavoriteColors/personFavoriteColors.xhtml";
	}

	public PersonFacade getPersonFacade() {
		if (personFacade == null) {
			personFacade = new PersonFacade();
		}

		return personFacade;
	}

	public Person getPerson() {
		if (person == null) {
			person = new Person();
		}

		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	public void setLanguageBean(LanguageBean languageBean) {
		this.languageBean = languageBean;
	}

	public void setFavoriteColorBean(FavoriteColorBean favoriteColorBean) {
		this.favoriteColorBean = favoriteColorBean;
	}

	public List<Person> getAllPersons() {
		if (persons == null) {
			loadPersons();
		}

		return persons;
	}
	
	public List<Language> getRemainingLanguages(String name) {
		//get all languages as copy
		List<Language> res = new ArrayList<Language>(this.languageBean.getAllLanguages());
		//remove already added languages
		res.removeAll(personWithLanguages.getLanguages());
		//remove when name not occurs
		res.removeIf(l -> l.getName().toLowerCase().contains(name.toLowerCase()) == false);
		return res;
	}

	public List<FavoriteColor> getRemainingFavoriteColors(String name) {
		//get all favoriteColors as copy
		List<FavoriteColor> res = new ArrayList<FavoriteColor>(this.favoriteColorBean.getAllFavoriteColors());
		//remove already added favoriteColors
		res.removeAll(personWithFavoriteColors.getFavoriteColors());
		//remove when name not occurs
		res.removeIf(l -> l.getName().toLowerCase().contains(name.toLowerCase()) == false);
		return res;
	}

	private void loadPersons() {
		persons = getPersonFacade().listAll();
	}

	public void resetPerson() {
		person = new Person();
	}

	public Language getLanguage() {
		if (language == null) {
			language = new Language();
		}

		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public void resetLanguage() {
		language = new Language();
	}

	private void reloadPersonWithLanguages() {
		personWithLanguages = getPersonFacade().findPersonWithAllLanguages(person.getId());
	}

	public FavoriteColor getFavoriteColor() {
		if (favoriteColor == null) {
			favoriteColor = new FavoriteColor();
		}

		return favoriteColor;
	}

	public void setFavoriteColor(FavoriteColor favoriteColor) {
		this.favoriteColor = favoriteColor;
	}

	public void resetFavoriteColor() {
		favoriteColor = new FavoriteColor();
	}

	private void reloadPersonWithFavoriteColors() {
		personWithFavoriteColors = getPersonFacade().findPersonWithAllFavoriteColors(person.getId());
	}
}