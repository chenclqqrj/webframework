package my.webframework.security.domain;

import java.sql.Blob;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MenuItem.class)
public abstract class MenuItem_ extends my.webframework.security.domain.Entity_ {

	public static volatile SingularAttribute<MenuItem, Integer> xh;
	public static volatile SingularAttribute<MenuItem, String> module;
	public static volatile SingularAttribute<MenuItem, Double> sort;
	public static volatile SingularAttribute<MenuItem, String> xtbm;
	public static volatile SetAttribute<MenuItem, RoleMenuItem> roleMenuItems;
	public static volatile SingularAttribute<MenuItem, Integer> system_Id;
	public static volatile SingularAttribute<MenuItem, Date> createdate;
	public static volatile ListAttribute<MenuItem, MenuItem> children;
	public static volatile SingularAttribute<MenuItem, String> xtgybl;
	public static volatile SingularAttribute<MenuItem, String> label;
	public static volatile SingularAttribute<MenuItem, Boolean> sfqy;
	public static volatile SingularAttribute<MenuItem, Integer> pid;
	public static volatile SingularAttribute<MenuItem, String> type;
	public static volatile SingularAttribute<MenuItem, String> cs;
	public static volatile SingularAttribute<MenuItem, String> creator;
	public static volatile SingularAttribute<MenuItem, Integer> id;
	public static volatile SingularAttribute<MenuItem, Blob> tb;
	public static volatile SingularAttribute<MenuItem, String> description;

}

