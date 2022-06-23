/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.Cliente;

/**
 *
 * @author argue
 */
public class ControladorVentanaModificar implements Initializable {

    @FXML
    private TextField jtfNombre;
    @FXML
    private TextField jtApellido;
    @FXML
    private TextField jtDireccion;
    @FXML
    private TextField jtEdad;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnSalir;

    private Cliente cliente;
    
    public int coor_x;
    public int coor_y;

    private ObservableList<Cliente> clientes;
    @FXML
    private AnchorPane moverPantalla;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void initAtributtes(ObservableList<Cliente> clientes, Cliente cli) {
        this.clientes = clientes;
        this.cliente = cli;
        this.jtfNombre.setText(cli.getNombre());
        this.jtApellido.setText(cli.getApellido());
        this.jtDireccion.setText(cli.getDireccion());
        this.jtEdad.setText(cli.getEdad() + "");
    }

    @FXML
    private void guardarCliente(ActionEvent event) {

        try {

            String nombre = jtfNombre.getText();
            String apellido = jtApellido.getText();
            String direccion = jtDireccion.getText();
            int edad = new Integer(jtEdad.getText());

            Cliente cli = new Cliente(nombre, apellido, direccion, edad);

            if (!clientes.contains(cli)) {

                this.cliente.setNombre(nombre);
                this.cliente.setApellido(apellido);
                this.cliente.setDireccion(direccion);
                this.cliente.setEdad(edad);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Información");
                alert.setContentText("Se ha modificado correctamente");
                alert.showAndWait();

                Stage stage = (Stage) this.btnGuardar.getScene().getWindow();
                stage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("El cliente ya existe");
                alert.showAndWait();
            }

        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("En edad debes de introducir un número");
            alert.showAndWait();
        }

    }

    @FXML
    private void salir(ActionEvent event) {
        this.cliente = null;
        Stage stage = (Stage) this.btnGuardar.getScene().getWindow();
        stage.close();
    }

    public Cliente getCliente() {
        return cliente;
    }

    @FXML
    private void hoverSalidaG(MouseEvent event) {
        btnGuardar.setStyle("-fx-background-color: #8B0000");
    }

    @FXML
    private void hoverEntradaG(MouseEvent event) {
        btnGuardar.setStyle("-fx-background-color: #bd0000");
    }

    @FXML
    private void hoverEntradaS(MouseEvent event) {
        btnSalir.setStyle("-fx-background-color: #bd0000");
    }

    @FXML
    private void pintarPanel(MouseEvent event) {
        Stage stage = (Stage) moverPantalla.getScene().getWindow();
        stage.setX(coor_x + event.getScreenX());
        stage.setY(coor_y + event.getScreenY());
    }

    @FXML
    private void moverPanel(MouseEvent event) {
        Stage stage = (Stage) moverPantalla.getScene().getWindow();

        coor_x = (int) (stage.getX() - event.getScreenX());
        coor_y = (int) (stage.getY() - event.getScreenY());
    }

    @FXML
    private void hoverSalidaS(MouseEvent event) {
        btnSalir.setStyle("-fx-background-color: #8B0000");
    }

}
