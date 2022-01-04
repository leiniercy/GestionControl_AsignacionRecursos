package com.example.application.views.person;

import com.example.application.data.entity.Persona;
import com.example.application.data.service.PersonaService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import java.util.Optional;
import javax.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@PageTitle("Persona")
@Route(value = "persona/:personaID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed("admin")
public class PersonView extends Div implements BeforeEnterObserver {

    private final String PERSON_ID = "personaID";
    private final String PERSON_EDIT_ROUTE_TEMPLATE = "persona/%d/edit";

    private Grid<Persona> grid = new Grid<>(Persona.class, false);

    private TextField nombre;
    private TextField apellidos;
    private IntegerField ci;
    private TextField email;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<Persona> binder;

    private Persona persona;

    private PersonaService personaService;

    public PersonView(@Autowired PersonaService personService) {
        this.personaService = personService;
        addClassNames("person-view", "flex", "flex-col", "h-full");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("nombre").setAutoWidth(true);
        grid.addColumn("apellidos").setAutoWidth(true);
        grid.addColumn("ci").setAutoWidth(true);
        grid.addColumn("email").setAutoWidth(true);
        grid.setItems(query -> personService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(PERSON_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(PersonView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Persona.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.persona == null) {
                    this.persona = new Persona();
                }
                binder.writeBean(this.persona);

                personService.update(this.persona);
                clearForm();
                refreshGrid();
                Notification.show("Person details stored.");
                UI.getCurrent().navigate(PersonView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the person details.");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Integer> personId = event.getRouteParameters().getInteger(PERSON_ID);
        if (personId.isPresent()) {
            Optional<Persona> personFromBackend = personaService.get(personId.get());
            if (personFromBackend.isPresent()) {
                populateForm(personFromBackend.get());
            } else {
                Notification.show(String.format("The requested person was not found, ID = %d", personId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(PersonView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("flex flex-col");
        editorLayoutDiv.setWidth("400px");

        Div editorDiv = new Div();
        editorDiv.setClassName("p-l flex-grow");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        nombre = new TextField("Nombre");
        apellidos = new TextField("Apellidos");
        ci = new IntegerField("CI");
        email = new TextField("Email");
        Component[] fields = new Component[]{nombre, apellidos, ci, email};

        for (Component field : fields) {
            ((HasStyle) field).addClassName("full-width");
        }
        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("w-full flex-wrap bg-contrast-5 py-s px-l");
        buttonLayout.setSpacing(true);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getLazyDataView().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Persona value) {
        this.persona = value;
        binder.readBean(this.persona);

    }
}
