package com.isai.demologinspringboot.app.services;

import com.isai.demologinspringboot.app.models.MembershipType;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface MembershipTypeCrud {
    MembershipType saveMembershipType(MembershipType membershipType);

    List<MembershipType> findAllMembershipTypes();

    List<MembershipType> findAllMembershipTypes(Sort sort);
}
