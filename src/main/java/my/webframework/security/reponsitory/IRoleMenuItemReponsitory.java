package my.webframework.security.reponsitory;

import my.webframework.security.domain.MenuItem;
import my.webframework.security.domain.RoleMenuItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository("roleMenuItemReponsitory")
public interface IRoleMenuItemReponsitory extends
		JpaRepository<RoleMenuItem, Integer>,
		JpaSpecificationExecutor<MenuItem> {
}
