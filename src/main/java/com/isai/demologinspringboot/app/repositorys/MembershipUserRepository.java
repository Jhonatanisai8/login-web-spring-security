package com.isai.demologinspringboot.app.repositorys;

import com.isai.demologinspringboot.app.models.MembershipUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipUserRepository extends JpaRepository<MembershipUser, Integer> {

}
