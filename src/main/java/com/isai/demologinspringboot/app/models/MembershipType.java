package com.isai.demologinspringboot.app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

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

    @Size(max = 20)
    @NotEmpty
    private String membershipCode;

    @NotEmpty
    @Size(max = 30)
    private String membershipName;

    @NotEmpty
    @Size(max = 250)
    private String description;

    @NotNull
    private Double price;

    @NotNull
    private Integer durationDays;

    @NotNull
    private Integer monthlyAccesses;

    @Size(max = 270)
    @NotBlank
    private String benefits;

    @NotBlank
    @Size(max = 270)
    private String restrictionsMembershipType;

    private Boolean active;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    private Date creationDate;

    private String pathImageProfile;

    @Transient
    private MultipartFile imageProfile;

    @OneToMany(mappedBy = "membershipType")
    private List<MembershipUser> users;
}
