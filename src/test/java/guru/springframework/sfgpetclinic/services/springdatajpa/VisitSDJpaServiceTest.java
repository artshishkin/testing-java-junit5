package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    VisitSDJpaService service;

    @Test
    void findAll() {
        Set<Visit> visitsExpected = singleton(new Visit());
        doReturn(visitsExpected).when(visitRepository).findAll();
        Set<Visit> visitsActual = service.findAll();
        verify(visitRepository).findAll();
        assertThat(visitsActual).hasSize(1).isEqualTo(visitsExpected);
    }

    @Test
    void findById1() {
        long id = 1L;
        Visit visitExpected = new Visit(id);
        doReturn(Optional.of(visitExpected)).when(visitRepository).findById(anyLong());
        Visit visitActual = service.findById(id);
        verify(visitRepository).findById(id);
        assertThat(visitActual).isEqualTo(visitExpected);
    }

    @Test
    void findById2() {
        long id = 2L;
        when(visitRepository.findById(anyLong())).thenReturn(Optional.of(new Visit()));
        Visit visitActual = service.findById(id);
        verify(visitRepository).findById(anyLong());
        assertThat(visitActual).isNotNull();
    }

    @Test
    void save() {
        Visit visitExpected = new Visit(2L, LocalDate.now());
        when(visitRepository.save(any(Visit.class))).thenReturn(visitExpected);
        Visit savedVisit = service.save(visitExpected);
        verify(visitRepository).save(any(Visit.class));
        assertThat(savedVisit)
                .isNotNull()
                .isEqualToComparingFieldByField(visitExpected);
    }

    @Test
    void delete() {
        service.delete(new Visit());
        verify(visitRepository).delete(any(Visit.class));
    }

    @Test
    void deleteById() {
        long id = 1L;
        service.deleteById(id);
        verify(visitRepository).deleteById(id);
    }
}
