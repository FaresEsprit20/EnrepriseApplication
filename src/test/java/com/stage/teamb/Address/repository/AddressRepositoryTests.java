//package com.stage.teamb.Address.repository;
//
//import com.stage.teamb.models.Address;
//import com.stage.teamb.models.Employee;
//import com.stage.teamb.repository.AddressRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.time.LocalDate;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@ActiveProfiles("test")
//@DataJpaTest
//public class AddressRepositoryTests {
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Autowired
//    private AddressRepository addressRepository;
//
//    @Test
//    public void testFindAddressByEmployeeId_ExistingEmployeeId() {
//        // Arrange
//        Long employeeId = 1L;
//        Address address = new Address(1L, "Rue", "Ville", "20 jump street");
//
//        Employee employee = new Employee();
//        employee.setId(1L);
//        employee.setNom("John");
//        employee.setPrenom("Doe");
//        employee.setEmail("emp@Gmail.com");
//        employee.setPoste("Emp");
//        employee.setDateN(LocalDate.now());
//        address.setEmployee(employee);
//        entityManager.persistAndFlush(address);
//
//        // Act
//        Optional<Address> result = addressRepository.findAddressByEmployeeId(employeeId);
//
//        // Assert
//        assertThat(result).isPresent();
//        assertThat(result.get().getId()).isEqualTo(1L);
//    }
//
//    @Test
//    public void testFindAddressByEmployeeId_NonExistingEmployeeId() {
//        // Arrange
//        Long employeeId = 999L;
//
//        // Act
//        Optional<Address> result = addressRepository.findAddressByEmployeeId(employeeId);
//
//        // Assert
//        assertThat(result).isEmpty();
//    }
//
//    // Add more test methods for other scenarios as needed
//}