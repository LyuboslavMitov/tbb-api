package com.tbb.demo;


import com.tbb.demo.domain.RoutesService;
import com.tbb.demo.domain.TicketsService;
import com.tbb.demo.domain.UsersService;
import com.tbb.demo.model.Route;
import com.tbb.demo.model.Ticket;
import com.tbb.demo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;

@Component
@Slf4j
public class DataInitializer implements ApplicationRunner {
    @Autowired
    private UsersService usersService;

    @Autowired
    private TicketsService ticketsService;

    @Autowired
    private RoutesService routesService;

    @Override
    public void run(ApplicationArguments args) {
        if (usersService.count() == 0) {
            User travelerUser = new User("traveller", "traveller123", "Method", "Man", "ROLE_TRAVELER", true);
            User companyUser = new User("alexiev", "company123", "Alex", "Man", "ROLE_COMPANY", true);
            usersService.add(travelerUser);
            usersService.add(companyUser);

            Route route = new Route(1234567L, "Vidin", "Sofia",
                    Collections.singletonList(""), "5.5h",
                    25d, 200, companyUser.getFirstName(), companyUser.getId()
            );
            routesService.add(route);

            Ticket ticket = new Ticket(123456L, "Vidin", "Sofia", Collections.singletonList(""),
                    "5.5h", 25d, "200", companyUser.getUsername(), LocalDateTime.now().toString(),
                    travelerUser.getId()
            );
            ticketsService.add(ticket);

            log.info("Initialized users....");
        }
    }
}
