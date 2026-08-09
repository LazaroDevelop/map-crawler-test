package net.space.developer.googlemapcrawler.controller;

import lombok.RequiredArgsConstructor;
import net.space.developer.googlemapcrawler.constants.GoogleMapCrawlerConstants;
import net.space.developer.googlemapcrawler.dto.CustomerDto;
import net.space.developer.googlemapcrawler.services.ICustomerService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(GoogleMapCrawlerConstants.BASE_URL)
public class CustomerController {

    private final ICustomerService customerService;

    @GetMapping("/customer/all")
    Flux<CustomerDto> findAllCustomers(){
        return customerService.findAll();
    }

    @GetMapping("/customer/{id}")
    Mono<CustomerDto> findCustomerById(@PathVariable("id") String id){
        return customerService.findCustomerById(id);
    }

    @GetMapping("/customer/user/{id}")
    Mono<CustomerDto> findCustomerUserById(@PathVariable("id") String id){
        return customerService.findCustomerByUserId(id);
    }

    @PostMapping("/customer/save")
    Mono<CustomerDto> saveCustomer(@RequestBody CustomerDto customerDto){
        return customerService.saveCustomer(customerDto);
    }

    @PutMapping("/customer/update/{id}")
    Mono<CustomerDto> updateCustomer(@PathVariable("id") String id, @RequestBody CustomerDto customerDto){
        return customerService.updateCustomer(id, customerDto);
    }

    @DeleteMapping("/customer/delete/{id}")
    Mono<Void> deleteCustomer(@PathVariable("id") String id){
        return customerService.deleteById(id);
    }
}
