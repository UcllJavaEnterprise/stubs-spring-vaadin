package be.ucll.java.ent.view;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import javax.annotation.PostConstruct;

@Route("")
@PageTitle("StuBS")
@Theme(value = Lumo.class, variant = Lumo.LIGHT)
@CssImport("styles/main-view.css")
class MainView extends AppLayout  {

    // Content views
    private StudentenView sView;
    private LeermodulesView lmView;

    // Left navigation tabs
    private Tab tab1;
    private static final String TABNAME1 = "Studenten";
    private Tab tab2;
    private static final String TABNAME2 = "Leermodules";
    private Tab tab3;
    private Tab tab4;
    private Tabs tabs;

    public MainView() {
        // Header / Menu bar on the top of the page
        H3 header = new H3("Stubs - Het STUdenten Beheer Systeem");
        header.setId("header-layout");

        Image img = new Image("images/ucll.jpg", "UCLL logo");
        img.setHeight("30px");
        img.setId("aligneer-rechts");

        addToNavbar(new DrawerToggle(),
                new Html("<span>&nbsp;&nbsp;</span>"),
                header,
                new Html("<span>&nbsp;&nbsp;</span>"),
                new Icon(VaadinIcon.ACADEMY_CAP), img);

        // Tabs on the left side drawer
        tab1 = new Tab(TABNAME1);
        tab2 = new Tab(TABNAME2);
        tab3 = new Tab("Help");
        tab3.setEnabled(false);
        tab4 = new Tab(new Icon(VaadinIcon.COG));

        tabs = new Tabs(tab1, tab2, tab3, tab4);
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addSelectedChangeListener(event -> {
            handleTabClicked(event);
        });
        addToDrawer(tabs);
    }

    @PostConstruct
    private void setMainViewContent() {
        sView = new StudentenView();
        sView.loadData();

        lmView = new LeermodulesView();
        lmView.loadData();

        // As default load the studentenview
        this.setContent(sView);
    }

    private void handleTabClicked(Tabs.SelectedChangeEvent event) {
        Tab selTab = tabs.getSelectedTab();
        if (selTab.getLabel() != null) {
            if (selTab.getLabel().equals(TABNAME1)) {
                setContent(sView);
            } else if (selTab.getLabel().equals(TABNAME2)) {
                setContent(lmView);
            } else {
                setContent(new Label("Te implementeren scherm voor Admins only"));
            }
        }
    }
}
