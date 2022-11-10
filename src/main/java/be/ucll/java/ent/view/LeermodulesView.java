package be.ucll.java.ent.view;

import be.ucll.java.ent.controller.LeermoduleController;
import be.ucll.java.ent.domain.LeermoduleDTO;
import be.ucll.java.ent.utils.BeanUtil;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route("leermodules")
public class LeermodulesView extends VerticalLayout {

    private final LeermoduleController leermoduleMgr;

    private SplitLayout splitLayout;
    private VerticalLayout lpvLayout; // Left Panel Vertical Layout
    private HorizontalLayout lphLayout;
    private VerticalLayout rpvLayout;
    private HorizontalLayout rphLayout;

    private Label lblCode;
    private TextField txtCode;

    private Button btnCancel;
    private Button btnCreate;
    private Button btnUpdate;
    private Button btnDelete;

    private Grid<LeermoduleDTO> grid;
    private LeermoduleFragment frm;

    public LeermodulesView() {
        this.setSizeFull();
        this.setPadding(false);

        splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        splitLayout.addToPrimary(createGridLayout());
        splitLayout.addToSecondary(createEditorLayout());
        add(splitLayout);

        // Load Spring Beans via a utility class
        // We can't use @Autowired because Vaadin Views are preferably NOT declared as SpringComponent
        leermoduleMgr = BeanUtil.getBean(LeermoduleController.class);
    }

    private Component createGridLayout() {
        lpvLayout = new VerticalLayout();
        lpvLayout.setWidthFull();

        frm = new LeermoduleFragment();

        lphLayout = new HorizontalLayout();
        lblCode = new Label("Code (bevat)");
        txtCode = new TextField();
        txtCode.setValueChangeMode(ValueChangeMode.EAGER);
        txtCode.addValueChangeListener(e -> {
            handleValueChanged(null);
        });

        lphLayout.add(lblCode);
        lphLayout.add(txtCode);

        grid = new Grid<>();
        grid.setItems(new ArrayList<LeermoduleDTO>(0));
        // grid.addColumn(LeermoduleDTO::getCode).setHeader("Code").setSortable(true);
        // Alternatief thanks to Jaimie
        grid.addColumn(LeermoduleDTO -> LeermoduleDTO.getCode()).setHeader("Code").setSortable(true);
        grid.addColumn(LeermoduleDTO::getBeschrijving).setHeader("Beschrijving").setSortable(true);
        grid.addColumn(LeermoduleDTO::getSchooljaar).setHeader("Schooljaar").setSortable(true);
        grid.setHeightFull();

        //when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> populateForm(event.getValue()));

        lpvLayout.add(lphLayout);
        lpvLayout.add(grid);
        lpvLayout.setWidth("70%");
        return lpvLayout;
    }

    private Component createEditorLayout() {
        rpvLayout = new VerticalLayout();

        frm = new LeermoduleFragment();

        rphLayout = new HorizontalLayout();
        rphLayout.setWidthFull();
        rphLayout.setSpacing(true);

        btnCancel = new Button("Annuleren");
        btnCancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        btnCancel.addClickListener(e -> handleClickCancel(e));

        btnCreate = new Button("Toevoegen");
        btnCreate.addClickListener(e -> handleClickCreate(e));

        btnUpdate = new Button("Opslaan");
        btnUpdate.addClickListener(e -> handleClickUpdate(e));
        btnUpdate.setVisible(false);

        btnDelete = new Button("Verwijderen");
        btnDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        btnDelete.addClickListener(e -> handleClickDelete(e));
        btnDelete.setVisible(false);

        rphLayout.add(btnCancel, btnCreate, btnUpdate, btnDelete);

        rpvLayout.add(frm);
        rpvLayout.add(rphLayout);
        rpvLayout.setWidth("30%");

        return rpvLayout;
    }

    public void loadData() {
        if (leermoduleMgr != null) {
            List<LeermoduleDTO> lst = leermoduleMgr.getAllLeermodules();
            grid.setItems(lst);
        }
    }

    private void handleValueChanged(ClickEvent event) {
        if (txtCode.getValue().trim().length() == 0) {
            List<LeermoduleDTO> lst = leermoduleMgr.getAllLeermodules();
            grid.setItems(lst);
        } else {
            String searchTerm = txtCode.getValue().trim();
            List<LeermoduleDTO> lst = leermoduleMgr.getLeermodules(searchTerm);
            grid.setItems(lst);
        }
    }

    private void handleClickCancel(ClickEvent event) {
        grid.asSingleSelect().clear();
        frm.resetForm();
        btnCreate.setVisible(true);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);
    }

    private void handleClickCreate(ClickEvent event) {
        if (!frm.isformValid()) {
            Notification.show("Er zijn validatiefouten", 3000, Notification.Position.MIDDLE);
            return;
        }

        try {
            LeermoduleDTO l = new LeermoduleDTO(0, frm.txtCode.getValue(), frm.txtBeschrijving.getValue(), frm.cmbSchooljaar.getValue());
            long i = leermoduleMgr.createLeermodule(l);

            Notification.show("Leermodule created (id: " + i + ")", 3000, Notification.Position.TOP_CENTER);
            frm.resetForm();
            handleValueChanged(null);
        } catch (IllegalArgumentException e) {
            Notification.show(e.getMessage(), 5000, Notification.Position.MIDDLE);
        }
    }

    private void handleClickUpdate(ClickEvent event) {
        if (!frm.isformValid()) {
            Notification.show("Er zijn validatiefouten", 3000, Notification.Position.MIDDLE);
            return;
        }

        LeermoduleDTO l = new LeermoduleDTO(Integer.parseInt(frm.lblID.getText()), frm.txtCode.getValue(), frm.txtBeschrijving.getValue(), frm.cmbSchooljaar.getValue().toString());
        leermoduleMgr.updateLeermodule(l);

        Notification.show("Leermodule aangepast", 3000, Notification.Position.TOP_CENTER);
        frm.resetForm();
        handleValueChanged(null);
    }

    private void handleClickDelete(ClickEvent event) {
        try {
            leermoduleMgr.deleteLeermodule(Integer.parseInt(frm.lblID.getText()));
            Notification.show("Leermodule verwijderd", 3000, Notification.Position.TOP_CENTER);
        } catch (RuntimeException e) {
            Notification.show("Het is NIET mogelijk de Leermodule te verwijderen wegens geregistreerde inschrijvingen.", 5000, Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
        handleValueChanged(null);
        frm.resetForm();
    }

    private void populateForm(LeermoduleDTO l) {
        btnCreate.setVisible(false);
        btnUpdate.setVisible(true);
        btnDelete.setVisible(true);

        if (l != null) {
            // Copy the ID in a hidden field
            frm.lblID.setText("" + l.getId());
            if (l.getCode() != null) {
                frm.txtCode.setValue(l.getCode());
            } else {
                frm.txtCode.setValue("");
            }
            if (l.getBeschrijving() != null) {
                frm.txtBeschrijving.setValue(l.getBeschrijving());
            } else {
                frm.txtBeschrijving.setValue("");
            }

            if (l.getSchooljaar() != null) {
                try {
                    frm.cmbSchooljaar.setValue(l.getSchooljaar());
                } catch (NullPointerException e) {
                    frm.cmbSchooljaar.setValue(null);
                }
            } else {
                frm.cmbSchooljaar.setValue(null);
            }
        }
    }
}
