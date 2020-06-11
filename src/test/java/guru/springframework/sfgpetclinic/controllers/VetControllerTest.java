package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.SpecialtyService;
import guru.springframework.sfgpetclinic.services.VetService;
import guru.springframework.sfgpetclinic.services.map.SpecialityMapService;
import guru.springframework.sfgpetclinic.services.map.VetMapService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class VetControllerTest {

    private static class ModelMapImpl implements Model {
        private Map<String, Object> attributes = new HashMap<>();

        @Override
        public void addAttribute(String key, Object o) {
            attributes.put(key, o);
        }

        @Override
        public void addAttribute(Object o) {
            throw new RuntimeException("Not implemented");
        }

        public Map<String, Object> getAttributes() {
            return attributes;
        }
    }

    @Test
    void listVetsMyFirstApproach() {

        ModelMapImpl model = new ModelMapImpl();

        SpecialtyService specialtyService = new SpecialityMapService();

        VetService vetService = new VetMapService(specialtyService);

        Set<Speciality> specialities = Stream.of("foo", "bar", "buzz").map(Speciality::new).collect(Collectors.toSet());

        specialities.forEach(specialtyService::save);

        Vet vet1 = new Vet(null, "fn1", "ln1", specialities);
        Vet vet2 = new Vet(null, "fn2", "ln2", specialities);

        vetService.save(vet1);
        vetService.save(vet2);

        VetController vetController = new VetController(vetService);
        String listVets = vetController.listVets(model);
//        assertEquals("vets/index", listVets);
        assertAll("Mocking model and stubbing specialities ",
                () -> assertThat(listVets).isEqualTo("vets/index"),
                () -> {
                    Map<String, Object> modelAttributes = model.getAttributes();
                    assertThat((Set<Vet>) modelAttributes.get("vets")).containsOnlyOnce(vet1, vet2);
                }
        );

    }

    @Test
    void listVetsMySecondApproach() {

        ModelMapImpl modelMock = new ModelMapImpl();

        VetService vetServiceMock = new VetService() {
            private Map<Long, Vet> vets = new HashMap<>();

            @Override
            public Set<Vet> findAll() {
                return new HashSet<>(vets.values());
            }

            @Override
            public Vet findById(Long aLong) {
                return vets.get(aLong);
            }

            @Override
            public Vet save(Vet object) {
                return vets.put(object.getId(), object);
            }

            @Override
            public void delete(Vet object) {
                throw new RuntimeException("Not implemented");
            }

            @Override
            public void deleteById(Long aLong) {
                throw new RuntimeException("Not implemented");
            }
        };


        Vet vet1 = new Vet(1L, "fn1", "ln1", null);
        Vet vet2 = new Vet(2L, "fn2", "ln2", null);

        vetServiceMock.save(vet1);
        vetServiceMock.save(vet2);

        VetController vetController = new VetController(vetServiceMock);
        String listVets = vetController.listVets(modelMock);
        Map<String, Object> modelAttributes = modelMock.getAttributes();


        assertAll("Mocking VetService and Model",
                () -> assertThat(listVets).isEqualTo("vets/index"),
//                () -> assertEquals("vets/index", listVets),
//                () -> assertNotEquals("vets/index1", listVets),
//                () -> assertTrue(((Set<Vet>) modelAttributes.get("vets")).contains(vet1)),
//                () -> assertTrue(((Set<Vet>) modelAttributes.get("vets")).contains(vet2)),
                () -> assertThat((Set<Vet>) modelAttributes.get("vets")).containsOnlyOnce(vet1, vet2),
//                () -> assertFalse(((Set<Vet>) modelAttributes.get("vets")).contains(null))
                () -> assertThat((Set<Vet>) modelAttributes.get("vets")).doesNotContainNull()
        );


    }

    @Test
    void listVetsMyThirdApproach() {

        ModelMapImpl modelMock = new ModelMapImpl();

        Vet vet1 = new Vet(1L, "fn1", "ln1", null);
        Vet vet2 = new Vet(2L, "fn2", "ln2", null);

        VetService vetServiceMock = new VetService() {
            private Set<Vet> vets = Stream.of(vet1, vet2)
                    .collect(Collectors.toSet());

            @Override
            public Set<Vet> findAll() {
                return vets;
            }

            @Override
            public Vet findById(Long aLong) {
                throw new RuntimeException("Not implemented");
            }

            @Override
            public Vet save(Vet object) {
                throw new RuntimeException("Not implemented");
            }

            @Override
            public void delete(Vet object) {
                throw new RuntimeException("Not implemented");
            }

            @Override
            public void deleteById(Long aLong) {
                throw new RuntimeException("Not implemented");
            }
        };

        VetController vetController = new VetController(vetServiceMock);
        String listVets = vetController.listVets(modelMock);
        Map<String, Object> modelAttributes = modelMock.getAttributes();

        assertAll("Mocking VetService and Model",
                () -> assertThat(listVets).isEqualTo("vets/index"),
                () -> assertThat((Set<Vet>) modelAttributes.get("vets")).containsOnlyOnce(vet1, vet2),
                () -> assertThat((Set<Vet>) modelAttributes.get("vets")).hasSize(2),
                () -> assertThat((Set<Vet>) modelAttributes.get("vets")).doesNotContainNull()
        );


    }


}