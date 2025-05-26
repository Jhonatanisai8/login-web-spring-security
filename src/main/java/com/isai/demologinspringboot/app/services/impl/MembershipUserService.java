package com.isai.demologinspringboot.app.services.impl;

import com.isai.demologinspringboot.app.models.MembershipType;
import com.isai.demologinspringboot.app.models.MembershipUser;
import com.isai.demologinspringboot.app.models.User;
import com.isai.demologinspringboot.app.repositorys.MembershipUserRepository;
import com.isai.demologinspringboot.app.services.MembershipUserCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MembershipUserService implements MembershipUserCrud {

    private final MembershipUserRepository membershipUserRepository;

    @Override
    public void assignMembershipToUser(User user, MembershipType membershipType) {
        if (user == null || membershipType == null) {
            throw new IllegalArgumentException("User and MembershipType must not be null");
        }

        MembershipUser userMembership = new MembershipUser();
        userMembership.setUser(user);
        userMembership.setMembershipType(membershipType);

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(membershipType.getDurationDays());

        userMembership.setStartDate(startDate);
        userMembership.setEndDate(endDate);
        userMembership.setStatus(true);

        userMembership.setRemainingAccesses(membershipType.getDurationDays().intValue());

        userMembership.setPricePaid(membershipType.getPrice());
        userMembership.setLastRenewalDate(startDate);
        userMembership.setAutomaticRenewal(true);

        membershipUserRepository.save(userMembership);
    }


}
