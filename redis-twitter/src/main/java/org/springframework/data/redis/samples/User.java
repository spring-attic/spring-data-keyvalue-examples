package org.springframework.data.redis.samples;


/**
 * User class.
 * 
 * @author Costin Leau
 */
public class User {

	private Long id;
	private String name;
	private String pass;
	private String authKey;

	public User(String name, String pass) {
		this.name = name;
		this.pass = pass;
	}

	/**
	 * Returns the id.
	 *
	 * @return Returns the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the name.
	 *
	 * @return Returns the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the pass.
	 *
	 * @return Returns the pass
	 */
	public String getPass() {
		return pass;
	}

	/**
	 * @param pass The pass to set.
	 */
	public void setPass(String password) {
		this.pass = password;
	}

	/**
	 * Returns the authKey.
	 *
	 * @return Returns the authKey
	 */
	public String getAuthKey() {
		return authKey;
	}

	/**
	 * @param authKey The authKey to set.
	 */
	public void setAuthKey(String loginKey) {
		this.authKey = loginKey;
	}
}