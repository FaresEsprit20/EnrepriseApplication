package com.stage.teamb.Address.service;


import com.stage.teamb.dtos.address.AddressDTO;
import com.stage.teamb.dtos.employee.EmployeeDTO;
import com.stage.teamb.exception.CustomException;
import com.stage.teamb.mappers.AddressMapper;
import com.stage.teamb.mappers.EmployeeMapper;
import com.stage.teamb.models.Address;
import com.stage.teamb.models.Employee;
import com.stage.teamb.repository.jpa.address.AddressRepository;
import com.stage.teamb.repository.jpa.employee.EmployeeRepository;
import com.stage.teamb.services.address.AddressServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
@Transactional
class AddressServiceTest {

    @InjectMocks
    private AddressServiceImpl addressService;

    @PersistenceContext
    EntityManager entityManager;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    EmployeeRepository employeeRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testFindAllAddressesSuccess() {
        // Arrange
        List<Address> addresses = new ArrayList<>();
        Address address1 = new Address();
        address1.setId(1L);
        address1.setStreet("123 Main St");
        // Set other address properties as needed
        addresses.add(address1);
        when(addressRepository.findAll()).thenReturn(addresses);
        // Act
        List<AddressDTO> result = addressService.findAllAddresses();
        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        AddressDTO resultAddress = result.get(0);
        assertEquals(1L, resultAddress.getId().longValue());
        assertEquals("123 Main St", resultAddress.getStreet());
        // Add more assertions for other address properties
    }

    @Test
    public void testFindAllAddressesFail() {
        // Arrange
        when(addressRepository.findAll()).thenThrow(new RuntimeException("Database connection error"));
        // Act and Assert
        assertThrows(CustomException.class, () -> addressService.findAllAddresses());
    }


    @Test
    public void testFindAddressByIdSuccess() {
        // Arrange
        Address address = new Address();
        address.setId(1L);
        address.setStreet("123 Main St");
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        // Act
        AddressDTO result = addressService.findAddressById(1L);
        // Assert
        assertEquals(1L, result.getId());
        assertEquals("123 Main St", result.getStreet());
    }

    @Test
    public void testFindAddressByIdFailure() {
        // Arrange
        when(addressRepository.findById(1L)).thenReturn(Optional.empty());
        // Act & Assert
        assertThrows(CustomException.class, () -> addressService.findAddressById(1L));
    }

    @Test
    public void testSaveAddressSuccess() {
        // Arrange
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet("123 Main St");
        // Set other addressDTO properties as needed
        Address addressToSave = AddressMapper.toEntity(addressDTO);
        Address savedAddress = new Address();
        savedAddress.setId(1L);
        savedAddress.setStreet("123 Main St");
        // Set other saved address properties as needed
        when(addressRepository.save(any())).thenReturn(savedAddress);
        // Act
        AddressDTO result = addressService.saveAddress(addressDTO);
        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId().longValue());
        assertEquals("123 Main St", result.getStreet());
        // Add more assertions for other address properties
    }

    @Test
    public void testSaveAddressFail() {
        // Arrange
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet("123 Main St");
        // Set other addressDTO properties as needed
        when(addressRepository.save(any())).thenThrow(new DataAccessException("Database insert error") {
        });
        // Act and Assert
        assertThrows(CustomException.class, () -> addressService.saveAddress(addressDTO));
    }

    @Test
    public void testDeleteAddressByIdSuccess() {
        // Arrange
        Long addressId = 1L;
        when(addressRepository.existsById(addressId)).thenReturn(true);
        // Act
        assertDoesNotThrow(() -> addressService.deleteAddressById(addressId));
        // Assert
        verify(addressRepository, times(1)).deleteById(addressId);
    }

    @Test
    public void testDeleteAddressByIdAddressNotFound() {
        // Arrange
        Long addressId = 1L;
        when(addressRepository.existsById(addressId)).thenReturn(false);
        // Act and Assert
        assertThrows(CustomException.class, () -> addressService.deleteAddressById(addressId));
    }

    @Test
    public void testDeleteAddressByIdDatabaseError() {
        // Arrange
        Long addressId = 1L;
        when(addressRepository.existsById(addressId)).thenReturn(true);
        doThrow(new DataAccessException("Database delete error") {}).when(addressRepository).deleteById(addressId);
        // Act and Assert
        assertThrows(CustomException.class, () -> addressService.deleteAddressById(addressId));
    }

    @Test
    public void testUpdateAddressSuccess() {
        // Arrange
        AddressDTO updatedAddressDTO = new AddressDTO();
        updatedAddressDTO.setId(1L);
        updatedAddressDTO.setStreet("Updated Street");
        updatedAddressDTO.setStreetCode("Updated Code");
        updatedAddressDTO.setTown("Updated Town");
        Address existingAddress = new Address();
        existingAddress.setId(1L);
        existingAddress.setStreet("Original Street");
        existingAddress.setStreetCode("Original Code");
        existingAddress.setTown("Original Town");
        when(addressRepository.findById(updatedAddressDTO.getId())).thenReturn(Optional.of(existingAddress));
        when(addressRepository.save(any(Address.class))).then(AdditionalAnswers.returnsFirstArg());
        // Act
        AddressDTO result = addressService.updateAddress(updatedAddressDTO);
        // Assert
        assertEquals(updatedAddressDTO.getId(), result.getId());
        assertEquals(updatedAddressDTO.getStreet(), result.getStreet());
        assertEquals(updatedAddressDTO.getStreetCode(), result.getStreetCode());
        assertEquals(updatedAddressDTO.getTown(), result.getTown());
        verify(addressRepository, times(1)).findById(updatedAddressDTO.getId());
        verify(addressRepository, times(1)).save(existingAddress);
    }

    @Test
    public void testUpdateAddressFailAndError() {
        // Arrange
        AddressDTO updatedAddressDTO = new AddressDTO();
        updatedAddressDTO.setId(1L);
        updatedAddressDTO.setStreet("Updated Street");
        updatedAddressDTO.setStreetCode("Updated Code");
        updatedAddressDTO.setTown("Updated Town");
        when(addressRepository.findById(updatedAddressDTO.getId())).thenReturn(Optional.empty());
        doThrow(new DataAccessException("Database update error") {}).when(addressRepository).save(any(Address.class));
        // Act and Assert
        assertThrows(CustomException.class, () -> addressService.updateAddress(updatedAddressDTO));
    }

    @Test
    public void testFindAddressesByEmployeeIdSuccess() {
        // Arrange
        Employee employee = new Employee();
        employee.setId(1L);
        Address address1 = new Address();
        address1.setId(1L);
        address1.setStreet("123 Main St");
        Address address2 = new Address();
        address2.setId(2L);
        address2.setStreet("456 Elm St");
        List<Address> addresses = List.of(address1, address2);
        employee.setAddresses(addresses);
        when(addressRepository.findAddressesByEmployeeId(1L)).thenReturn(addresses);
        // Act
        List<AddressDTO> results = addressService.findAddressesByEmployeeId(1L);
        // Assert
        assertEquals(2, results.size());
    }

    @Test
    public void testFindAddressesByEmployeeIdFailure() {
        // Arrange
        when(addressRepository.findAddressesByEmployeeId(1L)).thenReturn(List.of());
        // Act & Assert
        assertThrows(CustomException.class, () -> addressService.findAddressesByEmployeeId(1L));
    }

    @Test
    public void testAssociateEmployeeWithAddressSuccess() {
        // Success scenario
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(1L);
        addressDTO.setStreet("Sample Street");
        addressDTO.setStreetCode("12345");
        addressDTO.setTown("Sample Town");
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1L);
        employeeDTO.setName("John Doe");
        Address addressEntity = AddressMapper.toEntity(addressDTO);
        Employee employeeEntity = EmployeeMapper.toEntity(employeeDTO);
        Mockito.doReturn(Optional.of(addressEntity)).when(addressRepository).findById(addressDTO.getId());
        Mockito.doReturn(Optional.of(employeeEntity)).when(employeeRepository).findById(employeeDTO.getId());
        Mockito.doReturn(addressEntity).when(addressRepository).save(any(Address.class));
        AddressDTO result = addressService.associateEmployeeWithAddress(addressDTO.getId(), employeeDTO.getId());
        assertNotNull(result);
    }

    @Test
    public void testAssociateEmployeeWithAddressError() {
        // Error scenario
        Long invalidAddressId = 999L; // An invalid address ID
        Long invalidEmployeeId = 999L; // An invalid employee ID
        Mockito.doThrow(new CustomException("Address not found", 404))
                .when(addressRepository)
                .findById(invalidAddressId);
        Mockito.doThrow(new CustomException("Employee not found", 404))
                .when(employeeRepository)
                .findById(invalidEmployeeId);

        try {
            addressService.associateEmployeeWithAddress(invalidAddressId, invalidEmployeeId);
            // If the method doesn't throw an exception as expected, the test will fail
            fail("Expected CustomException was not thrown");
        } catch (CustomException e) {
            // Verify that the correct exception was thrown
            assertEquals("Address not found", e.getMessage());
            assertEquals(404, e.getStatus_code());
        }
    }


    private int countSqlQueries(Runnable operation) {
        SessionFactory sessionFactory = entityManager.getEntityManagerFactory().unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);
        sessionFactory.getStatistics().clear();
        operation.run();
        return (int) sessionFactory.getStatistics().getEntityLoadCount();
    }


}