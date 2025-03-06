module synfogrp.synfo {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.github.oshi;


    opens synfogrp.synfo to javafx.fxml;
    exports synfogrp.synfo;
}