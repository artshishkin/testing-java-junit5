package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock(lenient = true)
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
        then(specialtyRepository).should(timeout(100).times(2)).deleteById(anyLong());
    }

    @Test
    void deleteByIdAtLeastOnce() {
        //when
        service.deleteById(1L);
        service.deleteById(2L);
        //then
        then(specialtyRepository).should(timeout(100).atLeastOnce()).deleteById(anyLong());
    }

    @Test
    void deleteByIdAtLeastNTimes() {
        //given - none
        //when
        service.deleteById(1L);
        service.deleteById(2L);
        service.deleteById(3L);
        //then
        then(specialtyRepository).should(timeout(10).atLeast(2)).deleteById(anyLong());
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
        then(specialtyRepository).should(timeout(100)).findById(id);
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

    @Test
    void testDoThrow() {
        doThrow(new RuntimeException("boom")).when(specialtyRepository).delete(any());
        Assertions.assertThrows(RuntimeException.class, () -> service.delete(null));
        verify(specialtyRepository).delete(any());
    }

    @Test
    void testReturnVoidThrowBDD() {
        //given
        willThrow(new RuntimeException("boom")).given(specialtyRepository).delete(any());
        //when
        Assertions.assertThrows(RuntimeException.class, () -> service.delete(null));
        //then
        then(specialtyRepository).should().delete(any());
//        given(specialtyRepository.deleteById(anyLong())).willThrow(new RuntimeException("bang")); //some problems - return void
    }

    @Test
    void testFindByIdThrows() {
        //given
        given(specialtyRepository.findById(anyLong())).willThrow(new RuntimeException("boom"));
        //when
        Executable executable = () -> service.findById(-1L);
        //then
        Assertions.assertThrows(RuntimeException.class, executable);
        then(specialtyRepository).should().findById(anyLong());
    }

    @Test
    void testSaveLambda() {
        //given
        final String MATCH_ME = "Match me";
        Speciality speciality = new Speciality(MATCH_ME);

        Speciality savedSpeciality = new Speciality();
        savedSpeciality.setId(1L);
        //need mock to only return on match MATCH_ME
        given(specialtyRepository.save(argThat(argument -> argument.getDescription().equals(MATCH_ME)))).willReturn(savedSpeciality);
//        willReturn(savedSpeciality).given(specialtyRepository).save(argThat(argument -> argument.getDescription().equals(MATCH_ME)));
//        doReturn(savedSpeciality).when(specialtyRepository).save(argThat(argument -> argument.getDescription().equals(MATCH_ME)));
//        when(specialtyRepository.save(argThat(argument -> argument.getDescription().equals(MATCH_ME)))).thenReturn(savedSpeciality);

        //when
        Speciality returnedSpeciality = service.save(speciality);

        //then
        assertThat(returnedSpeciality.getId()).isEqualTo(1L);
    }

    @Test
    void testSaveLambdaNotMatch() {
        //given
        final String MATCH_ME = "Match me";
        Speciality speciality = new Speciality("Not match");

        Speciality savedSpeciality = new Speciality();
        savedSpeciality.setId(1L);
        given(specialtyRepository.save(argThat(argument -> argument.getDescription().equals(MATCH_ME)))).willReturn(savedSpeciality);

        //when
        Speciality returnedSpeciality = service.save(speciality);

        //then
        assertThat(returnedSpeciality).isNull();
    }

    @Test
    void testSaveTimeout() {
        //given
        given(specialtyRepository.save(any(Speciality.class))).willReturn(new Speciality());

        //when
        new Thread(()->{
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
            service.save(new Speciality());
        }).start();

        //then
        then(specialtyRepository).should(timeout(50).times(0)).save(any(Speciality.class));
        then(specialtyRepository).should(timeout(150)).save(any(Speciality.class));
    }
}
