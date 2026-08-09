package net.space.developer.googlemapcrawler.services;

import lombok.RequiredArgsConstructor;
import net.space.developer.googlemapcrawler.constants.CustomerConstants;
import net.space.developer.googlemapcrawler.dto.CustomerDto;
import net.space.developer.googlemapcrawler.repository.CustomerRepository;
import net.space.developer.googlemapcrawler.utils.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService{

    private final CustomerRepository customerRepository;

    @Override
    public Flux<CustomerDto> findAll() {
        return customerRepository.findAll()
                .map(Utils::customerEntityToDto);
    }

    @Override
    public Mono<CustomerDto> saveCustomer(CustomerDto customerDto) {
        return Mono.just(customerDto)
                .map(Utils::customersDtoToEntity)
                .flatMap(customerRepository::insert)
                .map(Utils::customerEntityToDto);
    }

    @Override
    public Mono<CustomerDto> findCustomerById(String id) {
        return customerRepository.findById(id)
                .map(Utils::customerEntityToDto);
    }

    @Override
    public Mono<CustomerDto> findCustomerByUserId(String userId) {
        return customerRepository.findCustomersByUserId(userId)
                .map(Utils::customerEntityToDto);
    }

    @Override
    public Mono<CustomerDto> updateCustomer(String id, CustomerDto customerDto) {
        return customerRepository.findById(id)
                .switchIfEmpty(Mono.error(new Exception(
                        String.format(CustomerConstants.CUSTOMER_NOT_FOUND, id)
                )))
                .flatMap(i -> {
                    BeanUtils.copyProperties(customerDto, i);
                    i.setId(id);
                    return customerRepository.save(i);
                })
                .map(Utils::customerEntityToDto);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return customerRepository.deleteById(id);
    }
}
