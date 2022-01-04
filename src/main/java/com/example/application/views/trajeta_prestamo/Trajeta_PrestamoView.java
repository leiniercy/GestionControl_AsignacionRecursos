package com.example.application.views.trajeta_prestamo;

import com.example.application.data.entity.TarjetaPrestamo;
import com.example.application.data.service.TarjetaPrestamoService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import java.util.Optional;
import javax.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@PageTitle("Trajeta_Prestamo")
@Route(value = "trajeta_Prestamo/:tarjeta_PrestamoID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed("admin")
public class Trajeta_PrestamoView extends Div implements BeforeEnterObserver {

    private final String TARJETA_PRESTAMO_ID = "tarjeta_PrestamoID";
    private final String TARJETA_PRESTAMO_EDIT_ROUTE_TEMPLATE = "trajeta_Prestamo/%d/edit";

    private Grid<TarjetaPrestamo> grid = new Grid<>(TarjetaPrestamo.class, false);

    private DatePicker fecha_prestamo;
    private DatePicker fecha_recojida;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<TarjetaPrestamo> binder;

    private TarjetaPrestamo tarjeta_Prestamo;

    private TarjetaPrestamoService tarjeta_PrestamoService;

    public Trajeta_PrestamoView(@Autowired TarjetaPrestamoService tarjeta_PrestamoService) {
        this.tarjeta_PrestamoService = tarjeta_PrestamoService;
        addClassNames("trajeta-prestamo-view", "flex", "flex-col", "h-full");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("fecha_prestamo").setAutoWidth(true);
        grid.addColumn("fecha_recojida").setAutoWidth(true);
        grid.setItems(query -> tarjeta_PrestamoService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(TARJETA_PRESTAMO_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(Trajeta_PrestamoView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(TarjetaPrestamo.class);

        // Bind fields. This where you'd define e.g. validation rules

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.tarjeta_Prestamo == null) {
                    this.tarjeta_Prestamo = new TarjetaPrestamo();
                }
                binder.writeBean(this.tarjeta_Prestamo);

                tarjeta_PrestamoService.update(this.tarjeta_Prestamo);
                clearForm();
                refreshGrid();
                Notification.show("Tarjeta_Prestamo details stored.");
                UI.getCurrent().navigate(Trajeta_PrestamoView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the tarjeta_Prestamo details.");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Integer> tarjeta_PrestamoId = event.getRouteParameters().getInteger(TARJETA_PRESTAMO_ID);
        if (tarjeta_PrestamoId.isPresent()) {
            Optional<TarjetaPrestamo> tarjeta_PrestamoFromBackend = tarjeta_PrestamoService
                    .get(tarjeta_PrestamoId.get());
            if (tarjeta_PrestamoFromBackend.isPresent()) {
                populateForm(tarjeta_PrestamoFromBackend.get());
            } else {
                Notification.show(String.format("The requested tarjeta_Prestamo was not found, ID = %d",
                        tarjeta_PrestamoId.get()), 3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(Trajeta_PrestamoView.class);
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
        fecha_prestamo = new DatePicker("Fecha_prestamo");
        fecha_recojida = new DatePicker("Fecha_recojida");
        Component[] fields = new Component[]{fecha_prestamo, fecha_recojida};

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

    private void populateForm(TarjetaPrestamo value) {
        this.tarjeta_Prestamo = value;
        binder.readBean(this.tarjeta_Prestamo);

    }
}
