package synfogrp.synfo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class mainApp extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/FXMLScene.fxml"));
        Controller controller = new Controller();
        loader.setController(controller);
        AnchorPane root = loader.load();

        primaryStage.setTitle("SysInfo");
        Scene mainScene = new Scene(root, 600, 430);
        mainScene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        HBox titleBar = controller.getTitleBar();
        
        titleBar.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        titleBar.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        Button closeButton = controller.getExitButton();
        closeButton.setOnAction(_ -> primaryStage.close());

        Button minButton = controller.getMinButton();
        minButton.setOnAction(_ -> primaryStage.setIconified(true));;

        primaryStage.show();

        // First Section
        getSystemInfo info = new getSystemInfo();
        controller.setOS(info.getOs().toString().trim());
        controller.setMB(info.getMb().toString().trim());
        controller.setCPU(info.getCPU().toString().trim());
        controller.setGPU(info.getGPU().toString().trim());
        controller.setMem(info.getMem().toString().trim());
        controller.setSto(info.getSto().toString().trim());
        controller.setArc(info.getArc().toString().trim());
        controller.setUsr(info.getUsr().toString().trim());
        controller.setMan(info.getMan().toString().trim());
        // Second Section
        controller.setCPUModel(info.getCPUName().toString().trim());
        controller.setCPUCores(Integer.toString(info.getCPUCoresAndThread()[0]));
        controller.setCPUThreads(Integer.toString(info.getCPUCoresAndThread()[1]));
        controller.setCPUSocket(info.getCPUSocket().toString().trim());
        controller.setCPUArc(info.getCPUArc().toString().trim());
        controller.setRAMType(info.getRAMType());
        controller.setRAMSpeed(info.getRAMFreq());

        Timeline coreTimeline = new Timeline(new KeyFrame(Duration.seconds(0.5), _ -> {
            controller.setCoreSpeed(info.getCurrentFreq(controller.getCore()));
        }));
        coreTimeline.setCycleCount(Timeline.INDEFINITE);

        ToggleButton sideToggle2 = controller.getSide2();

        controller.getToggleGroup().selectedToggleProperty().addListener((_, _, newToggle) ->{
            if (newToggle == sideToggle2){
                coreTimeline.play();
            }
            else{
                coreTimeline.stop();
            }
        });
        controller.setGPUInfo(info.getGPUInfo());
        controller.setNetInfo(info.getNetInfo());

        // Third Section
        // Storage
        Rectangle usedStorageRect = controller.getFilledRect();
        Label usedStorageLabel = controller.getUsedStorageLabel();
        usedStorageRect.heightProperty().addListener((_, _, newHeight) -> {
            usedStorageRect.setY(300 - newHeight.doubleValue());
            usedStorageLabel.setLayoutX(45);
            usedStorageLabel.layoutYProperty().bind(usedStorageRect.yProperty().add(usedStorageRect.heightProperty().subtract(usedStorageLabel.heightProperty()).divide(2)));
        });
        controller.setStorageFilledHeight(100 * info.getStorageInfo()[1]/info.getStorageInfo()[0]);
        controller.setStorageFilledValue(info.getStorageInfo()[1]);

        Rectangle unusedStorageRect = controller.getEmptyRect();
        Label unusedStorageLabel = controller.getUnusedStorageLabel();
        unusedStorageLabel.setLayoutX(45);
        unusedStorageLabel.layoutYProperty().bind(unusedStorageRect.yProperty().add(unusedStorageRect.heightProperty().subtract(unusedStorageLabel.heightProperty()).divide(2)));
        controller.setStorageFilledHeight(100 * info.getStorageInfo()[1]/info.getStorageInfo()[0]);
        controller.setStorageEmptyValue(info.getStorageInfo()[0] - info.getStorageInfo()[1]);
        // Memory
        Rectangle usedMemoryRect = controller.getMemoryFilledRect();
        Label usedMemoryLabel = controller.getUsedMemoryLabel();
        usedMemoryRect.heightProperty().addListener((_, _, newHeight) -> {
            usedMemoryRect.setY(300 - newHeight.doubleValue());
            usedMemoryLabel.setLayoutX(45);
            usedMemoryLabel.layoutYProperty().bind(usedMemoryRect.yProperty().add(usedMemoryRect.heightProperty().subtract(usedMemoryLabel.heightProperty()).divide(2)));
        });
        controller.setMemoryFilledHeight(100 * info.getMemoryInfo()[1]/info.getMemoryInfo()[0]);
        controller.setMemoryFilledValue(info.getMemoryInfo()[1]);

        Rectangle unusedMemoryRect = controller.getMemoryEmptyRect();
        Label unusedMemoryLabel = controller.getUnusedMemoryLabel();
        unusedMemoryLabel.setLayoutX(45);
        unusedMemoryLabel.layoutYProperty().bind(unusedMemoryRect.yProperty().add(unusedMemoryRect.heightProperty().subtract(unusedMemoryLabel.heightProperty()).divide(2)));
        controller.setMemoryFilledHeight(100 * info.getMemoryInfo()[1]/info.getMemoryInfo()[0]);
        controller.setMemoryEmptyValue(info.getMemoryInfo()[0] - info.getMemoryInfo()[1]);

        // Timeline to update memory and storage

        Timeline storageMemoryTimeline = new Timeline(new KeyFrame(Duration.seconds(0.5), _ -> {
            // Set stuff up
            controller.setMemoryFilledHeight(100 - 100 * info.getMemoryInfo()[1]/info.getMemoryInfo()[0]);
            controller.setMemoryFilledValue(info.getMemoryInfo()[0] - info.getMemoryInfo()[1]);
            controller.setMemoryEmptyValue(info.getMemoryInfo()[1]);

            controller.setStorageFilledHeight(100 - 100 * info.getStorageInfo()[1]/info.getStorageInfo()[0]);
            controller.setStorageFilledValue(info.getStorageInfo()[0] - info.getStorageInfo()[1]);
            controller.setStorageEmptyValue(info.getStorageInfo()[1]);

        }));
        storageMemoryTimeline.setCycleCount(Timeline.INDEFINITE);

        ToggleButton sideToggle3 = controller.getSide3();

        controller.getToggleGroup().selectedToggleProperty().addListener((_, _, newToggle) ->{
            if (newToggle == sideToggle3){
                storageMemoryTimeline.play();
            }
            else{
                storageMemoryTimeline.stop();
            }
        });




        // Debug
        // SystemInfoProvider inf = new SystemInfoProvider();
        // for (int i = 0; i<5;i++){
        // System.out.println(info.getGPUInfo()[i]);
        // }
        // inf.printAllSystemInfo();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
