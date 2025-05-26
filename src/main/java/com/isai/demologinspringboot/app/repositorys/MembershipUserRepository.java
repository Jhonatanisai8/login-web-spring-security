package com.isai.demologinspringboot.app.repositorys;

import com.isai.demologinspringboot.app.models.MembershipUser;
import com.isai.demologinspringboot.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembershipUserRepository extends JpaRepository<MembershipUser, Integer> {
    Optional<MembershipUser> findByUserAndStatus(User user, Boolean status);
}
