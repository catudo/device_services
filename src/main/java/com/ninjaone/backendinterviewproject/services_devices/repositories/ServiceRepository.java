package com.ninjaone.backendinterviewproject.services_devices.repositories;

import com.ninjaone.backendinterviewproject.services_devices.models.ServiceBusiness;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceRepository extends JpaRepository<ServiceBusiness, Long> {

    Optional<ServiceBusiness> findByType(String type);
}