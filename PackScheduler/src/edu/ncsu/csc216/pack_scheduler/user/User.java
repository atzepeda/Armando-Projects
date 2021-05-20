package edu.ncsu.csc216.pack_scheduler.user;

/**
 * Represents a User, includes user's first and last name; id, email, and
 * password.
 * 
 * @author Yu
 */
public abstract class User {

	/** First name of student */
	private String firstName;
	/** Last name of student */
	private String lastName;
	/** Unique ID of student */
	private String id;
	/** Email of student account */
	private String email;
	/** Password of student account */
	private String password;

	/**
	 * Constructs user object
	 * 
	 * @param firstName
	 *            user's first name
	 * @param lastName
	 *            user's last name
	 * @param id
	 *            user's id
	 * @param email
	 *            user's email
	 * @param password
	 *            user's password
	 */
	public User(String firstName, String lastName, String id, String email, String password) {
		setFirstName(firstName);
		setLastName(lastName);
		setId(id);
		setEmail(email);
		setPassword(password);
	}

	/**
	 * Returns email of student account
	 * 
	 * @return String of student email account
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Returns password of student account
	 * 
	 * @return String of password for student account
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Returns first name of student
	 * 
	 * @return String of first name of student
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns last name of student
	 * 
	 * @return String of last name of student
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns unique ID of student
	 * 
	 * @return String of unique student ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets first name of student
	 * 
	 * @param firstName
	 *            first name of student
	 * @throws IllegalArgumentException
	 *             if input is null or empty
	 */
	public void setFirstName(String firstName) {
		if (firstName == null) {
			throw new IllegalArgumentException("Invalid first name");
		}
		if (firstName.length() == 0) {
			throw new IllegalArgumentException("Invalid first name");
		}
		this.firstName = firstName;
	}

	/**
	 * Sets the Student's last name. If the last name is null, or an empty string an
	 * IllegalArgumentException is thrown.
	 * 
	 * @param lastName
	 *            the lastName to set
	 * @throws IllegalArgumentException
	 *             if last name is null or an empty string
	 */
	public void setLastName(String lastName) {
		if (lastName == null) {
			throw new IllegalArgumentException("Invalid last name");
		}
		if (lastName.length() == 0) {
			throw new IllegalArgumentException("Invalid last name");
		}
		this.lastName = lastName;
	}

	/**
	 * Sets unique ID of student, remains unchanged
	 * 
	 * @param id
	 *            ID of student
	 * @throws IllegalArgumentException
	 *             if input is null or empty
	 */
	protected void setId(String id) {
		if (id == null) {
			throw new IllegalArgumentException("Invalid id");
		}
		if (id.length() == 0) {
			throw new IllegalArgumentException("Invalid id");
		}
		this.id = id;
	}

	/**
	 * Sets email of student account
	 * 
	 * @param email
	 *            of student account
	 * @throws IllegalArgumentException
	 *             if input is null or empty
	 * @throws IllegalArgumentException
	 *             if '.' precedes '@' in email
	 */
	public void setEmail(String email) {
		if (email == null || email.equals("")) {
			throw new IllegalArgumentException("Invalid email");
		}
		boolean at = false;
		boolean dot = false;
		int atIndex = 0;
		int dotIndex = 0;

		for (int i = 0; i < email.length(); i++) {
			if (email.charAt(i) == '@') {
				at = true;
				atIndex = i;
				break;
			}
		}

		for (int i = 0; i < email.length(); i++) {
			if (email.charAt(i) == '.') {
				dot = true;
				dotIndex = i;
			}
		}

		if (!at || !dot) {
			throw new IllegalArgumentException("Invalid email");
		}
		if (dotIndex < atIndex) {
			throw new IllegalArgumentException("Invalid email");
		}

		this.email = email;
	}

	/**
	 * Sets password of student account
	 * 
	 * @param password
	 *            of student account
	 * @throws IllegalArgumentException
	 *             if input is null or empty
	 */
	public void setPassword(String password) {
		if (password == null) {
			throw new IllegalArgumentException("Invalid password");
		}
		if (password.length() == 0) {
			throw new IllegalArgumentException("Invalid password");
		}
		this.password = password;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

}