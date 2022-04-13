package pt.uc.dei.proj5.dto;

import java.io.Serializable;

import javax.validation.constraints.*;
//import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import javax.xml.bind.annotation.XmlRootElement;

import pt.uc.dei.proj5.entity.User;

//import org.hibernate.validator.constraints.URL;

@XmlRootElement
public class UserDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	//implements seriaziable sinaliza que pode mandar dados para ficheiros
	//com a anotaçao @XmlRootElement sinalizamos este pojo para transformar o tipo de dados em xml ou json
	 @NotBlank 
	private String firstName;
	 @NotBlank 
	private String lastName;
	//This regular expression refers to a pattern with at least one digit, one upper case letter, one lower case letter and one special symbol (“@#$%”).
	//@Pattern(regexp="((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,15})")
	//@Size(min=6, message="Password no mínimo tem de ter 6 carateres")
	@NotBlank 
	private String password;
	@NotBlank @Email (message = "Email deve introduzir um email válido (não pode ficar vazio)")
	private String email;
	//@URL(protocol = "http")
	@NotBlank 
	private String image;
	private String biography;
//	@NotBlank 
//	private User.UserPriv privileges;
//	


	
	//o código que serializa e desserializa usa o construtor vazio e depois chama os setters e getters. -> usado por exemplo em R3
	/**
	 * Construtor vazio
	 */
	public UserDTO() {
		//nothing to do here;
	}
	/**
	 * Função que cria um utilizador com os parametros definidos
	 * @param firstName Primeiro nome do utilizador
	 * @param lastName Último nome do utilizador
	 * @param password
	 * @param email Email do utilizador
	 * @param image URL da fotografia do utilizador
	 * @param biography biography do utilizador
	 */
	public UserDTO(@NotBlank String firstName, @NotBlank String lastName,
			@Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,15})") @Size(min = 6, message = "Password no mínimo tem de ter 6 carateres") @NotBlank String password,
			@NotBlank @Email(message = "Email deve introduzir um email válido (não pode ficar vazio)") String email,
			@NotBlank String image, String biography) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.image = image;
		this.biography=biography;
		//this.privileges = privileges;
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

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String getBiography() {
		return biography;
	}
	public void setBiography(String biography) {
		this.biography = biography;
	}
	//	public User.UserPriv getPrivileges() {
//		return privileges;
//	}
//	public void setPrivileges(User.UserPriv privileges) {
//		this.privileges = privileges;
//	}
	@Override
	public String toString() {
		return "UserDTO [firstName=" + firstName + ", lastName=" + lastName + ", password=" + password + ", email="
				+ email + ", image=" + image +  "]";
	}	
	
	
}
