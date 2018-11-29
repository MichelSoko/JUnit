package bo;

import bo.CityBO;

public class PersonBO {
	
	// attributs
	private int id_person;
	private CityBO cityBO;
	private String firstname, lastname, emails, phone;
	
	// constructeurs
	public PersonBO() {
	}
	
	public PersonBO(CityBO cityBO, String firstname, String lastname, String emails, String phone) {
		this.setCityBO(cityBO);
		this.setFirstname(firstname);
		this.setLastname(lastname);
		this.setEmails(emails);
		this.setPhone(phone);
	}
	
	// accesseurs et modificateurs
	public int getId_person() {
		return id_person;
	}
	public void setId_person(int id_person) {
		this.id_person = id_person;
	}
	public CityBO getCityBO() {
		return cityBO;
	}
	public void setCityBO(CityBO cityBO) {
		this.cityBO = cityBO;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmails() {
		return emails;
	}
	public void setEmails(String emails) {
		this.emails = emails;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}	

	@Override
	public String toString() {
		return "Personne : " + getFirstname() + " " + getLastname() 
				+ " (tél:" + getPhone() + "|email:" + getEmails() + ")";
	}

}
