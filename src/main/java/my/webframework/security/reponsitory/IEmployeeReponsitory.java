package my.webframework.security.reponsitory;

import my.webframework.security.domain.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository("employeeReponsitory")
public interface IEmployeeReponsitory extends
		JpaRepository<Employee, Integer>,
		JpaSpecificationExecutor<Employee> {
}
