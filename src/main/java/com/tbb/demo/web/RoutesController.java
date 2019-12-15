package com.tbb.demo.web;

import com.tbb.demo.domain.RoutesService;
import com.tbb.demo.domain.UsersService;
import com.tbb.demo.exception.InvalidEntityException;
import com.tbb.demo.model.Route;
import com.tbb.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/routes")
public class RoutesController {
    @Autowired
    private RoutesService routesService;

    @Autowired
    private UsersService usersService;

    @GetMapping
    public List<Route> getRoutes(Principal principal) {
        User user = usersService.findByUsername(principal.getName());
        if(user.getRoles().contains("ROLE_TRAVELER")) {
            return routesService.findAllRoutes();
        }
        return routesService.findAllCompanyRoutes(user.getId());
    }

    @GetMapping("{id}")
    public Route getRouteById(@PathVariable("id") String routeId) {
        validateIsOwnerOfRoute(routeId);
        return routesService.findById(routeId);
    }

    @PostMapping
    public ResponseEntity<Route> addRoute(@Valid @RequestBody Route route, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasFieldErrors()) {
            String message = bindingResult.getFieldErrors().stream()
                    .map(err -> String.format("Invalid '%s' -> '%s': %s\n",
                            err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
                    .reduce("", (acc, errStr) -> acc + errStr);
            throw new InvalidEntityException(message);
        }
        route.setCompany(principal.getName());
        usersService.findByUsername(principal.getName());
        route.setCompanyId(usersService.findByUsername(principal.getName()).getId());
        Route created = routesService.add(route);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}").build(created.getId()))
                .body(created);
    }

    @PutMapping("{id}")
    public Route update(@PathVariable String id, @Valid @RequestBody Route route) {
        if (!id.equals(route.getId())) {
            throw new InvalidEntityException(
                    String.format("Entity ID='%s' is different from URL resource ID='%s'", route.getId(), id));
        }
        validateIsOwnerOfRoute(id);
        return routesService.update(route);
    }

    @DeleteMapping("{id}")
    public Route remove(@PathVariable String id) {
        validateIsOwnerOfRoute(id);
        return routesService.remove(id);
    }
    @RequestMapping(
            value = "/**",
            method = RequestMethod.OPTIONS
    )
    public ResponseEntity handle() {
        return new ResponseEntity(HttpStatus.OK);
    }
    private void validateIsOwnerOfRoute(String routeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Route route = routesService.findById(routeId);
        User user = usersService.findByUsername(authentication.getName());
        if(route.getCompany()==null || !route.getCompanyId().equals(user.getId())) {
                throw new InvalidEntityException("You cannot interact with other users routes");
        }
    }
}
