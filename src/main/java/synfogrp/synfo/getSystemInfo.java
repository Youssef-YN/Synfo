package synfogrp.synfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import oshi.SystemInfo;
import oshi.hardware.GraphicsCard;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;

public class getSystemInfo {
    private final SystemInfo systemInfo;
    private final HardwareAbstractionLayer hardware;
    private final OperatingSystem operatingSystem;
    private GraphicsCard graphicsCard;
    public getSystemInfo() {
        systemInfo = new SystemInfo();
        hardware = systemInfo.getHardware();
        operatingSystem = systemInfo.getOperatingSystem();
        try{
            graphicsCard = hardware.getGraphicsCards().get(1);
        } catch (Exception e){
            graphicsCard = hardware.getGraphicsCards().get(0);
        }
    }


    // 
    // // // For First Part
    // 
    public Object getOs() {
        return operatingSystem;
    }

    public Object getMb(){
        if (cmdType() == "W11" || cmdType() == "W10"){
            return getWin11MB();
        }
        else if (cmdType() == "Linux"){
            return "Linux Motherboard";
        }
        else{
            return hardware.getComputerSystem().getModel();
        }
    }

    public Object getCPU(){
        return hardware.getProcessor().getProcessorIdentifier().getName();
    }

    public Object getGPU(){
        return graphicsCard.getName();
    }

    public Object getMem(){
        return formatBytes(hardware.getMemory().getTotal());
    }

    public Object getSto(){
        List<Long> total = new ArrayList<>();
        long finalSto = 0;
        hardware.getDiskStores().forEach(disk -> {
            total.add(disk.getSize());
        });
        for(long x: total){
            finalSto += x;
        }
        return formatBytes(finalSto);
    }

    public Object getArc(){
        return System.getProperty("os.arch").toUpperCase();
    }

    public Object getUsr(){
        return System.getProperty("user.name");
    }

    public Object getMan(){
        return hardware.getComputerSystem().getManufacturer();
    }
    



    // 
    // // // For Second Part
    // 
    public Object getCPUName(){
        String input = getCPU().toString().trim();
        int coreIndex = input.indexOf("Core");
        
        if (coreIndex != -1 && coreIndex > 2) {
            String result = input.substring(0, coreIndex - 2);
            return result; 
        } else {
            return getCPU();
        }
    }
    
    public int[] getCPUCoresAndThread(){
        int[] CoresAndThreads;
        CoresAndThreads = new int[2];
        CoresAndThreads[0] = hardware.getProcessor().getPhysicalProcessorCount();
        CoresAndThreads[1] = hardware.getProcessor().getLogicalProcessorCount();
        return CoresAndThreads;
    }

    public Object getCPUSocket(){
        return windowsPowershell("(Get-WmiObject -Class Win32_Processor).SocketDesignation");
    }

    public Object getCPUArc(){
        return hardware.getProcessor().getProcessorIdentifier().getMicroarchitecture();
    }

    public long getRAMFreq(){
        return hardware.getMemory().getPhysicalMemory().get(0).getClockSpeed();
    }

    public String getRAMType(){
        return parseMemoryType(Integer.parseInt(windowsPowershell("(Get-WmiObject -Class Win32_PhysicalMemory | Select-Object -First 1).SMBIOSMemoryType")));
    }

    public long getMaxFreq(){
        return hardware.getProcessor().getMaxFreq();
    }

    public double getCurrentFreq(int i){
        return hardware.getProcessor().getCurrentFreq()[i];
    }


    // For Second Half Part 2
    public Object[] getGPUInfo(){
        Object[] gpuInfo = new Object[3];
        gpuInfo[0] = graphicsCard.getName();
        if (gpuInfo[0].toString().toUpperCase().contains("NVIDIA")){
            gpuInfo[1] = Long.parseLong(windowsPowershell("nvidia-smi --query-gpu=memory.total --format=csv,noheader,nounits")) / 1_000;
        }
        else{
            gpuInfo[1] = graphicsCard.getVRam() / 1_000_000;
        }
        gpuInfo[2] = graphicsCard.getVersionInfo().toString().replace("DriverVersion=", "");
        return gpuInfo;
    }

    public Object[] getNetInfo(){
        Object[] netInfo = new Object[4];
        netInfo[0] = hardware.getNetworkIFs().get(0).getDisplayName();
        netInfo[1] = hardware.getNetworkIFs().get(0).getMacaddr();
        netInfo[2] = hardware.getNetworkIFs().get(0).getSpeed();
        netInfo[3] = hardware.getNetworkIFs().get(0).getName();
        return netInfo;
    }

    public long[] getStorageInfo(){
        FileSystem fs = systemInfo.getOperatingSystem().getFileSystem();
        long totalStorage = 0;
        long totalUnusedStorage = 0;
        for (OSFileStore fileStore : fs.getFileStores()) {
            totalStorage += fileStore.getTotalSpace();
            long usableSpace = fileStore.getUsableSpace();
            totalUnusedStorage += usableSpace;
        }
        long[] storageInfo = {totalStorage/1_048_576, totalUnusedStorage/1_048_576};
        return storageInfo;
    }

    // Memory
    public long[] getMemoryInfo(){
        long[] memoryInfo = new long[2];
        memoryInfo[0] = hardware.getMemory().getTotal()/1_048_576;
        memoryInfo[1] = hardware.getMemory().getAvailable()/1_048_576;
        return memoryInfo;
    }


    // Format bytes for storage and memory
    private String formatBytes(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        } else if (bytes < 1024 * 1024) {
            return String.format("%.2f KB", bytes / 1024.0);
        } else if (bytes < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", bytes / (1024.0 * 1024));
        } else {
            return String.format("%.2f GB", bytes / (1024.0 * 1024 * 1024));
        }
    }

    // To check which PS to use
    private static String cmdType() {
        if (System.getProperty("os.name").toLowerCase().contains("nux")){
            return "Linux";
        }
        else if (System.getProperty("os.name").toLowerCase().contains("windows 11")){
            return "W11";
        }
        else if (System.getProperty("os.name").toLowerCase().contains("windows 10")){
            return "W10";
        }
        else{
            return "Unknown";
        }
    }

    // Get the Win11 Motherboard using PS
    private static String getWin11MB(){
        String powershellOutput = windowsPowershell("Get-WmiObject win32_baseboard | Format-List Product");
        powershellOutput = powershellOutput.replace("Product : ", "");
        return powershellOutput;
    }

    // Simplify the PS command
    private static String windowsPowershell(String command){
        StringBuilder output = new StringBuilder();
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                "powershell.exe", "-Command", command
            );

            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return output.toString().trim();
    }
    
    // Parse memory code
    private String parseMemoryType(int memoryType) {
        switch (memoryType) {
            case 0: return "Unknown";
            case 1: return "Other";
            case 2: return "DRAM";
            case 3: return "Synchronous DRAM";
            case 4: return "Cache DRAM";
            case 5: return "EDO";
            case 6: return "EDRAM";
            case 7: return "VRAM";
            case 8: return "SRAM";
            case 9: return "RAM";
            case 10: return "ROM";
            case 11: return "Flash";
            case 12: return "EEPROM";
            case 13: return "FEPROM";
            case 14: return "EPROM";
            case 15: return "CDRAM";
            case 16: return "3DRAM";
            case 17: return "SDRAM";
            case 18: return "SGRAM";
            case 19: return "RDRAM";
            case 20: return "DDR";
            case 21: return "DDR2";
            case 22: return "DDR2 FB-DIMM";
            case 24: return "DDR3";
            case 25: return "FBD2";
            case 26: return "DDR4";
            case 27: return "LPDDR";
            case 28: return "LPDDR2";
            case 29: return "LPDDR3";
            case 30: return "LPDDR4";
            case 31: return "Logical non-volatile device";
            case 32: return "HBM";
            case 33: return "HBM2";
            case 34: return "DDR5";
            case 35: return "LPDDR5";
            default: return "Unknown";
        }
    }

    // End of class
}
