package my.webframework.security.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import my.webframework.security.domain.User.AccountType;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends my.webframework.security.domain.Entity_ {

	public static volatile SingularAttribute<User, String> username;
	public static volatile SingularAttribute<User, AccountType> accountType;
	public static volatile SingularAttribute<User, String> reserveEmail;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, Boolean> isLock;

}

