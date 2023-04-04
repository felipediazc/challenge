package com.getontop.challenge.domain;

import com.getontop.challenge.db.entity.Ontopcommission;
import com.getontop.challenge.db.repository.OntopcommissionRepository;
import org.springframework.stereotype.Service;

@Service
public class OntopcommissionService {

    private final OntopcommissionRepository ontopcommissionRepository;

    public OntopcommissionService(OntopcommissionRepository ontopcommissionRepository) {

        this.ontopcommissionRepository = ontopcommissionRepository;
    }

    public Ontopcommission save(Ontopcommission ontopcommission) {
        return ontopcommissionRepository.save(ontopcommission);
    }
}
