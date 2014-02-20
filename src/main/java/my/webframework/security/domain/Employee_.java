package my.webframework.security.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Employee.class)
public abstract class Employee_ {

	public static volatile SingularAttribute<Employee, Double> order_number;
	public static volatile SingularAttribute<Employee, Date> birthday;
	public static volatile SingularAttribute<Employee, String> handphone;
	public static volatile SingularAttribute<Employee, Integer> area_id;
	public static volatile SingularAttribute<Employee, String> sex;
	public static volatile SingularAttribute<Employee, String> political;
	public static volatile SingularAttribute<Employee, String> pub_phone;
	public static volatile SingularAttribute<Employee, String> job;
	public static volatile SingularAttribute<Employee, byte[]> photo;
	public static volatile SingularAttribute<Employee, String> nation;
	public static volatile SingularAttribute<Employee, Integer> id;
	public static volatile SingularAttribute<Employee, Date> job_date;
	public static volatile SingularAttribute<Employee, String> school;
	public static volatile SingularAttribute<Employee, String> email;
	public static volatile SingularAttribute<Employee, Integer> organization_id;
	public static volatile SetAttribute<Employee, Role> roles;
	public static volatile SingularAttribute<Employee, String> name;
	public static volatile SingularAttribute<Employee, User> user;

}

