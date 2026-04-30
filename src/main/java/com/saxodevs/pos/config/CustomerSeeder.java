package com.saxodevs.pos.config;

import com.saxodevs.pos.model.Customer;
import com.saxodevs.pos.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerSeeder implements CommandLineRunner {

    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) {

        if (customerRepository.findByIsDefaultTrue().isEmpty()) {

            Customer generalCustomer = Customer.builder()
                    .isDefault(true)
                    .sku("CUT-00000000-000")
                    .email("cutomer@pos.com")
                    .firstName("General")
                    .lastName("Customer")
                    .phoneNumber("0000000000")
                    .build();

            customerRepository.save(generalCustomer);

            System.out.println("✅ Default customer created");
        }
    }
}
