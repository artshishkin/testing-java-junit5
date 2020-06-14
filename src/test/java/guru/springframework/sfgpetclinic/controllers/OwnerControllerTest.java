package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    private static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    private static final String REDIRECT_OWNERS_5 = "redirect:/owners/5";

    @Mock
    OwnerService ownerServiceMock;

    @Mock
    BindingResult resultMock;

    @Mock
    Owner ownerMock;

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
        given(resultMock.hasErrors()).willReturn(false);
        given(ownerMock.getId()).willReturn(5L);
        given(ownerServiceMock.save(any(Owner.class))).willReturn(ownerMock);

        //when
        String viewName = controller.processCreationForm(ownerMock, resultMock);

        //then
        then(ownerServiceMock).should().save(any(Owner.class));
        then(ownerMock).should().getId();
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

    @Test
    void processFindFormWildcardStringAnnotated() {
        //given
        Owner owner = new Owner(1L, "Foo", "Bar");
        given(ownerServiceMock.findAllByLastNameLike(stringArgumentCaptor.capture())).willReturn(Collections.singletonList(owner));

        //when
        String viewName = controller.processFindForm(owner, resultMock, null);

        //then
        then(ownerServiceMock).should().findAllByLastNameLike(anyString());
        assertThat(stringArgumentCaptor.getValue()).isEqualTo("%Bar%");
    }
}
