package my.webframework.security.reponsitory;

import my.webframework.security.domain.Organization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository("organizationReponsitory")
public interface IOrganizationReponsitory extends
		JpaRepository<Organization, Integer>,
		JpaSpecificationExecutor<Organization> {

}
