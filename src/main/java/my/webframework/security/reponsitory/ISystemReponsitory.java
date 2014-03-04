package my.webframework.security.reponsitory;

import my.webframework.security.domain.System;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("systemReponsitory")
public interface ISystemReponsitory extends JpaRepository<System, Integer>, JpaSpecificationExecutor<System> {
}
