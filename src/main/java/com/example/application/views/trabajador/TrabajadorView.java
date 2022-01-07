package com.example.application.views.trabajador;

import com.example.application.data.DataService;
import com.example.application.data.entity.Area;
import com.example.application.data.entity.Trabajador;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import com.example.application.data.service.TrabajadorService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.textfield.EmailField;
import javax.annotation.security.RolesAllowed;

@PageTitle("Trabajador")
@Route(value = "trabajador-view/:trabajadorID?/:action?(edit)", layout = MainLayout.class)
@Uses(Icon.class)
@RolesAllowed("admin")
public class TrabajadorView extends Div implements BeforeEnterObserver {

    private final String TRABAJADOR_ID = "trabajadorID";
    private final String TRABAJADOR_EDIT_ROUTE_TEMPLATE = "trabajador-view/%d/edit";

    private Grid<Trabajador> grid = new Grid<>(Trabajador.class, false);

    private TextField nombre;
    private TextField apellidos;
    private TextField ci;
    private EmailField email;
    private TextField solapin;
    private ComboBox<Area> area;

    private Button save = new Button("Añadir");
    private Button cancel = new Button("Cancelar");
    private Button delete = new Button("Eliminar");

    private BeanValidationBinder<Trabajador> binder;

    private Trabajador trabajador;

    private TrabajadorService trabajadorService;

    public TrabajadorView(
            @Autowired TrabajadorService trabajadorService,
            @Autowired DataService dataService) {

        this.trabajadorService = trabajadorService;
        addClassNames("profesor-view", "flex", "flex-col", "h-full");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.setVerticalScrollingEnabled(true);
        grid.addColumn("nombre").setAutoWidth(true);
        grid.addColumn("apellidos").setAutoWidth(true);
        grid.addColumn("ci").setAutoWidth(true);
        grid.addColumn("email").setAutoWidth(true);
        grid.addColumn("solapin").setAutoWidth(true);
        grid.addColumn(trabajador -> trabajador.getArea().getNombre()).setHeader("Area").setAutoWidth(true);

        grid.setItems(query -> trabajadorService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(TRABAJADOR_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(TrabajadorView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Trabajador.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.bindInstanceFields(this);

        area.setItems(dataService.findAllArea());
        area.setItemLabelGenerator(Area::getNombre);

        //Button
        cancel.addClickShortcut(Key.ESCAPE);
        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickShortcut(Key.ENTER);
        save.addClickListener(e -> {
            try {
                if (this.trabajador == null) {
                    this.trabajador = new Trabajador();
                }
                binder.writeBean(this.trabajador);

                trabajadorService.update(this.trabajador);
                clearForm();
                refreshGrid();
                Notification.show("Trabajador añadido.");
                UI.getCurrent().navigate(TrabajadorView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the profesor details.");
            }
        });

        delete.addClickShortcut(Key.DELETE);
        delete.addClickListener(e -> {
            try {
                if (this.trabajador == null) {
                    this.trabajador = new Trabajador();
                }
                binder.writeBean(this.trabajador);

                trabajadorService.delete(this.trabajador);
                clearForm();
                refreshGrid();
                Notification.show("Trabajador eliminado.");
                UI.getCurrent().navigate(TrabajadorView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the profesor details.");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Integer> profesorId = event.getRouteParameters().getInteger(TRABAJADOR_ID);
        if (profesorId.isPresent()) {
            Optional<Trabajador> trabajadorFromBackend = trabajadorService.get(profesorId.get());
            if (trabajadorFromBackend.isPresent()) {
                populateForm(trabajadorFromBackend.get());
            } else {
                Notification.show(
                        String.format("The requested samplePerson was not found, ID = %d", profesorId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(TrabajadorView.class);
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
        solapin = new TextField("Solapin");
        email = new EmailField("Correo");
        area = new ComboBox<>("Area");
        Component[] fields = new Component[]{nombre, apellidos, ci,email, solapin, area};

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

    private void populateForm(Trabajador value) {
        this.trabajador = value;
        binder.readBean(this.trabajador);

    }
}
