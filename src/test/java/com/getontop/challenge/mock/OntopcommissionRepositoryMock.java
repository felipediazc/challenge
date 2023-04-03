package com.getontop.challenge.mock;

import com.getontop.challenge.db.entity.Ontopcommission;
import com.getontop.challenge.db.repository.OntopcommissionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
public class OntopcommissionRepositoryMock {

    @Bean
    OntopcommissionRepository ontopcommissionRepositoryComponent() {
        Ontopcommission ontopcommission = new Ontopcommission();
        ontopcommission.setId(1);
        OntopcommissionRepository ontopcommissionRepository = mock(OntopcommissionRepository.class);
        when(ontopcommissionRepository.save(any())).thenReturn(ontopcommission);
        return ontopcommissionRepository;
    }
}
