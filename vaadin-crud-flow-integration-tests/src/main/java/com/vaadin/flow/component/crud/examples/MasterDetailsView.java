package com.vaadin.flow.component.crud.examples;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.BodySize;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Route
@Theme(Lumo.class)
@BodySize(height = "100vh", width = "100vw")
public class MasterDetailsView extends VerticalLayout {

    boolean hasBorder = true;

    public MasterDetailsView() {
        final Grid<Company> grid = new Grid<>();
        final Crud<Company> crud = new Crud<>(Company.class, grid, createCompanyEditor());
        crud.setWidth("800px");

        List<Company> data = new ArrayList<>();
        Company company1 = new Company("Vaadin", "Mr", "Rolf Hegbl",
                    "Ruukinkatu 346", "Turku", "20540", "Southern Finland",
                    "Finland", "123-234-345", "987-876-756");
        Company company2 = new Company("Google", "Mr", "Sergey Brin",
                    "Spear str 345", "San Francisco", "94105", "California",
                    "United States", "123-456-789", "987-654-321");
        data.add(company1);
        data.add(company2);
        ListDataProvider<Company> dataProvider = new ListDataProvider<>(data);

        grid.setDataProvider(dataProvider);

        Crud.addEditColumn(grid);

        grid.addColumn(c -> c.getCompanyName()).setHeader("Company Name").setWidth("160px");
        grid.addColumn(c -> c.getContactTitle() + " " + c.getContactName()).setHeader("Contact Person");
        grid.addColumn(c -> c.getPhone()).setHeader("Phone");

        crud.addSaveListener(c -> {
            if (!dataProvider.getItems().contains(c.getItem())) {
                dataProvider.getItems().add(c.getItem());
            }
        });

        crud.addNewListener(e -> dataProvider.getItems().add(e.getItem()));

        setHeight("100%");
        add(crud);
        setHorizontalComponentAlignment(Alignment.CENTER, crud);

        ComboBox<String> countries = new ComboBox<>();
        countries.setItems("Finland", "Belarus");
        Binder<Company> binder = new Binder<>(Company.class);
        binder.bind(countries, Company::getCountry, Company::setCountry);
    }

    private CrudEditor<Company> createCompanyEditor() {
        TextField companyName = new TextField("Company Name");
        companyName.getElement().setAttribute("colspan", "4");
        TextField contactTitle = new TextField("Contact Title");
        TextField contactName = new TextField("Contact Name");
        contactName.getElement().setAttribute("colspan", "3");

        TextField address = new TextField("Address");
        address.getElement().setAttribute("colspan", "2");
        TextField city = new TextField("City");
        TextField zip = new TextField("Postal/Zip Code");

        TextField region = new TextField("Region");
        region.getElement().setAttribute("colspan", "2");
        ComboBox<String> countries = new ComboBox<>();
        countries.setAllowCustomValue(true);
        countries.setLabel("Country");
        countries.getElement().setAttribute("colspan", "2");

        countries.setItems(getCountriesList());
        TextField phone = new TextField("Phone");
        phone.getElement().setAttribute("colspan", "2");
        TextField fax = new TextField("Fax");
        fax.getElement().setAttribute("colspan", "2");

        FormLayout form = new FormLayout(companyName, contactTitle, contactName,
                address, city, zip, region, countries, phone, fax);
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 4));

        Binder<Company> binder = new Binder<>(Company.class);
        binder.bind(companyName, Company::getCompanyName, Company::setCompanyName);
        binder.bind(contactTitle, Company::getContactTitle, Company::setContactTitle);
        binder.bind(contactName, Company::getContactName, Company::setContactName);

        binder.bind(address, Company::getAddress, Company::setAddress);
        binder.bind(city, Company::getCity, Company::setCity);
        binder.bind(zip, Company::getZip, Company::setZip);
        binder.bind(region, Company::getRegion, Company::setRegion);
        binder.bind(countries, Company::getCountry, Company::setCountry);
        binder.bind(phone, Company::getPhone, Company::setPhone);
        binder.bind(fax, Company::getFax, Company::setFax);

        return new BinderCrudEditor<>(binder, form);
    }

    private List<String> getCountriesList() {
        String[] locales = Locale.getISOCountries();
        List<String> countriesList = new ArrayList<>(locales.length);
        for (String iso : locales) {

            Locale obj = new Locale("", iso);
            countriesList.add(obj.getDisplayCountry());
        }
        Collections.sort(countriesList);
        return countriesList;
    }

    public static class Company {
        String companyName;
        String contactTitle;
        String contactName;

        String address;
        String city;
        String zip;

        String region;
        String country;
        String phone;
        String fax;

        public Company() {

        }

        public Company(String companyName, String contactTitle, String contactName,
                String address, String city, String zip, String region,
                String country, String phone, String fax) {
            setCompanyName(companyName);
            setContactTitle(contactTitle);
            setContactName(contactName);
            setAddress(address);
            setCity(city);
            setZip(zip);
            setRegion(region);
            setCountry(country);
            setPhone(phone);
            setFax(fax);
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getContactTitle() {
            return contactTitle;
        }

        public void setContactTitle(String contactTitle) {
            this.contactTitle = contactTitle;
        }

        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getZip() {
            return zip;
        }

        public void setZip(String zip) {
            this.zip = zip;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getFax() {
            return fax;
        }

        public void setFax(String fax) {
            this.fax = fax;
        }

    }
}
