package com.bksoftwarevn.auction.api;

import com.bksoftwarevn.auction.model.CreateRoleRequest;
import com.bksoftwarevn.auction.model.CreateRoleResponse;
import com.bksoftwarevn.auction.model.RolesResponse;
import com.bksoftwarevn.auction.model.UpdateRoleRequest;
import com.bksoftwarevn.auction.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.bksoftwarevn.auction.api.v1.RoleApi;


@RestController
@Slf4j
@RequiredArgsConstructor
public class RoleController implements RoleApi {

    private final RoleService roleService;


    @Override
    public ResponseEntity<RolesResponse> getRoles() {
        log.info("[RoleController.getRoles] Start get list role");
        return ResponseEntity.ok(roleService.pull());
    }

    @Override
    public ResponseEntity<CreateRoleResponse> postCreateRole(CreateRoleRequest createRoleRequest) {
        log.info("[RoleController.postCreateRole] Start create role: {}", createRoleRequest);
        return ResponseEntity.ok(roleService.create(createRoleRequest));
    }

    @Override
    public ResponseEntity<CreateRoleResponse> putUpdateRole(UpdateRoleRequest updateRoleRequest) {
        log.info("[RoleController.putUpdateRole] Start update role: {}", updateRoleRequest);
        return ResponseEntity.ok(roleService.update(updateRoleRequest));
    }
}
