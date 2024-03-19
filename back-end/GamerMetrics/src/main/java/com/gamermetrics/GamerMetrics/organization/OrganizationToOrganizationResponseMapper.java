package com.gamermetrics.GamerMetrics.organization;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class OrganizationToOrganizationResponseMapper implements Function<Organization, OrganizationResponse> {
    @Override
    public OrganizationResponse apply(Organization organization) {
        return OrganizationResponse.builder()
                .name(organization.getName())
                .description(organization.getDescription())
                .creationDate(organization.getCreationDate())
                .build();
    }
}
