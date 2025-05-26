package com.isai.demologinspringboot.app.services;

import com.isai.demologinspringboot.app.models.MembershipType;
import com.isai.demologinspringboot.app.models.User;

public interface MembershipUserCrud {
    void assignMembershipToUser(User userRequest, MembershipType membershipTypeRequest);
}
