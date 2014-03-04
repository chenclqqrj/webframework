package my.webframework.security.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Organization.class)
public abstract class Organization_ {

	public static volatile SingularAttribute<Organization, Double> order_number;
	public static volatile SingularAttribute<Organization, String> window_phone;
	public static volatile SingularAttribute<Organization, String> xzqh;
	public static volatile SingularAttribute<Organization, String> zip_code;
	public static volatile SingularAttribute<Organization, String> pub_phone;
	public static volatile SingularAttribute<Organization, String> www;
	public static volatile SetAttribute<Organization, Organization> children;
	public static volatile SingularAttribute<Organization, String> label;
	public static volatile SingularAttribute<Organization, String> property;
	public static volatile SingularAttribute<Organization, Integer> pid;
	public static volatile SingularAttribute<Organization, Integer> id;
	public static volatile SingularAttribute<Organization, String> address;
	public static volatile SingularAttribute<Organization, String> consulation_phone;
	public static volatile SetAttribute<Organization, Role> roles;
	public static volatile SetAttribute<Organization, Employee> employees;
	public static volatile SingularAttribute<Organization, String> full_name;

}

