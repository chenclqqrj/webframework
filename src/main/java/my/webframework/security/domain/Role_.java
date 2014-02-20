package my.webframework.security.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Role.class)
public abstract class Role_ {

	public static volatile SingularAttribute<Role, Integer> id;
	public static volatile SingularAttribute<Role, Integer> organization_id;
	public static volatile SetAttribute<Role, RoleMenuItem> roleMenuItems;
	public static volatile SingularAttribute<Role, String> name;
	public static volatile SetAttribute<Role, Role> children;
	public static volatile SingularAttribute<Role, Integer> pid;
	public static volatile SingularAttribute<Role, Integer> type;
	public static volatile SetAttribute<Role, Employee> employees;

}

