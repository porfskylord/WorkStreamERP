package com.wserp.inviteservice.repository;

import com.wserp.inviteservice.entity.Invite;
import com.wserp.inviteservice.entity.enums.InviteStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InviteRepository extends JpaRepository<Invite, String> {
    Invite findByEmail(String email);

    Optional<Invite> findByInviteToken(String inviteToken);

    List<Invite> findByStatus(InviteStatus inviteStatus);
}
