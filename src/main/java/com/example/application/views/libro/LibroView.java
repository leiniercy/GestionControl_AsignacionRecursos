package com.example.application.views.libro;

import com.example.application.data.entity.Libro;
import com.example.application.data.service.LibroService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import elemental.json.Json;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import javax.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.util.UriUtils;

@PageTitle("Libro")
@Route(value = "libro/:libroID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed("admin")
public class LibroView extends Div implements BeforeEnterObserver {

    private final String LIBRO_ID = "libroID";
    private final String LIBRO_EDIT_ROUTE_TEMPLATE = "libro/%d/edit";

    private Grid<Libro> grid = new Grid<>(Libro.class, false);

    private Upload image;
    private Image imagePreview;
    private TextField codigo;
    private TextField titulo;
    private TextField autor;
    private TextField volumen;
    private TextField tomo;
    private TextField precio;
    private TextField cant_libros;

    private Button save = new Button("Añadir");
    private Button cancel = new Button("Cancelar");
    private Button delete = new Button("Eliminar");

    private BeanValidationBinder<Libro> binder;

    private Libro libro;

    private LibroService libroService;

    public LibroView(@Autowired LibroService libroService) {
        this.libroService = libroService;
        addClassNames("libro-view", "flex", "flex-col", "h-full");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        TemplateRenderer<Libro> imageRenderer = TemplateRenderer
                .<Libro>of("<img style='height: 64px' src='[[item.image]]' />").withProperty("image", Libro::getImage);
        grid.addColumn(imageRenderer).setHeader("Image").setWidth("68px").setFlexGrow(0);

        grid.addColumn("codigo").setAutoWidth(true);
        grid.addColumn("titulo").setAutoWidth(true);
        grid.addColumn("autor").setAutoWidth(true);
        grid.addColumn("volumen").setAutoWidth(true);
        grid.addColumn("tomo").setAutoWidth(true);
        grid.addColumn("precio").setAutoWidth(true);
        grid.addColumn("cant_libros").setAutoWidth(true);
        grid.setItems(query -> libroService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(LIBRO_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(LibroView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Libro.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.forField(codigo).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("codigo");
        binder.forField(volumen).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("volumen");
        binder.forField(tomo).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("tomo");
        binder.forField(precio).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("precio");
        binder.forField(cant_libros).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("cant_libros");
                                        
        binder.bindInstanceFields(this);

        attachImageUpload(image, imagePreview);

        //Button
        cancel.addClickShortcut(Key.ESCAPE);
        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });
        
        save.addClickShortcut(Key.ENTER);
        save.addClickListener(e -> {
            try {
                if (this.libro == null) {
                    this.libro = new Libro();
                }
                binder.writeBean(this.libro);
                this.libro.setImage(imagePreview.getSrc());

                libroService.update(this.libro);
                clearForm();
                refreshGrid();
                Notification.show("Libro añadido.");
                UI.getCurrent().navigate(LibroView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the libro details.");
            }
        });
        
        delete.addClickShortcut(Key.DELETE);
        delete.addClickListener(e -> {
            try {
                if (this.libro == null) {
                    this.libro = new Libro();
                }
                binder.writeBean(this.libro);
                this.libro.setImage(imagePreview.getSrc());

                libroService.delete(this.libro);
                clearForm();
                refreshGrid();
                Notification.show("Libro eliminado.");
                UI.getCurrent().navigate(LibroView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the libro details.");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Integer> libroId = event.getRouteParameters().getInteger(LIBRO_ID);
        if (libroId.isPresent()) {
            Optional<Libro> libroFromBackend = libroService.get(libroId.get());
            if (libroFromBackend.isPresent()) {
                populateForm(libroFromBackend.get());
            } else {
                Notification.show(String.format("The requested libro was not found, ID = %d", libroId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(LibroView.class);
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
        Label imageLabel = new Label("Image");
        imagePreview = new Image();
        imagePreview.setWidth("100%");
        image = new Upload();
        image.getStyle().set("box-sizing", "border-box");
        image.getElement().appendChild(imagePreview.getElement());
        codigo = new TextField("Codigo");
        titulo = new TextField("Titulo");
        autor = new TextField("Autor");
        volumen = new TextField("Volumen");
        tomo = new TextField("Tomo");
        precio = new TextField("Precio");
        cant_libros = new TextField("Cant_libros");
        Component[] fields = new Component[]{imageLabel, image, codigo, titulo, autor, volumen, tomo, precio,
                cant_libros};

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

    private void attachImageUpload(Upload upload, Image preview) {
        ByteArrayOutputStream uploadBuffer = new ByteArrayOutputStream();
        upload.setAcceptedFileTypes("image/*");
        upload.setReceiver((fileName, mimeType) -> {
            return uploadBuffer;
        });
        upload.addSucceededListener(e -> {
            String mimeType = e.getMIMEType();
            String base64ImageData = Base64.getEncoder().encodeToString(uploadBuffer.toByteArray());
            String dataUrl = "data:" + mimeType + ";base64,"
                    + UriUtils.encodeQuery(base64ImageData, StandardCharsets.UTF_8);
            upload.getElement().setPropertyJson("files", Json.createArray());
            preview.setSrc(dataUrl);
            uploadBuffer.reset();
        });
        preview.setVisible(false);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getLazyDataView().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Libro value) {
        this.libro = value;
        binder.readBean(this.libro);
        this.imagePreview.setVisible(value != null);
        if (value == null) {
            this.imagePreview.setSrc("");
        } else {
            this.imagePreview.setSrc(value.getImage());
        }

    }
}
