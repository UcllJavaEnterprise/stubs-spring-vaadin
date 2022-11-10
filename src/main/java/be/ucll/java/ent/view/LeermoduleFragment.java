package be.ucll.java.ent.view;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextField;

import java.util.Arrays;
import java.util.List;

public class LeermoduleFragment extends FormLayout {

    // Public fields for ease of access
    public Label lblID;
    public TextField txtCode;
    public TextField txtBeschrijving;
    public ComboBox<String> cmbSchooljaar;

    public LeermoduleFragment() {
        lblID = new Label("");

        txtCode = new TextField();
        txtCode.setRequired(true);
        txtCode.setMaxLength(8);
        txtCode.setErrorMessage("Verplicht veld");

        txtBeschrijving = new TextField();
        txtBeschrijving.setRequired(true);
        txtBeschrijving.setMaxLength(256);
        txtBeschrijving.setErrorMessage("Verplicht veld");

        // TODO Some fake data here. Should be replaced by data coming from a table.
        List<String> schooljaren = Arrays.asList("2018-19", "2019-20", "2020-21", "2021-22");
        cmbSchooljaar = new ComboBox<>();
        cmbSchooljaar.setItems(schooljaren);

        addFormItem(txtCode, "Code");
        addFormItem(txtBeschrijving, "Beschrijving");
        addFormItem(cmbSchooljaar, "Schooljaar");
    }

    public void resetForm() {
        lblID.setText("");
        txtCode.clear();
        txtCode.setInvalid(false);
        txtBeschrijving.clear();
        txtBeschrijving.setInvalid(false);
        cmbSchooljaar.clear();
        cmbSchooljaar.setInvalid(false);
    }

    public boolean isformValid() {
        if (txtCode.getValue() == null) return false;
        if (txtCode.getValue().trim().length() == 0) return false;
        if (txtBeschrijving.getValue() == null) return false;
        if (txtBeschrijving.getValue().trim().length() == 0) return false;
        if (cmbSchooljaar.getValue() == null) return false;
        if (cmbSchooljaar.getValue().trim().length() == 0) return false;
        return true;
    }
}
