package tul.swiercz.thesis.bookmind.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.repository.CrudRepository;
import tul.swiercz.thesis.bookmind.domain.AbstractDomain;
import tul.swiercz.thesis.bookmind.mapper.AbstractMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CrudServiceTest {

    private TestCrudService testCrudService;

    @Mock
    private CrudRepository<TestDomain, Long> repository;

    @Mock
    private AbstractMapper<TestDomain> mapper;

    private List<TestDomain> testDomainList;

    private TestDomain testDomain1;

    private TestDomain testDomain2;

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.openMocks(this);
        testCrudService = new TestCrudService();
    }

    @BeforeEach
    void initFields() {
        testDomain1 = new TestDomain();
        testDomain1.setId(1L);
        testDomain2 = new TestDomain();

        testDomainList = new ArrayList<>();
        testDomainList.add(testDomain1);
        testDomainList.add(testDomain2);
    }

    @Test
    void getAll() {
        when(repository.findAll()).thenReturn(testDomainList);

        Iterable<TestDomain> testDomains = testCrudService.getAll();

        assertEquals(testDomainList, testDomains);
    }

    @Test
    void getById() {
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(testDomain1));

        TestDomain testDomain = testCrudService.getById(1L);

        assertEquals(testDomain1, testDomain);
    }

    @Test
    void create() {
        when(repository.save(testDomain1)).thenReturn(testDomain1);

        Long id = testCrudService.create(testDomain1);

        verify(repository).save(testDomain1);
        assertEquals(testDomain1.getId(), id);
    }

    @Test
    void update() {
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(testDomain1));

        testCrudService.update(1L, testDomain2);

        verify(mapper).update(testDomain2, testDomain1);
        verify(repository).save(testDomain1);
    }

    @Test
    void delete() {

        testCrudService.delete(1L);

        verify(repository).deleteById(1L);
    }

    private static class TestDomain extends AbstractDomain {
    }

    private class TestCrudService extends CrudService<TestDomain> {

        @Override
        protected CrudRepository<TestDomain, Long> getRepository() {
            return repository;
        }

        @Override
        protected AbstractMapper<TestDomain> getMapper() {
            return mapper;
        }

    }

}
