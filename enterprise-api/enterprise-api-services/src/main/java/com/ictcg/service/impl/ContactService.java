package com.ictcg.service.impl;

import com.ictcg.service.models.Contact;
import com.ictcg.service.repository.ContactRepository;
import com.ictcg.service.repository.EnterpriseRepository;
import com.ictcg.service.types.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class ContactService {

    private final static Logger LOG = LoggerFactory.getLogger(ContactService.class);

    private final ContactRepository contactRepository;
    private final EnterpriseRepository enterpriseRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository,
                          EnterpriseRepository enterpriseRepository) {
        this.contactRepository = contactRepository;
        this.enterpriseRepository = enterpriseRepository;
    }

    public Iterable<Contact> findAll() {
        return contactRepository.findAll();
    }

    public Contact findBy(Integer id) {
        Optional<Contact> contact = contactRepository.findById(id);
        if(contact.isPresent()){
            return contact.get();
        }
        throw new IllegalStateException(String.format("No contact has been found with id [%d]", id));
    }

    public Contact post(Contact contact){
        if(contact.getEnterprise() != null){
            contact.getEnterprise().forEach(enterprise -> {
                boolean exists = this.enterpriseRepository.existsById(enterprise.getId());
                if(!exists){
                    throw new IllegalStateException("Please create the enterprise before assign it to the contact");
                };
            });
        }

        contactRepository.save(contact);
        LOG.info("Updated contact named {} and firstName {}({})", contact.getName(), contact.getFirstName(), contact);
        return this.findBy(contact.getId());
    }

    public String delete(Integer id){
        Contact contact = this.findBy(id);
        if(contact.getId() != 0){
            contactRepository.delete(contact);
        }else{
            throw new IllegalStateException(String.format("No contact has been found with id [%d]", id));
        }
        return String.format("Delete successfull for id [%d]", id);
    }
}
