package com.isai.demologinspringboot.app.repositorys;

import com.isai.demologinspringboot.app.models.MembershipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipTypeRepository
        extends JpaRepository<MembershipType, Integer> {
}
