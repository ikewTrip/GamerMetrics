package com.gamermetrics.GamerMetrics.organization;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface OrganizationRoleRepository extends JpaRepository<OrganizationRole, Integer> {

    Optional<OrganizationRole> findOrganizationRoleByName(String name);

    List<OrganizationRole> findAllByUserSet_Id(Integer id);

}
