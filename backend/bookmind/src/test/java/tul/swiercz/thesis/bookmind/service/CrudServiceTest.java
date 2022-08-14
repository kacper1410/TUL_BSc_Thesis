package tul.swiercz.thesis.bookmind.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.repository.CrudRepository;
import tul.swiercz.thesis.bookmind.domain.AbstractIdDomain;
import tul.swiercz.thesis.bookmind.exception.ExceptionMessages;
import tul.swiercz.thesis.bookmind.exception.NotFoundException;
import tul.swiercz.thesis.bookmind.mapper.AbstractMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CrudServiceTest {

    private TestCrudService testCrudService;

    @Mock
    private CrudRepository<TestIdDomain, Long> repository;

    @Mock
    private AbstractMapper<TestIdDomain> mapper;

    private List<TestIdDomain> testDomainList;

    private TestIdDomain testDomain1;

    private TestIdDomain testDomain2;

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void initFields() {
        testCrudService = new TestCrudService();

        testDomain1 = new TestIdDomain();
        testDomain1.setId(1L);
        testDomain2 = new TestIdDomain();

        testDomainList = new ArrayList<>();
        testDomainList.add(testDomain1);
        testDomainList.add(testDomain2);
    }

    @Test
    void getAll() {
        when(repository.findAll()).thenReturn(testDomainList);

        Iterable<TestIdDomain> testDomains = testCrudService.getAll();

        assertEquals(testDomainList, testDomains);
    }

    @Test
    void getById() throws NotFoundException {
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(testDomain1));

        TestIdDomain testDomain = testCrudService.getById(1L);

        assertEquals(testDomain1, testDomain);
    }

    @Test
    void getByIdException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> testCrudService.getById(1L)
        );

        assertEquals(ExceptionMessages.GET_NOT_FOUND, exception.getMessage());
    }

    @Test
    void create() {
        when(repository.save(testDomain1)).thenReturn(testDomain1);

        Long id = testCrudService.create(testDomain1);

        verify(repository).save(testDomain1);
        assertEquals(testDomain1.getId(), id);
    }

    @Test
    void update() throws NotFoundException {
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(testDomain1));

        testCrudService.update(1L, testDomain2);

        verify(mapper).update(testDomain2, testDomain1);
        verify(repository).save(testDomain1);
    }

    @Test
    void updateException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> testCrudService.update(1L, testDomain2)
        );

        assertEquals(ExceptionMessages.UPDATE_NOT_FOUND, exception.getMessage());
    }

    @Test
    void delete() {

        testCrudService.delete(1L);

        verify(repository).deleteById(1L);
    }

    private static class TestIdDomain extends AbstractIdDomain {
    }

    private class TestCrudService extends CrudService<TestIdDomain> {

        @Override
        protected CrudRepository<TestIdDomain, Long> getRepository() {
            return repository;
        }

        @Override
        protected AbstractMapper<TestIdDomain> getMapper() {
            return mapper;
        }

    }

}
