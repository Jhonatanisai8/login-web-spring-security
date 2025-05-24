package com.isai.demologinspringboot.app.services.impl;

import com.isai.demologinspringboot.app.models.MembershipType;
import com.isai.demologinspringboot.app.repositorys.MembershipTypeRepository;
import com.isai.demologinspringboot.app.services.MembershipTypeCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipTypeService
        implements MembershipTypeCrud {

    private final MembershipTypeRepository membershipTypeRepository;

    @Override
    public MembershipType saveMembershipType(MembershipType membershipType) {
        return membershipTypeRepository.save(membershipType);
    }

    @Override
    public List<MembershipType> findAllMembershipTypes() {
        return membershipTypeRepository.findAll();
    }

    @Override
    public List<MembershipType> findAllMembershipTypes(Sort sort) {
        return membershipTypeRepository.findAll(sort);
    }
}
