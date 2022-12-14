
package pt.uc.dei.proj5.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
//import java.util.HexFormat;  //apenas para java 17
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.DatatypeConverter;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;



/**
 * The persistent class for the User database table.
 * 
 */
@Entity
@Table(name = "Users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true, updatable = false, insertable = false)
	private int id;
	
	@Column(name = "firstName", nullable = false)
	private String firstName;

	@Column(name = "lastName", nullable = false)
	private String lastName;
	
	@Column (name= "autoAdmin", nullable = true)
	private boolean autoAdmin;
	//	@ColumnTransformer(
//    forColumn = "password",
//    write = "HEX(AES_ENCRYPT(?, 'password'))",
//    read = "AES_DECRYPT(UNHEX(password),'password')"
//)
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "image", nullable = false)
	private String image;
	
	@Column(name = "biography", nullable = true)
	private String biography;

	@Column(name = "token", nullable = true) // s?? tem token se houver login
	private String token;
//boolean default true
	@Column(name = "deleted", nullable = false)
	private boolean deleted;
	//columnDefinition = "ENUM('VIEWER','MEMBER','ADMIN') DEFAULT VIEWER")
	@Enumerated(EnumType.STRING)
	@Column(name = "UserPriv", nullable = false, columnDefinition = "ENUM('VIEWER','MEMBER','ADMIN')")
	private UserPriv privileges;

	@Column(name = "createdDate", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp createdDate;

	@Column(name = "lastModifDate", nullable = true )
	private Timestamp lastModifDate;

	@Column(name = "lastLogoutDate", nullable = true )
	private Timestamp lastLogoutDate;

	@Column(name = "autoAcceptInvites", nullable = true )
	private boolean autoAcceptInvites;

	
	
	@ManyToOne (optional=true)  //presen??a n??o ??  obrigat??ria na rela????o
	private User lastModifBy;
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="lastModifBy")
	private List<User> updatedUsers;

	//fetch => FetchType.EAGER, cascade = CascadeType.REMOVE) // este fetch EAGER - as lista seguinte n??o ?? ign
		//fetch = FetchType.EAGER,
	//alternativa a ter mais do que 1 FetchType.EAGER de acordo com o link seguinte:
	//https://stackoverflow.com/questions/4334970/hibernate-throws-multiplebagfetchexception-cannot-simultaneously-fetch-multipl
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "createdBy")
	private List<News> createdNews;
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "lastModifBy")
	private List<News> updatedNews;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "createdBy")
	private List<Project> createdProjects;
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "lastModifBy")
	private List<Project> updatedProjects;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<Notification> notifications;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "user")
	private List<ProjectSharing> projectSharing;

//	@LazyCollection(LazyCollectionOption.FALSE)
//	@OneToMany(mappedBy = "user")
//	private List<NewsSharing> newsSharing;
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(mappedBy="users")
	private Set<News> news;
	
	
	//OUTRAS IDEIAS: em vez de member- editor/publisher ...
	public enum UserPriv {VIEWER,MEMBER,ADMIN};

	
	// o c??digo que serializa e desserializa usa o construtor vazio e depois chama
	// os setters e getters. -> usado por exemplo em R3
	/**
	 * Construtor vazio
	 */
	public User() {
		// nothing to do here;
	}

	
	
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
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
//	/**
//	 * @deprecated
//	 * @return
//	 */
////	public String getPassword() {
////		return password;
////	}
////	/**
//	 * @deprecated
//	 * @return
//	 */
////	public void setPassword(String password) {
////		this.password = password;
////	}


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}


	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}

	public UserPriv getPrivileges() {
		return privileges;
	}

	public void setPrivileges(UserPriv privileges) {
		this.privileges = privileges;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public List<News> getCreatedNews() {
		return createdNews;
	}

	public void setCreatedNews(List<News> createdNews) {
		this.createdNews = createdNews;
	}

	public List<News> getUpdatedNews() {
		return updatedNews;
	}

	public void setUpdatedNews(List<News> updatedNews) {
		this.updatedNews = updatedNews;
	}

	public List<Project> getCreatedProjects() {
		return createdProjects;
	}

	public void setCreatedProjects(List<Project> createdProjects) {
		this.createdProjects = createdProjects;
	}

	public List<Project> getUpdatedProjects() {
		return updatedProjects;
	}

	public void setUpdatedProjects(List<Project> updatedProjects) {
		this.updatedProjects = updatedProjects;
	}

	public List<ProjectSharing> getProjectSharing() {
		return projectSharing;
	}

	public void setProjectSharing(List<ProjectSharing> projectSharing) {
		this.projectSharing = projectSharing;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}
    public boolean isAutoAdmin() {
		return autoAdmin;
	}
	public void setAutoAdmin(boolean autoAdmin) {
		this.autoAdmin = autoAdmin;
	}
//	public List<NewsSharing> getNewsSharing() {
//		return newsSharing;
//	}
//	public void setNewsSharing(List<NewsSharing> newsSharing) {
//		this.newsSharing = newsSharing;
//	}
	public Timestamp getLastModifDate() {
		return lastModifDate;
	}
	public void setLastModifDate(Timestamp lastModifDate) {
		this.lastModifDate = lastModifDate;
	}	

	public void setLastModifByAndDate(User lastModifBy) {
		this.lastModifBy = lastModifBy;
		this.lastModifDate = new Timestamp(System.currentTimeMillis());
	}

	//HASHING PASSWORD -> criado para o trabalho de IPJ e adapatado para este novo projecto
	
	public Timestamp getLastLogoutDate() {
		return lastLogoutDate;
	}



	public void setLastLogoutDate(Timestamp lastLogoutDate) {
		this.lastLogoutDate = lastLogoutDate;
	}



	public boolean isAutoAcceptInvites() {
		return autoAcceptInvites;
	}



	public void setAutoAcceptInvites(boolean autoAcceptInvites) {
		this.autoAcceptInvites = autoAcceptInvites;
	}



	public String getPassword() {
		return password;
	}



	public Set<News> getNews() {
		return news;
	}



	public void setNews(Set<News> news) {
		this.news = news;
	}



	/**
     * M??todo que implementa o algoritmo PBKDF2 que permite obter uma hash da password em vez da pr??pria password.
     *
     * @param password a password da qual se pretende criar uma hash
     * @return salted password PBKDF2 hash de uma password
     */
    //ref. https://www.baeldung.com/java-password-hashing
    public String hashingPasswordRegisto(String password){
        int keyLenght=128;
        int iteration = 65536;//indicates how many iterations that this algorithm run for, increasing the time it takes to produce the hash.
        int salt_size=16; //it could also be 32,24,..

        try {
            //algoritmo seleccionado para fazer o hashing da password ?? o PBKDF2, apesar de mais lento ?? mais seguro
            SecureRandom random = new SecureRandom();
            //salt ?? um valor random que ?? adicionado no final, e que permite melhorar a seguran??a, ao permitir ter hashes diferentes se houver passwords iguais. esse valor random tem de ser guardado junto da hash
            byte[] salt = new byte[salt_size];
            random.nextBytes(salt);
            //O metodo PBEKeySpec tem 4 parametros:  password, salt, iterations, keyLength
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iteration, keyLenght);
            //o valor da string passada ?? insta??ncia do skf ?? o seguinte
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = skf.generateSecret(spec).getEncoded();

            String hashPass=iteration+":"+DatatypeConverter.printHexBinary(hash)+":"+DatatypeConverter.printHexBinary(salt)+":"+keyLenght;
            return hashPass;
        } catch ( NoSuchAlgorithmException | InvalidKeySpecException e ) {
            //e.printStackTrace();
            System.out.println("houve um problema com o hashing da password");
           // throw new RuntimeException( e );

        }
        return null;
    }

    /**
     *  M??todo que usa a implementa????o do PBKDF2 para obter uma hash de uma nova password, hash essa compar??vel com a da passowrd com a qual se pretende comparar a nova.
     *
     * @param password a nova password que se pretende comparar com uma password de um Utilizador espec??fico.
     * @param saltTouse valor do n??mero do salt usado na codifica????o da password com a qual se pretende comparar a nova.
     * @param iteration valor do n??mero de itera????es usado na codifica????o da password com a qual se pretende comparar a nova.
     * @param keyLenght valor do keyLenght  usado na codifica????o da password com a qual se pretende comparar a nova.
     * @return salted password PBKDF2 hash da nova password
     */
    public String hashingPasswordLogin(String password,String saltTouse, int iteration, int keyLenght){

        try {
            //algoritmo seleccionado para fazer o hashing da password ?? o PBKDF2, apesar de mais lento ?? mais seguro
            SecureRandom random = new SecureRandom();
            //salt ?? um valor random que ?? adicionado no final, e que permite melhorar a seguran??a, ao permitir ter hashes diferentes se houver passwords iguais. esse valor random tem de ser guardado junto da hash
            byte[] salt = DatatypeConverter.parseHexBinary(saltTouse);
            //O metodo PBEKeySpec tem 4 parametros:  password, salt, iterations, keyLength
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iteration, keyLenght);
            //o valor da string passada ?? insta??ncia do skf ?? o seguinte
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = skf.generateSecret(spec).getEncoded();

            String hashPass=iteration+":"+ DatatypeConverter.printHexBinary(hash)+":"+DatatypeConverter.printHexBinary(salt)+":"+keyLenght;
            return hashPass;
        } catch ( NoSuchAlgorithmException | InvalidKeySpecException e ) {
           // e.printStackTrace();
            System.out.println("houve um problema com o hashing da password");
           //throw new RuntimeException( e );


        }
        return null;
    }
	
	
	
	
    /**
     * M??todo que redefine o valor da hash da password do Utilizador
     *
     * @param password a nova password do Utilizador
     */
    public void setPassword(String password) {
        if(hashingPasswordRegisto(password)!=null) {
            this.password = hashingPasswordRegisto(password);
        }else{
            System.out.println("houve um problema com o hashing da password");
        }
    }

//    /**
//     * M??todo que redefine o valor da hash da password directamente a partir da pr??pria hash da password do Utilizador
//     *
//     * @param hashedpassword salted password PBKDF2 hash
//     */
//    public void setHashedPasswordAdmin(String hashedpassword) {this.password =hashedpassword;}


    /**
     * M??todo que devolve o valor da hash da password do Utilizador
     * @return hash da password do Utilizador
     */
    public String getHashPassword() {return this.password;}



	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", password=" + password
				+ ", email=" + email + ", image=" + image + ", biography=" + biography + ", token=" + token
				+ ", deleted=" + deleted + ", privileges=" + privileges + ", createdDate=" + createdDate
				 + "]";
	}

	

	
	

	//@NamedQueries({ @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
//			@NamedQuery(name = "User.findUserByToken", query = "SELECT u FROM User u WHERE  u.token = :token"),
//			@NamedQuery(name = "User.deleteDefinitelyUserByUsername", query = "DELETE FROM User WHERE username = :username"),
	//// @NamedQuery(name = "User.updateToNullUserToken", query="UPDATE FROM User set
	//// token = null, WHERE u.token = :token" )
	//})


}
