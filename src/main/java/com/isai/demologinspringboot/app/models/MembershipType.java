package com.isai.demologinspringboot.app.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "membership_types", uniqueConstraints = @UniqueConstraint(columnNames = {"membershipCode"}))
public class MembershipType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMembershipType;

    private String membershipCode;
    private String membershipName;
    private String description;
    private Double price;
    private Integer durationDays;
    private Integer monthlyAccesses;
    private String benefits;
    private String restrictionsMembershipType;
    private Boolean active;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date creationDate;

    private String pathImageProfile;

    @Transient
    private MultipartFile imageProfile;
}
