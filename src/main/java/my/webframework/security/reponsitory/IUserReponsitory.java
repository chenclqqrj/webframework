package my.webframework.security.reponsitory;

import my.webframework.security.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("userReponsitory")
public interface IUserReponsitory extends JpaRepository<User, Integer>,
		JpaSpecificationExecutor<User> {
}
