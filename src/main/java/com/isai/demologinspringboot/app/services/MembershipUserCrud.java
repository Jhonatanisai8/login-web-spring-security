package com.isai.demologinspringboot.app.services;

import com.isai.demologinspringboot.app.models.MembershipType;
import com.isai.demologinspringboot.app.models.MembershipUser;
import com.isai.demologinspringboot.app.models.User;

import java.util.List;
import java.util.Optional;

public interface MembershipUserCrud {
    void assignMembershipToUser(User userRequest, MembershipType membershipTypeRequest);

    Optional<MembershipUser> findByUserAndStatus(User user, Boolean status);

    List<MembershipUser> findAllByUser(User user);
}
