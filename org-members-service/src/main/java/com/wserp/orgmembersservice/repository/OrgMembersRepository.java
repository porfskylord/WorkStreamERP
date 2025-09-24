package com.wserp.orgmembersservice.repository;

import com.wserp.orgmembersservice.entity.OrgMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrgMembersRepository extends JpaRepository<OrgMembers, Long> {
    OrgMembers findByOrgIdAndUserId(String orgId, String userId);

}
