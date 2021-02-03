package com.ictcg.service.repository;

import com.ictcg.service.models.Address;
import com.ictcg.service.models.Enterprise;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Integer> {

}
