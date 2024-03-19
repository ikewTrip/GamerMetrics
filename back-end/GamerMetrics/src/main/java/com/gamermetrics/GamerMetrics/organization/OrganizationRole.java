package com.gamermetrics.GamerMetrics.organization;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gamermetrics.GamerMetrics.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrganizationRole {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "organizationRoles")
    @JsonIgnore
    private Set<User> userSet = new HashSet<>();

}
