package be.ucll.java.ent.view;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDate;
import java.util.Locale;

public class StudentFragment extends FormLayout {

    // Public fields for ease of access
    public Label lblID;
    public TextField txtVoornaam;
    public TextField txtNaam;
    public DatePicker datGeboorte;

    public StudentFragment() {
        super();

        lblID = new Label("");

        txtVoornaam = new TextField();
        txtVoornaam.setRequired(true);
        txtVoornaam.setMaxLength(128);
        txtVoornaam.setErrorMessage("Verplicht veld");

        txtNaam = new TextField();
        txtNaam.setRequired(true);
        txtNaam.setMaxLength(128);
        txtNaam.setErrorMessage("Verplicht veld");

        datGeboorte = new DatePicker();
        LocalDate now = LocalDate.now();
        datGeboorte.setPlaceholder("dd/mm/jjjj");
        //datGeboorte.setValue(now);
        datGeboorte.setMin(now.minusYears(100));
        datGeboorte.setMax(now);
        datGeboorte.setRequired(true);
        datGeboorte.addInvalidChangeListener(e -> datGeboorte.setErrorMessage("Verplicht veld. Ongeldig datumformaat of datum in de toekomst"));
        datGeboorte.setLocale(new Locale("nl", "BE"));
        datGeboorte.setClearButtonVisible(true);

        addFormItem(txtVoornaam, "Voornaam");
        addFormItem(txtNaam, "Naam");
        addFormItem(datGeboorte, "Geboortedatum");
    }

    public void resetForm() {
        lblID.setText("");
        txtVoornaam.clear();
        txtVoornaam.setInvalid(false);
        txtNaam.clear();
        txtNaam.setInvalid(false);
        datGeboorte.clear();
        datGeboorte.setInvalid(false);
    }

    public boolean isformValid() {
        boolean result = true;
        if (txtNaam.getValue() == null) {
            txtNaam.setInvalid(true);
            result = false;
        }
        if (txtNaam.getValue().trim().length() == 0) {
            txtNaam.setInvalid(true);
            result = false;
        }
        if (txtVoornaam.getValue() == null) {
            txtVoornaam.setInvalid(true);
            result = false;
        }
        if (txtVoornaam.getValue().trim().length() == 0) {
            txtVoornaam.setInvalid(true);
            result = false;
        }
        if (datGeboorte.getValue() == null) {
            datGeboorte.setInvalid(true);
            result = false;
        }
        return result;
    }
}
