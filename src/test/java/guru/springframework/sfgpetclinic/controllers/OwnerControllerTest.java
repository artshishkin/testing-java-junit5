package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    private static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    private static final String REDIRECT_OWNERS_5 = "redirect:/owners/5";

    @Mock
    OwnerService ownerServiceMock;

    @Mock
    BindingResult resultMock;

    @InjectMocks
    OwnerController controller;

    //second way to create captor
    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;


    @Test
    void processCreationFormResultHasErrors() {
        //given
        given(resultMock.hasErrors()).willReturn(true);
        //when
        String viewName = controller.processCreationForm(null, resultMock);
        //then
        then(resultMock).should().hasErrors();
        assertThat(viewName).isEqualToIgnoringCase(OWNERS_CREATE_OR_UPDATE_OWNER_FORM);
    }

    @Test
    void processCreationFormResultNoErrors() {
        //given
        Owner owner = new Owner(5L, "Foo", "Bar");
        given(resultMock.hasErrors()).willReturn(false);
        given(ownerServiceMock.save(any(Owner.class))).willReturn(owner);

        //when
        String viewName = controller.processCreationForm(owner, resultMock);

        //then
        then(ownerServiceMock).should().save(any(Owner.class));
        then(resultMock).should().hasErrors();
        assertThat(viewName).isEqualToIgnoringCase(REDIRECT_OWNERS_5);
    }

    @Test
    void processCreationFormResult() {
        //given
        given(resultMock.hasErrors()).willReturn(false);
        Owner owner = new Owner(5L, "Foo", "Bar");
        given(ownerServiceMock.save(any(Owner.class))).willReturn(owner);

        //when
        String viewName = controller.processCreationForm(owner, resultMock);

        //then
        then(ownerServiceMock).should().save(any(Owner.class));
        then(resultMock).should().hasErrors();
        assertThat(viewName).isEqualToIgnoringCase(REDIRECT_OWNERS_5);
    }

    @Test
    void processFindFormWildcardString() {
        //given
        Owner owner = new Owner(1L, "Foo", "Bar");
        //first way to create captor
        final ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        given(ownerServiceMock.findAllByLastNameLike(argumentCaptor.capture())).willReturn(Collections.singletonList(owner));

        //when
        String viewName = controller.processFindForm(owner, resultMock, null);

        //then
        then(ownerServiceMock).should().findAllByLastNameLike(anyString());
        assertThat(argumentCaptor.getValue()).isEqualTo("%Bar%");
    }

    @Nested
    class UsingAnswersTests {

        @Mock
        private Model modelMock;

        @BeforeEach
        void setUp() {
            given(ownerServiceMock.findAllByLastNameLike(stringArgumentCaptor.capture()))
                    .willAnswer(invocation -> {
                        String name = invocation.getArgument(0);
                        if ("%Bar%".equalsIgnoreCase(name)) return singletonList(new Owner(1L, "Foo", "Bar"));
                        if ("%Buzz%".equalsIgnoreCase(name)) return Arrays.asList(
                                new Owner(2L, "Yahoo", "Buzz1"),
                                new Owner(3L, "Yabadabadoo", "Buzz2")
                        );
                        return emptyList();
                    });

        }

        @Test
        void processFindFormWildcardStringAnnotatedOneResult() {
            //given
            Owner owner = new Owner(-1L, null, "Bar");

            //when
            String viewName = controller.processFindForm(owner, resultMock, null);

            //then
            then(ownerServiceMock).shouldHaveNoMoreInteractions();
            then(ownerServiceMock).should().findAllByLastNameLike(anyString());
            assertThat(stringArgumentCaptor.getValue()).isEqualTo("%Bar%");
            assertThat(viewName).startsWith("redirect:/owners/");
            verifyNoInteractions(modelMock);
        }

        @Test
        void processFindFormWildcardStringAnnotatedNoResults() {
            //given
            Owner owner = new Owner(-1L, null, "NoResult");

            //when
            String viewName = controller.processFindForm(owner, resultMock, null);

            //then
            then(ownerServiceMock).should().findAllByLastNameLike(anyString());
            assertThat(stringArgumentCaptor.getValue()).isEqualTo("%NoResult%");
            assertThat(viewName).isEqualTo("owners/findOwners");
            verifyNoInteractions(modelMock);
        }

        @Test
        void processFindFormWildcardStringAnnotatedMultipleResults() {
            //given
            Owner owner = new Owner(-1L, null, "Buzz");
            InOrder inOrder = inOrder(modelMock, ownerServiceMock);

            //when
            String viewName = controller.processFindForm(owner, resultMock, modelMock);

            //then
            then(ownerServiceMock).should().findAllByLastNameLike(anyString());
            assertThat(stringArgumentCaptor.getValue()).isEqualTo("%Buzz%");
            assertThat(viewName).isEqualTo("owners/ownersList");
            inOrder.verify(ownerServiceMock).findAllByLastNameLike(anyString());
            inOrder.verify(modelMock, times(1)).addAttribute(anyString(), any());
            verifyNoMoreInteractions(modelMock);
        }
    }
}
