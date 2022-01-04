package com.example.application.views.book;

import com.example.application.data.entity.Book;
import com.example.application.data.service.BookService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import javax.annotation.security.RolesAllowed;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import javax.annotation.security.RolesAllowed;
import org.vaadin.crudui.crud.impl.GridCrud;

@PageTitle("Book")
@Route(value = "book", layout = MainLayout.class)
@RolesAllowed("admin")
public class BookView extends VerticalLayout {

    private TextField title = new TextField("Título");
    private DatePicker published = new DatePicker("published");
    private IntegerField rating = new IntegerField("rating");

    public BookView(BookService service) {

        var crud = new GridCrud<>(Book.class, service);
        crud.getGrid().setColumns("title", "published", "rating");
        crud.getCrudFormFactory().setVisibleProperties("title", "published", "rating");
        crud.setAddOperationVisible(false);
        /*From*/
        var formLayout = new FormLayout();
        formLayout.add(title, published, rating);
        /*From*/

 /*Button*/
        var binder = new Binder(Book.class);
        binder.bindInstanceFields(this);
        var save = new Button("Save", event -> {
            var book = new Book();
            binder.writeBeanIfValid(book);
            service.add(book);
            crud.getGrid().select(null);
            crud.getGrid().getLazyDataView().refreshAll();
            Notification.show("Libro agreagado");
            binder.readBean(new Book());
        });
        var cancel = new Button("Cancelar", event -> {
            binder.setBean(new Book());
            crud.getGrid().select(null);
            crud.getGrid().getLazyDataView().refreshAll();
        });

        var buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        buttonLayout.add(cancel);
        /*Button*/

        add(
                new H1("Books"),
                formLayout,
                buttonLayout,
                crud
        );

    }

}
