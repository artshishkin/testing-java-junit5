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
    void delete() {
        service.delete(new Speciality());
    }

    @Test
    void deleteByIdOnce() {
        service.deleteById(1L);
        verify(specialtyRepository).deleteById(1L);
    }

    @Test
    void deleteByIdTwice() {
        service.deleteById(1L);
        service.deleteById(1L);
        verify(specialtyRepository, times(2)).deleteById(1L);
    }

    @Test
    void deleteByIdAtLeastOnce() {
        service.deleteById(1L);
        service.deleteById(1L);
        verify(specialtyRepository, atLeastOnce()).deleteById(1L);
    }

    @Test
    void deleteByIdAtLeastNTimes() {
        service.deleteById(1L);
        service.deleteById(1L);
        service.deleteById(1L);
        verify(specialtyRepository, atLeast(2)).deleteById(1L);
    }

    @Test
    void deleteByIdAtMostNTimes() {
        service.deleteById(1L);
        service.deleteById(1L);
        service.deleteById(1L);
        verify(specialtyRepository, atMost(5)).deleteById(1L);
    }

    @Test
    void deleteByIdNever() {
        service.deleteById(1L);
        service.deleteById(1L);
        service.deleteById(1L);
        verify(specialtyRepository, atLeastOnce()).deleteById(1L);
        verify(specialtyRepository, never()).deleteById(2L);
    }


    @Test
    void findByIdTest() {
        Speciality speciality = new Speciality();
        long id = 2L;
        when(specialtyRepository.findById(id)).thenReturn(Optional.of(speciality));
        assertThat(service.findById(id)).isSameAs(speciality);
//        verify(specialtyRepository).findById(id);
        verify(specialtyRepository).findById(anyLong());
    }

    @Test
    void deleteByObject() {
        Speciality speciality = new Speciality();
        service.delete(speciality);
        verify(specialtyRepository).delete(any(Speciality.class));
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
