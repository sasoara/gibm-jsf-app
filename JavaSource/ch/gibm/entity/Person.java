package ch.gibm.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Entity
@NamedQuery(name = "Person.findPersonByIdWithLanguages", query = "select p from Person p left join fetch p.languages where p.id = :personId")
@NamedQuery(name = "Person.findPersonByIdWithFavoriteColors", query = "select p from Person p left join fetch p.favoriteColors where p.id = :personId")
public class Person implements Serializable {

    public static final String FIND_PERSON_BY_ID_WITH_LANGUAGES = "Person.findPersonByIdWithLanguages";
    public static final String FIND_PERSON_BY_ID_WITH_FAVORITECOLORS = "Person.findPersonByIdWithFavoriteColors";
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Date birthdate;
    private String age;
    private Timestamp creatingTimeStamp = new Timestamp(new Date().getTime());

    // https://www.youtube.com/watch?v=dOvYkzKfsdg
    // https://tecadmin.net/get-current-timestamp-in-java/

	@ManyToMany
    private List<Language> languages;

    // ManyToOne
    @ManyToMany
    private List<FavoriteColor> favoriteColors;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getAge() {
		return age;
	}

    public void setAge() {
        LocalDate localDate = birthdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int day = localDate.getDayOfMonth();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        LocalDate today = LocalDate.now();
        LocalDate birthday = LocalDate.of(year, month, day);
        age = String.valueOf(Period.between(birthday, today).getYears());
    }

    public Timestamp getCreatingTimeStamp() {
        return creatingTimeStamp;
    }

    public void setCreatingTimeStamp(Timestamp creatingTimeStamp) {
        this.creatingTimeStamp = creatingTimeStamp;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public List<FavoriteColor> getFavoriteColors() {
        return favoriteColors;
    }

    public void setFavoriteColors(List<FavoriteColor> favoriteColors) {
        this.favoriteColors = favoriteColors;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Person) {
            Person person = (Person) obj;
            return person.getId() == id;
        }

        return false;
    }
}