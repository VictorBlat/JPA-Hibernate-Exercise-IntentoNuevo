module org.ieselgrao.hibernatepractica {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.sql;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    opens org.ieselgrao.hibernatepractica to javafx.fxml;
    opens org.ieselgrao.hibernatepractica.view to javafx.fxml;
    opens org.ieselgrao.hibernatepractica.controller to javafx.fxml;
    opens org.ieselgrao.hibernatepractica.model to org.hibernate.orm.core, javafx.base;

    exports org.ieselgrao.hibernatepractica;
    exports org.ieselgrao.hibernatepractica.view;
    exports org.ieselgrao.hibernatepractica.controller;
    exports org.ieselgrao.hibernatepractica.model;
}