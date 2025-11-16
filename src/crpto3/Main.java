
package crpto3;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

    public class Main extends Application {

        public static void main(String[] args) {
            launch(args); // ovo pokreće JavaFX i poziva start()
        }


        Map<String, String> check = new HashMap<>();
        String active;
        Path datasave;
        String secret = "123";
        Path AllUsers;
        List<String> allUserNames = new ArrayList<>();
        String baseDir = System.getProperty("user.home") + "/CipherAppData";



        @Override
        public void start(Stage stage) throws Exception{
            Map<String, String> users = new HashMap<>();
            users.put("Miha" , "12345");

            check.put("Miha", "12345");



            Path usersFolder = Paths.get(baseDir, "users");


            Path allUsersFile = usersFolder.resolve("AllUsers.txt");
            AllUsers = allUsersFile;


            if (!Files.exists(usersFolder)) {
                Files.createDirectories(usersFolder);
            }


            if (!Files.exists(allUsersFile)) {
                Files.createFile(allUsersFile);
            }



            List<String> list = Files.readAllLines(allUsersFile);
            for(String str : list){
                try{
                    String[] parts = str.split("#");
                    String username = parts[0];
                    String password = parts[1];
                    check.put(username, password);
                    users.put(username, password);
                    allUserNames.add(username);

                }catch (Exception e){
                    //
                }
            }

            showLogin(stage, users);

        }

        private void showLogin(Stage stage, Map<String, String> users){

            VBox login = new VBox();
            login.setPadding(new Insets(10, 10, 10, 10));
            login.setAlignment(Pos.CENTER);

            Label uvod = new Label("Log in");
            TextField userName = new TextField();
            userName.setPromptText("Username");

            PasswordField password = new PasswordField();
            password.setPromptText("Password");

            TextField passwordVisible = new TextField();
            passwordVisible.setManaged(false);
            passwordVisible.setVisible(false);


            passwordVisible.textProperty().bindBidirectional(password.textProperty());


            Button toggle = new Button("👁"); // možeš staviti i ikonu
            toggle.setOnAction(e -> {
                if (password.isVisible()) {
                    password.setVisible(false);
                    password.setManaged(false);
                    passwordVisible.setVisible(true);
                    passwordVisible.setManaged(true);
                } else {
                    password.setVisible(true);
                    password.setManaged(true);
                    passwordVisible.setVisible(false);
                    passwordVisible.setManaged(false);
                }
            });


            HBox passwordBox = new HBox(password, passwordVisible, toggle);
            passwordBox.setSpacing(5);

            Button loginButton = new Button("Login");
            loginButton.setDefaultButton(true);
            Label message = new Label();

            loginButton.setDefaultButton(true);




            loginButton.setOnAction(e -> {

                active = userName.getText();
                Path loginsRoot = Paths.get(baseDir, "logins");

                try { Files.createDirectories(loginsRoot); } catch (IOException ex) {}

                Path failPath = loginsRoot.resolve("failed.txt");

                // provjera login podataka
                if (users.containsKey(active) && users.get(active).equals(password.getText())) {

                    // USPIJEŠAN LOGIN → kreiraj folder
                    Path folderPath = loginsRoot.resolve(active);
                    try { Files.createDirectories(folderPath); } catch (IOException ex) {}

                    datasave = folderPath;

                    Path successPath = folderPath.resolve("login-success.txt");

                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:MM:yyyy HH:mm:ss");
                    String formattedDate = now.format(formatter);

                    String result = formattedDate + " " + active + " " + password.getText() + "\n";

                    try {
                        Files.write(successPath, result.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                    } catch (IOException ex) {}

                    showMainScene(stage);

                } else {

                    // FAIL LOGIN → ne kreiraš folder!
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:MM:yyyy HH:mm:ss");
                    String formattedDate = now.format(formatter);

                    String result = formattedDate + " " + userName.getText() + " " + password.getText() + "\n";

                    try {
                        Files.write(failPath, result.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                    } catch (IOException ex) {}

                    message.setText("Invalid Username or Password");
                }

            });


            Label register =  new Label("Register");
            TextField userNameField = new TextField();
            userNameField.setPromptText("Username");

            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Password");

            PasswordField confirmPasswordField = new PasswordField();
            confirmPasswordField.setPromptText("Confirm Password");


            TextField passwordFieldVisible = new TextField();
            passwordFieldVisible.setManaged(false);
            passwordFieldVisible.setVisible(false);
            passwordFieldVisible.textProperty().bindBidirectional(passwordField.textProperty());

            Button togglePassword = new Button("👁");
            togglePassword.setOnAction(e -> {
                if (passwordField.isVisible()) {
                    passwordField.setVisible(false);
                    passwordField.setManaged(false);
                    passwordFieldVisible.setVisible(true);
                    passwordFieldVisible.setManaged(true);
                } else {
                    passwordField.setVisible(true);
                    passwordField.setManaged(true);
                    passwordFieldVisible.setVisible(false);
                    passwordFieldVisible.setManaged(false);
                }
            });
            HBox passwordBoxRegister = new HBox(passwordField, passwordFieldVisible, togglePassword);
            passwordBoxRegister.setSpacing(5);


            TextField confirmPasswordFieldVisible = new TextField();
            confirmPasswordFieldVisible.setManaged(false);
            confirmPasswordFieldVisible.setVisible(false);
            confirmPasswordFieldVisible.textProperty().bindBidirectional(confirmPasswordField.textProperty());

            Button toggleConfirmPassword = new Button("👁");
            toggleConfirmPassword.setOnAction(e -> {
                if (confirmPasswordField.isVisible()) {
                    confirmPasswordField.setVisible(false);
                    confirmPasswordField.setManaged(false);
                    confirmPasswordFieldVisible.setVisible(true);
                    confirmPasswordFieldVisible.setManaged(true);
                } else {
                    confirmPasswordField.setVisible(true);
                    confirmPasswordField.setManaged(true);
                    confirmPasswordFieldVisible.setVisible(false);
                    confirmPasswordFieldVisible.setManaged(false);
                }
            });
            HBox confirmPasswordBox = new HBox(confirmPasswordField, confirmPasswordFieldVisible, toggleConfirmPassword);
            confirmPasswordBox.setSpacing(5);


            Label code = new Label("Code");

            TextField codeField = new TextField();
            codeField.setPromptText("Code");



            Button registerButton = new Button("Register");
            Label messag2 = new Label();



            registerButton.setOnAction(e -> {
                String confirm = codeField.getText();

                if(userNameField.getText() == null){
                    messag2.setText("Enter Valid Username");
                }else if(passwordFieldVisible.getText() == null){
                    messag2.setText("Enter Valid Password");
                }else if(allUserNames.contains(userNameField.getText())) {
                    messag2.setText("Username Already Exists");
                }
                else if(!passwordField.getText().equals(confirmPasswordField.getText())){
                    messag2.setText("Passwords do not match");
                }
                else if(!confirm.equals(secret)){
                    messag2.setText("Invalid Code");
                }
                else{
                    String result = userNameField.getText() + "#" + passwordField.getText() +"\n";
                    try {
                        Files.write(AllUsers, result.getBytes(), StandardOpenOption.APPEND);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    users.put(userNameField.getText(), passwordField.getText());
                    check.put(userNameField.getText(), passwordField.getText());

                    messag2.setText("User successfully registered");
                }
            });


            login.getChildren().addAll(uvod, userName, passwordBox, loginButton, message);
            login.getChildren().addAll(
                    register,
                    userNameField,
                    passwordBoxRegister,
                    confirmPasswordBox,
                    code,
                    codeField,
                    registerButton,
                    messag2
            );
            Scene loginScene = new Scene(login, 500, 500);
            stage.setScene(loginScene);
            stage.show();




        }



        private void showMainScene(Stage stage)  {


            Random rand = new Random();
            BlefGenerator blefGen = new BlefGenerator();
            LetterGenerator letterGen = new LetterGenerator();


            Map<String, String> decodeMap = getStringStringMap();

            VBox layout = new VBox();
            layout.setPadding(new Insets(10));


            Label name = new Label("Welcome to Crypto Boss");


            Label label1 = new Label("Code:");
            label1.setStyle("-fx-font-size: 18px;-fx-text-fill: darkblue;");

            TextField code = new TextField();
            code.setPromptText("Enter Message");
            code.setStyle(
                    "-fx-font-size: 14px;" +
                            "-fx-pref-height: 30px;" +
                            "-fx-text-fill: black;" +
                            "-fx-prompt-text-fill: gray;"
            );



            Button generate = new Button("Generate");
            generate.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black;");
            code.setOnAction(e -> generate.fire());

            TextArea result1 = new TextArea();
            result1.setPrefRowCount(5); // koliko redova će se prikazati
            result1.setWrapText(true);   // automatsko prelamanje teksta
            result1.setEditable(false);  // da korisnik ne mijenja rezultat, samo kopira
            result1.setStyle(
                    "-fx-font-size: 14px;" +
                            "-fx-text-fill: black;"
            );


            generate.setOnAction(e -> {

                ArrayList<String> list = new ArrayList<>();
                for (int i = 0; i < code.getText().length(); i++) {

                    if (i + 1 <= code.getText().length()) {
                        String word = code.getText().substring(i, i + 1);
                        list.add(word);
                    }
                }



                StringBuilder sb = new StringBuilder();
                for(String word : list) {
                    for(var entry : decodeMap.entrySet()) {
                        if(word.equals(entry.getValue())) {
                            sb.append(entry.getKey());

                            if(rand.nextBoolean()) {
                                sb.append(String.format("%s", blefGen.generateBlefBlock()));
                            }
                        }
                    }
                }



                result1.setText(sb.toString());

            });


            Image image = new Image(
                    getClass().getResource("/Crpto3/resources/app.png").toExternalForm()
            );

            BackgroundImage backgroundImage = new BackgroundImage(
                    image,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(
                            100, 100,      // širina i visina u %
                            true, true,     // width i height su relativni
                            false,          // contain = false
                            true            // cover = true → popunjava cijeli prostor, može crop
                    )
            );



            Region region = new Region();
            VBox.setVgrow(region, Priority.ALWAYS); // omogućava vertikalno rastezanje
            region.setBackground(new Background(backgroundImage));


            layout.getChildren().addAll(name, label1, code, result1, generate, region);

            Label save = new Label("Save");
            save.setStyle("-fx-font-size: 18px;-fx-text-fill: darkblue;");

            TextField saved = new TextField();
            saved.setPromptText("Enter Message");
            saved.setStyle( "-fx-font-size: 14px;" +
                    "-fx-pref-height: 30px;" +
                    "-fx-text-fill: black;" +
                    "-fx-prompt-text-fill: gray;");

            TextArea savedResult = new TextArea();
            savedResult.setPrefRowCount(1); // koliko redova će se prikazati
            savedResult.setWrapText(true);   // automatsko prelamanje teksta
            savedResult.setEditable(false);  // da korisnik ne mijenja rezultat, samo kopira
            savedResult.setStyle(
                    "-fx-font-size: 14px;" +
                            "-fx-text-fill: black;"
            );


            Path Data = datasave.resolve("Data.txt");
            Button saveButton = new Button("Save");
            saveButton.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black;");
            saved.setOnAction(e -> saveButton.fire());
            saveButton.setOnAction(e -> {
                StringBuilder text = new StringBuilder();
                text.append(saved.getText());
                text.append("\n");

                try{
                    LocalDateTime now2 = LocalDateTime.now();
                    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd:MM:yyyy HH:mm:ss");
                    String formattedDate2 = now2.format(formatter2);

                    String result = formattedDate2 + " " + text.toString() + "\n";

                    Files.write(Data, result.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
                    savedResult.setText("Message Saved");
                }catch(Exception ex){
                    //
                }
            });


            layout.getChildren().addAll(save, saved, savedResult,saveButton);


            Label load = new Label("Load Data");
            load.setStyle("-fx-font-size: 18px;-fx-text-fill: darkblue;");

            TextArea loadResult = new TextArea();
            loadResult.setPrefRowCount(5); // koliko redova će se prikazati
            loadResult.setWrapText(true);   // automatsko prelamanje teksta
            loadResult.setEditable(false);  // da korisnik ne mijenja rezultat, samo kopira
            loadResult.setStyle(
                    "-fx-font-size: 14px;" +
                            "-fx-text-fill: black;"
            );


            Button loadButton = new Button("Load");
            loadButton.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black;");
            loadButton.setDefaultButton(true);
            loadButton.setOnAction(e -> {
                try {
                    List<String> list = Files.readAllLines(Data);
                    StringBuilder sb = new StringBuilder();
                    for(String word : list) {
                        sb.append(word);
                        sb.append("\n");
                    }
                    loadResult.setText(sb.toString());
                } catch (IOException ex) {
                    //
                }
            });

            layout.getChildren().addAll(load, loadButton, loadResult);






            Label name2 = new Label("Decode:");
            name2.setStyle("-fx-font-size: 18px;-fx-text-fill: darkblue;");

            TextField decode = new TextField();
            decode.setPromptText("Enter Message");
            decode.setStyle(
                    "-fx-font-size: 14px;" +
                            "-fx-pref-height: 30px;" +
                            "-fx-text-fill: black;" +
                            "-fx-prompt-text-fill: gray;"
            );

            TextArea result2 = new TextArea();
            result2.setPrefRowCount(5); // koliko redova će se prikazati
            result2.setWrapText(true);   // automatsko prelamanje teksta
            result2.setEditable(false);  // da korisnik ne mijenja rezultat, samo kopira
            result2.setStyle(
                    "-fx-font-size: 14px;" +
                            "-fx-text-fill: black;"
            );


            Button decodeButton = new Button("Generate");
            decodeButton.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black;");
            decode.setOnAction(e -> decodeButton.fire());
            decodeButton.setOnAction(e -> {
                ArrayList<String> list = new ArrayList<>();

                if (decode.getText().length() % 4 != 0) {
                    decode.setText("Wrong code");
                    return;
                }

                for (int i = 0; i < decode.getText().length(); i = i + 4) {
                    if (i + 4 <= decode.getText().length()) {
                        String word = decode.getText().substring(i, i + 4);
                        list.add(word);
                    } else {
                        String word = decode.getText().substring(i);
                        list.add(word);
                    }

                }

                StringBuilder result = new StringBuilder();

                for (String word : list) {
                    if (decodeMap.containsKey(word)) {
                        result.append(decodeMap.get(word));
                    }
                }
                result2.setText(result.toString());

                });




            layout.getChildren().addAll(name2, decode, decodeButton, result2);


            HBox hbox = new HBox();
            hbox.setAlignment(Pos.CENTER);
            hbox.setSpacing(20);

            Label name3 = new Label("Kills: ");
            name3.setStyle("-fx-font-size: 18px;-fx-text-fill: darkblue;");

            TextField kills = new TextField();
            kills.setPromptText("Enter Message");
            kills.setStyle(
                    "-fx-font-size: 14px;" +
                            "-fx-pref-height: 30px;" +
                            "-fx-text-fill: black;" +
                            "-fx-prompt-text-fill: gray;"
            );


            Label name4 = new Label("Deaths: ");
            name4.setStyle("-fx-font-size: 18px;-fx-text-fill: darkblue;");

            TextField deaths = new TextField();
            deaths.setPromptText("Enter Message");
            deaths.setStyle(
                    "-fx-font-size: 14px;" +
                            "-fx-pref-height: 30px;" +
                            "-fx-text-fill: black;" +
                            "-fx-prompt-text-fill: gray;"
            );


            Label name5 = new Label("Wanted K/D: ");
            name5.setStyle("-fx-font-size: 18px;-fx-text-fill: darkblue;");

            TextField wantedK = new TextField();
            wantedK.setPromptText("Enter Message");
            wantedK.setStyle(
                    "-fx-font-size: 14px;" +
                            "-fx-pref-height: 30px;" +
                            "-fx-text-fill: black;" +
                            "-fx-prompt-text-fill: gray;"
            );


            Label name6 = new Label("Game limit: ");
            name6.setStyle("-fx-font-size: 18px;-fx-text-fill: darkblue;");

            TextField gameLimit = new TextField();
            gameLimit.setPromptText("Enter Message");
            gameLimit.setStyle(
                    "-fx-font-size: 14px;" +
                            "-fx-pref-height: 30px;" +
                            "-fx-text-fill: black;" +
                            "-fx-prompt-text-fill: gray;"
            );

            Label name7 = new Label("Deaths limit: ");
            name7.setStyle("-fx-font-size: 18px;-fx-text-fill: darkblue;");

            TextField deathsLimit = new TextField();
            deathsLimit.setPromptText("Enter Message");
            deathsLimit.setStyle(
                    "-fx-font-size: 14px;" +
                            "-fx-pref-height: 30px;" +
                            "-fx-text-fill: black;" +
                            "-fx-prompt-text-fill: gray;"
            );

            HBox hbox2 = new HBox();
            hbox2.setAlignment(Pos.BASELINE_LEFT);
            hbox2.setSpacing(40);



            hbox.getChildren().addAll(name3, kills, name4, deaths, name5, wantedK);




            Button calculate = new Button("Calculate");
            calculate.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black;");
            kills.setOnAction(e -> generate.fire());
            deaths.setOnAction(e -> generate.fire());
            wantedK.setOnAction(e -> generate.fire());
            gameLimit.setOnAction(e -> generate.fire());
            deathsLimit.setOnAction(e -> generate.fire());




            TextArea result3 = new TextArea();
            result3.setPrefRowCount(2); // koliko redova će se prikazati
            result3.setWrapText(true);   // automatsko prelamanje teksta
            result3.setEditable(false);  // da korisnik ne mijenja rezultat, samo kopira
            result3.setStyle(
                    "-fx-font-size: 14px;" +
                            "-fx-text-fill: black;"
            );


            calculate.setOnAction(e -> {
               double killes = Double.parseDouble(kills.getText());//10
               double deaded = Double.parseDouble(deaths.getText());//5
               double wanted = Double.parseDouble(wantedK.getText());//3
               double limit = Double.parseDouble(gameLimit.getText());//5
               double d = Double.parseDouble(deathsLimit.getText());


                result3.setText(KDService.calculate(killes, deaded, wanted, limit, d));


            });


            hbox2.getChildren().addAll(name6,  gameLimit, name7, deathsLimit,  calculate);

            layout.getChildren().addAll(hbox, hbox2, result3);







            Scene scene = new Scene(layout, 1200, 900);

            stage.setTitle("Crypto");
            stage.setScene(scene);
            stage.show();



        }
        private static Map<String, String> getStringStringMap() {
            Map<String, String> decodeMap = new HashMap<>();

            decodeMap.put("@X!%","a");
            decodeMap.put("9k#*","b");
            decodeMap.put("vZ+=","c");
            decodeMap.put("p3-%","d");
            decodeMap.put("Lq!*","e");
            decodeMap.put("r7#=","f");
            decodeMap.put("m0+%","g");
            decodeMap.put("Q2-!","h");
            decodeMap.put("u5*#","i");
            decodeMap.put("xK=%","j");
            decodeMap.put("dN!+","k");
            decodeMap.put("Sa#-","l");
            decodeMap.put("w1%=","m");
            decodeMap.put("Eb!*","n");
            decodeMap.put("Yf#%","o");
            decodeMap.put("nJ+=","p");
            decodeMap.put("o8-%","q");
            decodeMap.put("Cc!#","r");
            decodeMap.put("3T*+","s");
            decodeMap.put("2z=-","t");
            decodeMap.put("Xx%!","u");
            decodeMap.put("Hz#*","v");
            decodeMap.put("Ze+=","w");
            decodeMap.put("g9-%","x");
            decodeMap.put("Bl!#","y");
            decodeMap.put("tR*+","z");
            decodeMap.put("F2=-","0");
            decodeMap.put("Vk%!","1");
            decodeMap.put("jP#*","2");
            decodeMap.put("a6+=","3");
            decodeMap.put("Nq-%","4");
            decodeMap.put("Z0!#","5");
            decodeMap.put("h7*+","6");
            decodeMap.put("Ku=-","7");
            decodeMap.put("O1%!","8");
            decodeMap.put("bM#*","9");
            decodeMap.put("S+p="," ");
            decodeMap.put(":@#=",":");


            decodeMap.put("Ax!#","A");
            decodeMap.put("Bz@%","B");
            decodeMap.put("Cy$^","C");
            decodeMap.put("Dw&*","D");
            decodeMap.put("Ex(+","E");
            decodeMap.put("Fy)-","F");
            decodeMap.put("Gz_=","G");
            decodeMap.put("Ha{}","H");
            decodeMap.put("Ii:]","I");
            decodeMap.put("Jj<>","J");
            decodeMap.put("Kk:/","K");
            decodeMap.put("Ll;'","L");
            decodeMap.put("Mm:/","M");
            decodeMap.put("Nn|~","N");
            decodeMap.put("Oo.,","O");
            decodeMap.put("Pp?`","P");
            decodeMap.put("Qq@#","Q");
            decodeMap.put("Rr$%","R");
            decodeMap.put("Ss^&","S");
            decodeMap.put("Tt)_","T");
            decodeMap.put("Uu+-","U");
            decodeMap.put("Vv{}","V");
            decodeMap.put("Ww[]","W");
            decodeMap.put("Xx:'","X");
            decodeMap.put("Yy<>","Y");
            decodeMap.put("Zz.?","Z");
            return decodeMap;
        }





    }