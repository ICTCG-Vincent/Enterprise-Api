package com.ictcg.service.impl;

import com.ictcg.service.models.Address;
import com.ictcg.service.models.Enterprise;
import com.ictcg.service.repository.AddressRepository;
import com.ictcg.service.repository.EnterpriseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class EnterpriseService {

    private final static Logger LOG = LoggerFactory.getLogger(EnterpriseService.class);

    private final EnterpriseRepository enterpriseRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public EnterpriseService(EnterpriseRepository enterpriseRepository, AddressRepository addressRepository) {
        this.enterpriseRepository = enterpriseRepository;
        this.addressRepository = addressRepository;
    }

    public Iterable<Enterprise> findAll() {
        return enterpriseRepository.findAll();
    }

    public Enterprise findBy(Integer id) {
        Optional<Enterprise> enterprise = enterpriseRepository.findById(id);
        if(enterprise.isPresent())
            return enterprise.get();
        return new Enterprise();
    }

    public Enterprise post(Enterprise enterprise){
        this.manageEnterpriseAddress(enterprise);
        this.manageEntepriseSiege(enterprise);

        enterpriseRepository.save(enterprise);
        LOG.info("Updated enterprise named {} ({})", enterprise.getName(), enterprise);
        return this.findBy(enterprise.getId());
    }

    public String delete(Integer id){
        Enterprise enterprise = this.findBy(id);
        enterpriseRepository.delete(enterprise);
        LOG.info("deleted enterprise id {} ({})", enterprise.getName(), enterprise);
        return "delete sucessfull";
    }

    private void manageEnterpriseAddress(Enterprise enterprise) {
        if(enterprise.getAddress() != null && !enterprise.getAddress().isEmpty()){
            this.updateAddresses(enterprise);
        }else{
            throw new IllegalStateException("No enterprise addresses are present, please create a list of addresses");
        }
    }

    private void manageEntepriseSiege(Enterprise enterprise) {
        if(enterprise.getSiege() == null){
            Set<Address> address = enterprise.getAddress();
            enterprise.setSiege(address.stream().findFirst().get());
        }else{
            boolean exist = enterprise.getAddress().stream()
                    .anyMatch(address -> address.getId() == enterprise.getSiege().getId());
            if(!exist){
                throw new IllegalStateException("The siege should be contains in the list of the enterprise address");
            }
        }
    }

    private void updateAddresses(Enterprise enterprise) {
        enterprise.getAddress().forEach(address ->
        {
            if(address.getId() != 0){
                Optional<Address> optionalAddress = addressRepository.findById(address.getId());
                if (optionalAddress.isPresent()){
                    Address addressToUpdate = optionalAddress.get();
                    addressToUpdate.setCity(address.getCity());
                    addressToUpdate.setStreet(address.getStreet());
                    addressRepository.save(addressToUpdate);
                }else{
                    throw new IllegalStateException("No address with id [%d] exist, remove incorrect id");
                }
            }else{
                addressRepository.save(address);
            }
        });
    }

}
