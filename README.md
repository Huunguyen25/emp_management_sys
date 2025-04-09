# Employee Manager program 
## Overview
Program is an employement management system, that alows HR admin to main employee data and regular employees to view their data. Program uses Gradle to provide JavaFX dependencies

> [!NOTE]
> ##### The program uses Gradle v8.8 which is recommends JavaFX v22 
> ##### Project default to Gradle v8.8, however compatability issues do happen
> ##### You can check by running gradle version with ```./gradlew --version```
> ##### The JBDC connector is default to version 8.1.0 however you can change version in ```/app/build.gradle``` file
> #### If issues still persist consider going to [FIX](##Issues)

## 🚀 Getting Started
#### Installation
```bash
git clone repo
cd employee_manager
```


#### Build and Run
  > ```bash  
  > ./gradlew build
  > ./gradlew run
  > ```

## Program Structure
```
src/
├── main/
│   ├── java/
│   │   └── employee_manager/
│   │       ├── controller/
│   │       │   └── LoginController.java
│   │       │   └── AdminController.java
│   │       │   └── RegularEmployee.java
│   │       ├── model/
│   │       │   └── Model.java
│   │       ├── view/
│   │       │   └── ViewManager.java
│   │       └── App.java
│   └── resources/
│       └── employee_manager/
│           └── view/
│               └── Login.fxml
│               └── Admin.fxml
│               └── RegularEmployee.fxml
└── test/
    └── java/
        └── employee_manager/
            └── AppTest.java
```

## Key Files and Descriptions
#### Entry point: 
- **App.java**: The main application entry point that loads the JavaFX UI

#### Model: 
- **Model.java**: Model class that will be managing logic of the program

#### View: 
- **ViewManager.java**: Will me managing all Resource Views 

#### Controllers:
- **LoginController.java**: Controls the Login.fxml view and handles user interactions
- **AdminController.java**: Controls the Admin.fxml view and handles user admin inputs
- **RegularEmployeeController.java**: Controls the RegularEmployee.fxml view and handles regular employee inputs
  
#### Resource Views: 
- **Login.fxml**: The login screen UI layout
- **Admin.fxml**: The Admin screen UI layout
- **RegularEmployee.fxml**: The RegularEmployee screen UI layout



## Fixing Issues