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
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    VisitSDJpaService service;

    @Test
    void findAll() {
        //given
        Set<Visit> visitsExpected = singleton(new Visit());
        given(visitRepository.findAll()).willReturn(visitsExpected);
        //when
        Set<Visit> visitsActual = service.findAll();
        //then
        then(visitRepository).should().findAll();
        assertThat(visitsActual).hasSize(1).isEqualTo(visitsExpected);
    }

    @Test
    void findById() {
        //given
        long id = 1L;
        Visit visitExpected = new Visit(id);
        given(visitRepository.findById(anyLong())).willReturn(Optional.of(visitExpected));
        //when
        Visit visitActual = service.findById(id);
        //then
        then(visitRepository).should().findById(id);
        assertThat(visitActual).isEqualTo(visitExpected);
    }

    @Test
    void save() {

        //given
        Visit visitExpected = new Visit(2L, LocalDate.now());
        given(visitRepository.save(any(Visit.class))).willReturn(visitExpected);

        //when
        Visit savedVisit = service.save(visitExpected);

        //then
        then(visitRepository).should().save(any(Visit.class));
        assertThat(savedVisit)
                .isNotNull()
                .isEqualToComparingFieldByField(visitExpected);
    }

    @Test
    void delete() {
        //given
        Visit visit = new Visit();
        //when
        service.delete(visit);
        //then
        then(visitRepository).should().delete(any(Visit.class));
    }

    @Test
    void deleteById() {
        //given
        long id = 33L;
        //when
        service.deleteById(id);
        //then
        then(visitRepository).should().deleteById(id);
    }
}
