package com.stage.teamb.repository.jpa.address;

import com.stage.teamb.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {

    @Query("SELECT a FROM Address a JOIN FETCH a.employee e WHERE e.id = :employeeId")
    List<Address> findAddressesByEmployeeId(Long employeeId);

    @Query("SELECT a FROM Address a LEFT JOIN FETCH a.employee WHERE a.id = :addressId")
    Optional<Address> findByIdWithEmployee(Long addressId);

    @Query("SELECT a FROM Address a LEFT JOIN FETCH a.employee WHERE a.id = :addressId")
    Address findByIdEagerly(Long addressId);



}
