package com.tbb.demo.domain;


import com.tbb.demo.dao.RoutesRepository;
import com.tbb.demo.exception.NonexisitngEntityException;
import com.tbb.demo.model.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RoutesService {
    @Autowired
    private RoutesRepository repo;

    public List<Route> findAllCompanyRoutes(String companyId) {
        return repo.findAllByCompanyId(companyId);
    }

    public List<Route> findAllRoutes() {return repo.findAll();}

    public Route findById(String routeId) {
        return repo.findById(routeId).orElseThrow(() -> new NonexisitngEntityException(
                String.format("Route with ID='%s' does not exist.", routeId)));
    }

    public Route add(Route Route) {
        return repo.insert(Route);
    }

    public Route update(Route route) {
        Optional<Route> old = repo.findById(route.getId());
        if (!old.isPresent()) {
            throw new NonexisitngEntityException(
                    String.format("Route with ID='%s' does not exist.", route.getId()));
        }
        return repo.save(route);
    }

    public Route remove(String routeId) {
        Optional<Route> old = repo.findById(routeId);
        log.info("!!!!!! RouteID = " + routeId);
        if (!old.isPresent()) {
            throw new NonexisitngEntityException(
                    String.format("Route with ID='%s' does not exist.", routeId));
        }
        repo.deleteById(routeId);
        return old.get();
    }
}
