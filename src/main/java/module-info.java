module synfogrp.synfo {
    requires javafx.controls;
    requires javafx.fxml;


    opens synfogrp.synfo to javafx.fxml;
    exports synfogrp.synfo;
}