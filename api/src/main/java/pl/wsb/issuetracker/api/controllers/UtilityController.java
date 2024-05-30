package pl.wsb.issuetracker.api.controllers;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.wsb.issuetracker.administration.AdministrationClient;
import pl.wsb.issuetracker.administration.dto.DepartmentDTO;
import pl.wsb.issuetracker.administration.dto.UserAccountDisplayDTO;
import pl.wsb.issuetracker.common.pagination.PageWrapperDTO;
import pl.wsb.issuetracker.jpa.entity.UserRole;
import pl.wsb.issuetracker.user.dto.UserFiltersDTO;

import java.util.Collection;

@RestController
@Validated
@RequestMapping(UtilityController.REST_API_BASE_PATH)
@RequiredArgsConstructor
public class UtilityController {
    static final String REST_API_BASE_PATH = "${rest.prefix}${rest.secured.path}";
    public static final UserFiltersDTO TECHNICIAN_USER_FILTER = UserFiltersDTO.builder()
            .userRole(UserRole.TECHNICIAN.name())
            .isActive(true)
            .build();

    private final AdministrationClient administrationClient;

    @GetMapping("/technicians")
    @PreAuthorize("hasRole('ROLE_TECHNICIAN')")
    public PageWrapperDTO<UserAccountDisplayDTO> getAvailableTechnicians(@RequestParam(defaultValue = "0") @Min(0) final int offset,
                                                                         @RequestParam(defaultValue = "20") @Min(20) final int limit) {
        return administrationClient.getUserAccounts(TECHNICIAN_USER_FILTER, offset, limit);
    }

    @GetMapping("/departments")
    public Collection<DepartmentDTO> getAllDepartments() {
        return administrationClient.getAllDepartments();
    }

}
