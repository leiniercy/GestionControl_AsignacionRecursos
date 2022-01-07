package com.example.application.views.estudiantes;

import com.example.application.data.DataService;
import com.example.application.data.entity.Area;
import com.example.application.data.entity.Estudiante;
import com.example.application.data.entity.Grupo;
import com.example.application.data.service.EstudianteService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.shared.Registration;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.annotation.security.RolesAllowed;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@PageTitle("Estudiantes")
@Route(value = "estudiantes/:estudianteID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed("admin")
public class EstudiantesView extends Div implements BeforeEnterObserver {

    private final String ESTUDIANTE_ID = "estudianteID";
    private final String ESTUDIANTE_EDIT_ROUTE_TEMPLATE = "estudiantes/%d/edit";

    private Grid<Estudiante> grid = new Grid<>(Estudiante.class, false);

    private TextField nombre;
    private TextField apellidos;
    private TextField ci;
    private EmailField email;
    private TextField solapin;
    private TextField anno_academico;
    private ComboBox<Grupo> grupo;

    private Button save = new Button("Añadir");
    private Button cancel = new Button("Cancelar");
    private Button delete = new Button("Eliminar");

    private BeanValidationBinder<Estudiante> binder;

    private Estudiante estudiante;

    private EstudianteService estudianteService;

    public EstudiantesView(
            @Autowired EstudianteService estudianteService,
            @Autowired DataService dataService
    ) {

        this.estudianteService = estudianteService;
        addClassNames("estudiantes-view", "flex", "flex-col", "h-full");

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
        grid.addColumn("solapin").setAutoWidth(true);
        grid.addColumn("anno_academico").setAutoWidth(true);
        grid.addColumn(estudiante -> estudiante.getGrupo().getNumero()).setHeader("Grupo").setAutoWidth(true);
        grid.setItems(query -> estudianteService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(ESTUDIANTE_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(EstudiantesView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Estudiante.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.forField(anno_academico).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("anno_academico");

        binder.bindInstanceFields(this);

        grupo.setItems(dataService.findAllGrupo());
        grupo.setItemLabelGenerator(Grupo::getNumero);

        //Button
        save.addClickShortcut(Key.ENTER);
        save.addClickListener(e -> {
            try {
                if (this.estudiante == null) {
                    this.estudiante = new Estudiante();
                }
                binder.writeBean(this.estudiante);
                estudianteService.update(this.estudiante);
                clearForm();
                refreshGrid();
                Notification.show("Estudiante añadido correctamente.");
                UI.getCurrent().navigate(EstudiantesView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the estudiante details.");
            }
        });

        cancel.addClickShortcut(Key.ESCAPE);
        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        delete.addClickShortcut(Key.DELETE);
        delete.addClickListener(e -> {
            try {
                if (this.estudiante == null) {
                    this.estudiante = new Estudiante();
                }
                binder.writeBean(this.estudiante);
                estudianteService.delete(this.estudiante);
                clearForm();
                refreshGrid();
                Notification.show("Estudiante eliminado correctamente.");
                UI.getCurrent().navigate(EstudiantesView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the estudiante details.");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Integer> estudianteId = event.getRouteParameters().getInteger(ESTUDIANTE_ID);
        if (estudianteId.isPresent()) {
            Optional<Estudiante> estudianteFromBackend = estudianteService.get(estudianteId.get());
            if (estudianteFromBackend.isPresent()) {
                populateForm(estudianteFromBackend.get());
            } else {
                Notification.show(String.format("The requested estudiante was not found, ID = %d", estudianteId.get()),
                        3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(EstudiantesView.class);
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
        ci = new TextField("CI");
        email = new EmailField("Email");
        solapin = new TextField("Solapin");
        anno_academico = new TextField("Año académico");
        grupo = new ComboBox<>("Grupo");
        Component[] fields = new Component[]{nombre, apellidos, ci,email, solapin, anno_academico, grupo};

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
        buttonLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.BASELINE);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, delete, cancel);
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

    private void populateForm(Estudiante value) {
        this.estudiante = value;
        binder.readBean(this.estudiante);

    }

}
