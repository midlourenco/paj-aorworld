package pt.uc.dei.proj5.dto;

import java.io.Serializable;
import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlRootElement;

import pt.uc.dei.proj5.entity.User;


@XmlRootElement
public class UserDTOResp implements Serializable{
	private static final long serialVersionUID = 1L;
	//implements seriaziable sinaliza que pode mandar dados para ficheiros
	//com a anotaçao @XmlRootElement sinalizamos este pojo para transformar o tipo de dados em xml ou json
	 @NotBlank 
	private String firstName;
	 @NotBlank 
	private String lastName;
	 @NotBlank @Email (message = "Email deve introduzir um email válido")
	private String email;
	//@URL(protocol = "http")
	@NotBlank 
	private String image;
	@NotBlank 
	private User.UserPriv privileges;
	@NotBlank 
	private int id;
	private String biography;
	private boolean deleted;
	
	//o código que serializa e desserializa usa o construtor vazio e depois chama os setters e getters. -> usado por exemplo em R3
	/**
	 * Construtor vazio
	 */
	public UserDTOResp() {
		//nothing to do here;
	}

	/**
	 * Função que cria um utilizador com os parametros definidos
	 * @param firstName Primeiro nome do utilizador
	 * @param lastName Último nome do utilizador
	 * @param email Email do utilizador
	 * @param image URL da fotografia do utilizador
	  * @param id
	 * @param privileges Privilégios do utilizador
	 */
	public UserDTOResp(@NotBlank String firstName, @NotBlank String lastName,
			@NotBlank @Email(message = "Email deve introduzir um email válido") String email, @NotBlank String image,
			@NotBlank User.UserPriv privileges, @NotBlank int id, String biography) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.image = image;
		this.privileges = privileges;
		this.id = id;
		this.biography = biography;
	}
	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public User.UserPriv getPrivileges() {
		return privileges;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPrivileges(User.UserPriv privileges) {
		this.privileges = privileges;
	}


	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "UserDTOResp [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", image="
				+ image + ", privilegies=" + privileges + "]";
	}


	
	
}
