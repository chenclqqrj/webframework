package my.webframework.security.reponsitory;

import my.webframework.security.domain.MenuItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository("menuItemReponsitory")
public interface IMenuItemReponsitory extends
		JpaRepository<MenuItem, Integer>,
		JpaSpecificationExecutor<MenuItem> {
}
