package com.stage.teamb.repository;

import com.stage.teamb.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {

    Optional<Address> findAddressByEmployeeId(Long employeeId);

}
