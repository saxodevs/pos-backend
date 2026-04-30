package com.saxodevs.pos.service.implementation;

import com.saxodevs.pos.dto.CustomerDTO;
import com.saxodevs.pos.mapper.CustomerMapper;
import com.saxodevs.pos.model.Customer;
import com.saxodevs.pos.model.User;
import com.saxodevs.pos.repository.CustomerRepository;
import com.saxodevs.pos.service.CustomerService;
import com.saxodevs.pos.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CustomerImplementation implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserService userService;
    private final CustomIdGenerator generator;

    @Override
    public CustomerDTO createCustomer(CustomerDTO dto) throws Exception {

        User user = userService.getCurrentUser();


        if (customerRepository.findEmail(dto.getEmail()) != null) throw new Exception("Customer email is used");

        if (customerRepository.findByPhone(dto.getPhoneNumber()) != null) throw new Exception("Customer phone is in used");

        dto.setSku(generator.generate("CTM"));

        Customer customer = CustomerMapper.toEntity(dto, user);

        Customer savedCustomer = customerRepository.save(customer);

        return CustomerMapper.toDTO(savedCustomer);
    }

    @Override
    public CustomerDTO updateCustomer(UUID id, CustomerDTO dto) throws Exception {

        Customer existingCustomer = customerRepository.findById(id).orElseThrow(
                () -> new Exception("Customer not found by ID: " + id)
        );

        User user = userService.getCurrentUser();

        existingCustomer.setEmail(dto.getEmail());
        existingCustomer.setFirstName(dto.getFirstName());
        existingCustomer.setLastName(dto.getLastName());
        existingCustomer.setPhoneNumber(dto.getPhoneNumber());
        existingCustomer.setUpdatedBy(user);
        existingCustomer.setUpdatedAt(dto.getUpdatedAt());

        Customer updatedCustomer = customerRepository.save(existingCustomer);

        return CustomerMapper.toDTO(updatedCustomer);
    }

    @Override
    public List<CustomerDTO> getAll() throws Exception {
        List<Customer> customers = customerRepository.findByDeletedFalse();

        if (customers.isEmpty()){
            throw new Exception("No customer added yet");
        }
        return CustomerMapper.toDTOList(customers);
    }

    @Override
    public void deleteCustomer(UUID id) throws Exception {

        Customer existingCustomer = customerRepository.findById(id).orElseThrow(
                () -> new Exception("Customer not found by ID:" + id)
        );

        existingCustomer.setDeleted(true);
        existingCustomer.setDeletedBy(userService.getCurrentUser());
        customerRepository.save(existingCustomer);

    }

    @Override
    public CustomerDTO getById(UUID id) throws Exception {

        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new Exception("Customer not found by ID: " + id)
        );
        return CustomerMapper.toDTO(customer);
    }

    @Override
    public CustomerDTO getByName(String name) throws Exception {

        Customer customer = customerRepository.findByName(name);

        if (customer == null){
            throw new Exception("Customer not found by Name: " + name);
        }
        return CustomerMapper.toDTO(customer);
    }

    @Override
    public CustomerDTO getByPhone(String phone) throws Exception {
        Customer customer = customerRepository.findByPhone(phone);

        if (customer == null){
            throw new Exception("Customer not found by Phone: " + phone);
        }
        return CustomerMapper.toDTO(customer);
    }

    @Override
    public CustomerDTO getByEmail(String email) throws Exception {
        Customer customer = customerRepository.findEmail(email);

        if (customer == null){
            throw new Exception("Customer not found by Email: " + email);
        }
        return CustomerMapper.toDTO(customer);
    }
}
