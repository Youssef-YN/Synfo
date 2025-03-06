# Synfo by Youssef YN.
A General System information application made in Java
## UI
The Graphical user interface is made using Java FX.
The main elements were made using Scene Builder to generate an FXML File which was later on imported to my project.
```java 
FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/FXMLScene.fxml"));
```
To style the application I used a `style.css` file that connected to the FXML File, this allowed the app to have a modern UI even though it had very little elements.

## System Information
To retrieve System information I used a combination of [OSHI](https://github.com/oshi/oshi) (Operating System and Hardware Information) and Windows PowerShell commands.

## Features
- Retrieve your system version, your CPU, GPU and Motherboard.
- Detailed CPU and RAM information with Real-Time core speed.
- Live Storage and RAM usage indicator

## Screenshots
# General Tab

![image](https://github.com/user-attachments/assets/dbd88f38-c47d-4e5e-a0d3-49fe4a58453a)

# Details Tab

![details](https://github.com/user-attachments/assets/9b2e2499-600b-4035-a1c6-5a77d1343ed9)

# Storage Tab

![image](https://github.com/user-attachments/assets/9b12c2d3-eb13-4eab-ac4e-9a37359d7d48)


## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
