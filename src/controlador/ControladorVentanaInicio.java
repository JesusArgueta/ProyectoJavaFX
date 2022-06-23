/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import modelo.Cliente;

/**
 *
 * @author argue
 */
public class ControladorVentanaInicio implements Initializable {

    @FXML
    private TextField jtfUsuario;
    @FXML
    private PasswordField jtfPassword;
    @FXML
    private Button btnAcceso;
    @FXML
    private Button btnSalir;
    @FXML
    private Label lbAyuda;
    private Label moverPatalla;

    private int coor_x;
    private int coor_y;
    private AnchorPane anchorPane;
    @FXML
    private AnchorPane moverPanel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void acceso(ActionEvent event) {
        String usuario = jtfUsuario.getText();
        String pass = jtfPassword.getText();

        if ((usuario.equals("admin")) && (pass.equals("admin"))) {
//            try {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaVentanaPrincipal.fxml"));
//
//                Parent root;
//
//                root = loader.load();
//
//                Scene scene = new Scene(root);
//                Stage stage = new Stage();
//                stage.initModality(Modality.APPLICATION_MODAL);
//                stage.setScene(scene);
//                stage.showAndWait();
//            } catch (IOException ex) {
//                Logger.getLogger(ControladorVentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
//            }
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaVentanaPrincipal.fxml"));

                Parent root = loader.load();

                ControladorVentanaPrincipal controlador = loader.getController();

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.show();

                stage.setOnCloseRequest(e -> controlador.closeWindows());

                Stage myStage = (Stage) this.btnAcceso.getScene().getWindow();
                myStage.close();

            } catch (IOException ex) {
                Logger.getLogger(ControladorVentanaInicio.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Contraseña y usuario mal");
            alert.showAndWait();
        }

    }

    @FXML
    private void pulsarUsuario(MouseEvent event) {
        jtfUsuario.setText("");
    }

    @FXML
    private void pulsarContraseña(MouseEvent event) {
        jtfPassword.setText("");
    }

    @FXML
    private void hoverSalida(MouseEvent event) {
        btnAcceso.setStyle("-fx-background-color: #8B0000");

    }

    @FXML
    private void hoverEntrada(MouseEvent event) {
        btnAcceso.setStyle("-fx-background-color: #bd0000");
    }

    @FXML
    private void salir(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void hoverSalidaS(MouseEvent event) {
        btnSalir.setStyle("-fx-background-color: #8B0000");
    }

    @FXML
    private void hoverEntradas(MouseEvent event) {
        btnSalir.setStyle("-fx-background-color: #bd0000");
    }

    @FXML
    private void pulsarAyuda(MouseEvent event) {
        JOptionPane.showMessageDialog(null, "Usuario: admin y Contraseña: admin");
    }

    @FXML
    private void pintarPanel(MouseEvent event) {
        Stage stage = (Stage) moverPanel.getScene().getWindow();
        stage.setX(coor_x + event.getScreenX());
        stage.setY(coor_y + event.getScreenY());
    }

    @FXML
    private void moverPanel(MouseEvent event) {
        Stage stage = (Stage) moverPanel.getScene().getWindow();

        coor_x = (int) (stage.getX() - event.getScreenX());
        coor_y = (int) (stage.getY() - event.getScreenY());
    }

}
