package my.webframework.security.reponsitory;

import my.webframework.security.domain.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("roleReponsitory")
public interface IRoleReponsitory extends
		JpaRepository<Role, Integer>,
		JpaSpecificationExecutor<Role> {

}
