package com.isai.demologinspringboot.app.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "membership_users")
public class MembershipUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMembershipUser;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_membership_type", nullable = true)
    private MembershipType membershipType;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "start_date")
    private LocalDate startDate;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "end_date")
    private LocalDate endDate;

    private Boolean status;

    private Double pricePaid;

    private Integer remainingAccesses;

    private LocalDate lastRenewalDate;

    private Boolean automaticRenewal;

}
