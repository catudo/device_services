package com.ninjaone.backendinterviewproject.services_devices.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "services")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceBusiness {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "type", length = 100)
    private String type;

    @OneToMany(mappedBy = "serviceBusiness")
    @JsonIgnore
    private Set<DevicesService> devicesServices = new LinkedHashSet<>();


}