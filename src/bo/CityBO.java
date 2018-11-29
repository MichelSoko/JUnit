package bo;

public class CityBO {

	// attributs
	private int id_city;
	private String name;
	private String mayor;
	private int inhabitants;
	private String postalcode;
	
	// constructeurs
	public CityBO() {	
	}
	
	public CityBO(String name, String mayor, int inhabitants, String postalcode) {
		this.setName(name);
		this.setMayor(mayor);
		this.setInhabitants(inhabitants);
		this.setPostalcode(postalcode);
	}
	
	// accesseurs et modificateurs
	public int getId_city() {
		return id_city;
	}
	public void setId_city(int id_city) {
		this.id_city = id_city;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMayor() {
		return mayor;
	}
	public void setMayor(String mayor) {
		this.mayor = mayor;
	}
	public int getInhabitants() {
		return inhabitants;
	}
	public void setInhabitants(int inhabitants) {
		this.inhabitants = inhabitants;
	}
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	
	@Override
	public String toString() {
		return "Ville : " + getName() + "\n"
				+ "> Maire : " + getMayor() + "\n"
				+ "> Nombre d'habitants : " + getInhabitants() + "\n"
				+ "> Code Postal : " + getPostalcode();
	}
	
}
