package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.ControllerTests;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Owner Map Service Test - ")
class OwnerMapServiceTest {

    private PetService petService;
    private PetTypeService petTypeService;
    private OwnerMapService ownerMapService;

    @BeforeEach
    void setUp() {
        petService = new PetMapService();
        petTypeService = new PetTypeMapService();
        ownerMapService = new OwnerMapService(petTypeService, petService);

        System.out.println("Before each Level 1");
    }


    @Test
    @DisplayName("Verify Zero Owners")
    void ownersAreZero() {
        assertThat(ownerMapService.findAll()).isEmpty();
    }


    @Nested
    @DisplayName("Pet Type - ")
    class TestCreatePetTypes {

        private PetType dog;
        private PetType cat;

        @BeforeEach
        void setUp() {
            dog = new PetType(1L, "Dog");
            cat = new PetType(2L, "Cat");

            petTypeService.save(dog);
            petTypeService.save(cat);

            System.out.println("Before each Level 2");
        }

        @Test
        void testPetCount() {
            assertThat(petTypeService.findAll()).isNotEmpty().hasSize(2);
        }

        @Nested
        @DisplayName("Save Owner Tests - ")
        class SaveOwnerTests {


            @BeforeEach
            void setUp() {
                System.out.println("Before each Level 3");
                ownerMapService.save(new Owner(1L, "Before", "Each"));
            }

            @Test
            void ownerSave() {
                Pet kuzya = new Pet();
                Owner owner = new Owner(2L, "In Test", "Method");
                kuzya.setOwner(owner);
                kuzya.setPetType(dog);
                kuzya.setName("Kuzya");
                kuzya.setBirthDate(LocalDate.of(2009, Month.APRIL, 12));
                owner.getPets().add(kuzya);
                ownerMapService.save(owner);

                assertThat(ownerMapService.findAll()).isNotEmpty();
                assertThat(ownerMapService.findByLastName("Method")).isEqualTo(owner);

            }

            @Nested
            class FindOwnersTests {
                @BeforeEach
                void setUp() {
                    System.out.println("Before each Level 4");
                }

                @Test
                void findOwner() {
                    assertThat(ownerMapService.findById(1L)).isNotNull().hasFieldOrPropertyWithValue("firstName", "Before");
                }

                @Test
                void findOwnerNotFound() {
                    assertThat(ownerMapService.findById(2L)).isNull();
                }
            }
        }


    }


    @Test
    @DisplayName("Verify Still Zero Owners")
    void ownersAreStillZero() {
        assertThat(ownerMapService.findAll()).isEmpty();
    }


}