package be.ucll.java.ent.view.dialogs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class OKCancelDialog extends Dialog  {

    private Label lblName;

    private HorizontalLayout hlButtons = new HorizontalLayout();
    private Button btnCancel;
    private Button btnOK;

    private boolean okClicked;

    public OKCancelDialog(String text) {

        VerticalLayout vl = new VerticalLayout();
        vl.setSizeFull();
        // vl.getStyle().set("background-color", "#ffe4e1");
        vl.setPadding(false);

        lblName = new Label(text);
        vl.add(lblName);

        btnCancel = new Button("Annuleren");
        btnCancel.addClickListener(buttonClickEvent -> {
            okClicked = false;
            this.close();
        });
        btnOK = new Button("OK");
        btnOK.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnOK.addClickListener(buttonClickEvent -> {
            okClicked = true;
            this.close();
        });
        hlButtons.add(btnCancel, btnOK);
        hlButtons.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        hlButtons.setWidthFull();

        // Wrapper to use up extra space and push footer to the bottom
        FlexLayout wrapper = new FlexLayout();
        wrapper.setWidthFull();
        wrapper.setAlignItems(FlexComponent.Alignment.END);
        wrapper.getElement().getStyle().set("order", "999");
        wrapper.add(hlButtons);
        vl.add(wrapper);
        vl.expand(wrapper);

        this.add(vl);
    }

    public boolean wasOKClicked(){
        return okClicked;
    }

}

