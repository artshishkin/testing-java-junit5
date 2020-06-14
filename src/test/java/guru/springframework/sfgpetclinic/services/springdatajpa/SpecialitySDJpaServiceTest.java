package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock
    SpecialtyRepository specialtyRepository;

    @InjectMocks
    SpecialitySDJpaService service;

    @Test
    void deleteByIdOnce() {
        //given
        long id = 1L;
        //when
        service.deleteById(id);
        //then
        then(specialtyRepository).should().deleteById(id);
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void deleteByIdTwice() {
        //when
        service.deleteById(1L);
        service.deleteById(2L);
        //then
        then(specialtyRepository).should(times(2)).deleteById(anyLong());
    }

    @Test
    void deleteByIdAtLeastOnce() {
        //when
        service.deleteById(1L);
        service.deleteById(2L);
        //then
        then(specialtyRepository).should(atLeastOnce()).deleteById(anyLong());
    }

    @Test
    void deleteByIdAtLeastNTimes() {
        //given - none
        //when
        service.deleteById(1L);
        service.deleteById(2L);
        service.deleteById(3L);
        //then
        then(specialtyRepository).should(atLeast(2)).deleteById(anyLong());
    }

    @Test
    void deleteByIdAtMostNTimes() {
        //given - none
        //when
        service.deleteById(1L);
        service.deleteById(2L);
        service.deleteById(3L);
        //then
        then(specialtyRepository).should(atMost(5)).deleteById(anyLong());
    }

    @Test
    void deleteByIdNever() {
        //given - none
        //when
        service.deleteById(1L);
        service.deleteById(1L);
        service.deleteById(1L);
        //then
        then(specialtyRepository).should(atLeastOnce()).deleteById(1L);
        then(specialtyRepository).should(never()).deleteById(2L);
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }


    @Test
    void findByIdTest() {
        //given
        Speciality speciality = new Speciality();
        long id = 2L;
        when(specialtyRepository.findById(anyLong())).thenReturn(Optional.of(speciality));
        //when
        Speciality specialityActual = service.findById(id);
        //then
        assertThat(specialityActual).isSameAs(speciality);
        then(specialtyRepository).should().findById(id);
    }

    @Test
    void deleteByObject() {
        //given
        Speciality speciality = new Speciality();
        //when
        service.delete(speciality);
        //then
        then(specialtyRepository).should().delete(any(Speciality.class));
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }


    //BDD Section
// OLD VERSION
//    @Test
//    void findByIdTest() {
//        Speciality speciality = new Speciality();
//        long id = 2L;
//        when(specialtyRepository.findById(id)).thenReturn(Optional.of(speciality));
//        assertThat(service.findById(id)).isSameAs(speciality);
//        verify(specialtyRepository).findById(anyLong());
//    }

    // BDD VERSION
// given-when-then
    @Test
    void findByIdBddTest() {
        //given
        Speciality speciality = new Speciality();
        given(specialtyRepository.findById(anyLong())).willReturn(Optional.of(speciality));
        //when
        Speciality speciality1 = service.findById(1L);
        Speciality speciality2 = service.findById(2L);
        //then
        assertThat(speciality1).isNotNull();
        assertThat(speciality2).isNotNull();
        then(specialtyRepository).should(times(2)).findById(anyLong());
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }


}
