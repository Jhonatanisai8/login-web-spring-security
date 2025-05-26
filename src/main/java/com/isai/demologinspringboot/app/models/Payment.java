package com.isai.demologinspringboot.app.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPayment;

    private String receiptNumber;

    private Double paymentAmount;

    private LocalDateTime paymentDate;

    private String concept;

    private String observations;

    private String receipt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_membership_type", nullable = false)
    private MembershipType membershipType;
}
