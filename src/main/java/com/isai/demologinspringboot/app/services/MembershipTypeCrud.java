package com.isai.demologinspringboot.app.services;

import com.isai.demologinspringboot.app.models.MembershipType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MembershipTypeCrud {
    MembershipType saveMembershipType(MembershipType membershipType);

    List<MembershipType> findAllMembershipTypes();

    Page<MembershipType> findAllMembershipTypes(Pageable pageable);

}
