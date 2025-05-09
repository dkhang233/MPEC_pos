package com.pos.app.controller;

import java.io.IOException;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import com.pos.app.api.UserApi;
import com.pos.app.dto.LoginDto;
import com.pos.app.model.Login;
import com.pos.app.store.UserStore;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LoginController {

    private final UserApi userApi = new UserApi();

    @FXML
    private Pane loginContainer;

    @FXML
    public void initialize() {
        // Initialization logic here
        Login loginInfo = new Login();

        Form loginForm = Form.of(
                Group.of(
                        Field.ofStringType(loginInfo.getUsername())
                                .label("Username")
                                .required(true),

                        Field.ofStringType(loginInfo.getPassword())
                                .label("Password")
                                .required(true)));

        FormRenderer formRenderer = new FormRenderer(loginForm);
        formRenderer.setPrefSize(500, 280);

        Button loginButton = new Button("Đăng nhập");
        Label errorLabel = new Label();

        loginButton.setOnAction(event -> {
            if (loginForm.isValid()) {
                loginForm.persist();
                String username = loginInfo.getUsername().get();
                String password = loginInfo.getPassword().get();

                LoginDto loginDto = new LoginDto(username, password);
                // Kiểm tra thông tin đăng nhập (ví dụ đơn giản)
                if (userApi.login(loginDto)) {
                    // try {
                    // // Chuyển sang màn hình Home
                    // // switchToHome();
                    // } catch (IOException e) {
                    // errorLabel.setText("Lỗi khi chuyển màn hình!");
                    // }
                    errorLabel.setText("Đăng nhập thành công!");
                    loginSuccess(loginDto);
                } else {
                    errorLabel.setText("Sai tên đăng nhập hoặc mật khẩu!");
                }
            } else {
                errorLabel.setText("Vui lòng điền đầy đủ thông tin!");
            }
        });

        // Tạo VBox
        VBox layout = new VBox(10, formRenderer, loginButton, errorLabel);
        layout.setAlignment(Pos.CENTER); // Căn giữa các thành phần bên trong VBox

        // Bọc VBox trong StackPane để căn giữa trong loginContainer
        StackPane centeredPane = new StackPane(layout);
        centeredPane.setAlignment(Pos.CENTER); // Căn giữa StackPane trong loginContainer

        // Thêm StackPane vào loginContainer
        loginContainer.getChildren().add(centeredPane);
    }

    private void loginSuccess(LoginDto loginDto) {
        UserStore.username = loginDto.getUsername();
        // loginContainer.getParent().getChi
    }
}
