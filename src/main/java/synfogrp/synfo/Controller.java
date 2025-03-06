package synfogrp.synfo;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public class Controller {

    // Main Components
    @FXML
    private Label titleLbl;

    @FXML
    private ToggleGroup sideGroup;

    @FXML
    private ToggleButton sideBtn1;

    @FXML
    private ToggleButton sideBtn2;

    @FXML
    private ToggleButton sideBtn3;

    @FXML
    private VBox firstPane;

    @FXML
    private VBox secondPane;

    @FXML
    private HBox thirdPane;
    
    @FXML
    private Button btnExit;

    @FXML
    private Button btnMin;

    @FXML
    private HBox hboxTitle;

    private Pane[] panes;

    // First Toggle
    @FXML
    private TextField txtArc;

    @FXML
    private TextField txtCPU;

    @FXML
    private TextField txtGPU;

    @FXML
    private TextField txtMB;

    @FXML
    private TextField txtMem;

    @FXML
    private TextField txtOS;

    @FXML
    private TextField txtSto;

    @FXML
    private TextField txtUser;

    @FXML
    private TextField txtMan;

    @FXML
    private TextField txtVer;
    
    // Second toggle
    @FXML
    private TextField cpumodel;
    
    @FXML
    private TextField cpucores;

    @FXML
    private TextField cputhreads;
    
    @FXML
    private TextField cpusocket;

    @FXML
    private TextField cpuarc;

    @FXML
    private TextField ramtype;
    
    @FXML
    private TextField ramspeed;

    @FXML
    private ChoiceBox<String> coreselect;

    @FXML
    private TextField corespeed;

    @FXML
    private TextField gpudriver2;

    @FXML
    private TextField gpumodel2;

    @FXML
    private TextField maxNetSpeed;

    @FXML
    private TextField macaddress2;

    @FXML
    private TextField netadapter2;

    @FXML
    private TextField netinterface2;

    @FXML
    private TextField vram2;

    @FXML
    private StackPane leftStorageStack;


    // Third Toggle
    @FXML
    private Pane storagePane;

    @FXML
    private Rectangle storageEmptyRect;

    @FXML
    private Rectangle storageFilledRect;

    @FXML
    private Rectangle storageOuterRect;

    @FXML
    private Label usedStorageLabel;

    @FXML
    private Label unusedStorageLabel;

    List<String> coreNames = new ArrayList<>();

    getSystemInfo si = new getSystemInfo();
    // 
    // // // Functions for main elements
    // 
    public HBox getTitleBar(){
        return hboxTitle;
    }

    public Button getMinButton(){
        return btnMin;
    }

    public Button getExitButton(){
        return btnExit;
    }

    @FXML
    private Rectangle memoryEmptyRect;

    @FXML
    private Rectangle memoryFilledRect;

    @FXML
    private Rectangle memoryOuterRect;

    @FXML
    private Label usedMemoryLabel;

    @FXML
    private Label unusedMemoryLabel;

    @FXML
    private Pane memoryPane;

    // 
    // // // Functions for first part
    // 
    public void setOS(String OS){
        txtOS.setText(OS);
    }
    public void setMB(String MB){
        txtMB.setText(MB);
    }
    public void setCPU(String CPU){
        txtCPU.setText(CPU);
    }
    public void setGPU(String GPU){
        txtGPU.setText(GPU);
    }
    public void setMem(String Mem){
        txtMem.setText(Mem);
    }
    public void setSto(String Sto){
        txtSto.setText(Sto);
    }
    public void setArc(String Arc){
        txtArc.setText(Arc);
    }
    public void setUsr(String Usr){
        txtUser.setText(Usr);
    }
    public void setMan(String Man){
        txtMan.setText(Man);
    }


    // 
    // // // Functions for second part
    // 

    public ToggleButton getSide2(){
        return sideBtn2;
    }
    public void setCPUModel(String model){
        cpumodel.setText(model);
    }
    public void setCPUCores(String corecount){
        cpucores.setText(corecount);
    }
    public void setCPUThreads(String threadcount){
        cputhreads.setText(threadcount);
    }
    public void setCPUSocket(String socket){
        cpusocket.setText(socket);
    }
    public void setCPUArc(String arc){
        cpuarc.setText(arc);
    }
    public void setRAMType(String type){
        ramtype.setText(type);
    }
    public void setRAMSpeed(long speed){
        ramspeed.setText(Long.toString(speed / 1000000) + "MHz");
    }
    public int getCore(){
        return Integer.parseInt(coreselect.getValue());
    }
    public boolean getIsSide2(){
        return !secondPane.isDisable();
    }
    public ToggleGroup getToggleGroup(){
        return sideGroup;
    }
    public void setCoreSpeed(double cspeed){
        corespeed.setText(Double.toString(cspeed / 1_000_000) + "MHz");
    }
    public void setGPUInfo(Object[] gpuInfo){
        gpumodel2.setText(gpuInfo[0].toString());
        vram2.setText(gpuInfo[1].toString() + "GB");
        gpudriver2.setText(gpuInfo[2].toString());
    }

    public void setNetInfo(Object[] netInfo){
        netadapter2.setText(netInfo[0].toString());
        macaddress2.setText(netInfo[1].toString());
        maxNetSpeed.setText(Long.toString(Long.parseLong(netInfo[2].toString())/1_000_000) + " MB/s");
        netinterface2.setText(netInfo[3].toString());
    }




    // 
    // // // Functions for third part
    // 

    // Storage
    public void setStorageFilledHeight(double percentage){
        storageFilledRect.setHeight(storageOuterRect.getHeight() * percentage/100);
        storageEmptyRect.setHeight(storageOuterRect.getHeight() * (100.0 - percentage)/100);
    }
    public void setStorageFilledValue(double value){
        usedStorageLabel.setText(String.format("%.02f", value/1024) + " GB");
    }
    public Rectangle getFilledRect(){
        return storageFilledRect;
    }
    public Rectangle getEmptyRect(){
        return storageEmptyRect;
    }
    public Pane getStoragePane(){
        return storagePane;
    }
    public Label getUsedStorageLabel(){
        return usedStorageLabel;
    }
    public Label getUnusedStorageLabel(){
        return unusedStorageLabel;
    }
    public void setStorageEmptyValue(double value){
        unusedStorageLabel.setText(String.format("%.02f", value/1024) + " GB");
    }

    // Memory
    public void setMemoryFilledHeight(double percentage){
        memoryFilledRect.setHeight(memoryOuterRect.getHeight() * percentage/100);
        memoryEmptyRect.setHeight(memoryOuterRect.getHeight() * (100.0 - percentage)/100);
    }
    public void setMemoryFilledValue(double value){
        usedMemoryLabel.setText(String.format("%.02f", value/1024) + " GB");
    }
    public Rectangle getMemoryFilledRect(){
        return memoryFilledRect;
    }
    public Rectangle getMemoryEmptyRect(){
        return memoryEmptyRect;
    }
    public Pane getMemoryPane(){
        return memoryPane;
    }
    public Label getUsedMemoryLabel(){
        return usedMemoryLabel;
    }
    public Label getUnusedMemoryLabel(){
        return unusedMemoryLabel;
    }
    public void setMemoryEmptyValue(double value){
        unusedMemoryLabel.setText(String.format("%.02f", value/1024) + " GB");
    }
    public ToggleButton getSide3(){
        return sideBtn3;
    }

    



    // 
    // // // Other Functions
    // 
    protected void showPane(int index) {
        for (int i = 0; i < panes.length; i++) {
            if (panes[i] != null) {
                panes[i].setVisible(i == index);
                panes[i].setDisable(i != index);
            }
        }
    }
    // 
    // // // Initialize
    // 
    @FXML
    public void initialize(){
        panes = new Pane[]{firstPane, secondPane, thirdPane};
        showPane(0);
        titleLbl.setFont(Font.loadFont(getClass().getResourceAsStream("fonts/Jumpers-6Y3Mv.ttf"), 20));
        sideBtn1.setSelected(true);
        sideGroup = new ToggleGroup();
        sideBtn1.setToggleGroup(sideGroup);
        sideBtn2.setToggleGroup(sideGroup);
        sideBtn3.setToggleGroup(sideGroup);
        int corecnt = si.getCPUCoresAndThread()[0];
        for (int i = 0; i < corecnt; i++){
            coreNames.add(Integer.toString(i));
        }
        coreselect.setValue("0");
        coreselect.setItems(FXCollections.observableArrayList(coreNames));
    }

    // 
    // // // FXML Functions
    // 

    @FXML
    void sidePressed(ActionEvent event) {
        ToggleButton clickedButton = (ToggleButton) event.getSource();
        
        // Update label content based on clicked button
        switch (clickedButton.getId()) {
            case "sideBtn1":
                sideBtn1.setSelected(true);
                showPane(0);
                break;
            case "sideBtn2":
                sideBtn2.setSelected(true);
                showPane(1);
                break;
            case "sideBtn3":
                sideBtn3.setSelected(true);
                showPane(2);
                break;
        }
    }

}
