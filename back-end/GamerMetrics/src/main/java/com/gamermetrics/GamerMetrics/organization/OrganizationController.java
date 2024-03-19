package com.gamermetrics.GamerMetrics.organization;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/organizations")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping
    ResponseEntity<OrganizationResponse[]> getAllOrganizations() {
        return new ResponseEntity<>(organizationService.getOrganizations(), HttpStatus.OK);
    }

    @GetMapping("{organizationId}")
    ResponseEntity<OrganizationResponse> getOrganizationById(@PathVariable Integer organizationId) {
        return new ResponseEntity<>(organizationService.getOrganizationById(organizationId), HttpStatus.OK);
    }

    @GetMapping("/organization-role")
    ResponseEntity<OrganizationRoleResponse> getOrganizationRoles(Principal principal) {
        return new ResponseEntity<>(organizationService.getOrganizationRole(principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/my")
    ResponseEntity<OrganizationResponse> getMyOrganization(Principal principal) {
        return new ResponseEntity<>(organizationService.getMyOrganization(principal.getName()), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<OrganizationResponse> createOrganization(
            @Valid @RequestBody OrganizationRequest organizationRequest,
            Principal principal) {
        return new ResponseEntity<>(organizationService.createOrganization(organizationRequest, principal.getName()), HttpStatus.CREATED);
    }

    @PutMapping
    ResponseEntity<OrganizationResponse> updateOrganizationInfo(
            @Valid @RequestBody OrganizationRequest organizationRequest,
            Principal principal) {
        return new ResponseEntity<>(organizationService.updateOrganizationInfo(organizationRequest, principal.getName()), HttpStatus.OK);
    }

    @DeleteMapping
    ResponseEntity<Void> deleteOrganization(Principal principal) {
        organizationService.deleteOrganization(principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
