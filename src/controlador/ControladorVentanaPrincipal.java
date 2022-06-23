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
import modelo.Cliente;

/**
 *
 * @author argue
 */
public class ControladorVentanaPrincipal implements Initializable {

    @FXML
    private Button btnAgregar;

    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApellido;
    @FXML
    private TableColumn colDireccion;
    @FXML
    private TableColumn colEdad;

    private ObservableList<Cliente> clientes;
    private ObservableList<Cliente> filtroClientes;
    private int coor_x;
    private int coor_y;

    @FXML
    private TableView<Cliente> tblClientes;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private TextField buscarNombre;
    @FXML
    private AnchorPane moverPantalla;
    @FXML
    private Button btnSalir;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clientes = FXCollections.observableArrayList();
        filtroClientes = FXCollections.observableArrayList();
        this.tblClientes.setItems(clientes);
        this.colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.colApellido.setCellValueFactory(new PropertyValueFactory("apellido"));
        this.colDireccion.setCellValueFactory(new PropertyValueFactory("direccion"));
        this.colEdad.setCellValueFactory(new PropertyValueFactory("edad"));
    }

    public void closeWindows() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaVentanaInicio.fxml"));

            Parent root = loader.load();

            ControladorVentanaInicio controlador = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();

            Stage myStage = (Stage) this.btnSalir.getScene().getWindow();
            myStage.close();

        } catch (IOException ex) {
            Logger.getLogger(ControladorVentanaInicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void agregarCliente(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaVentanaAgregar.fxml"));

            Parent root = loader.load();

            ControladorVentanaAgregar controlador = loader.getController();
            controlador.initAtributtes(clientes);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.showAndWait();

            Cliente cli = controlador.getCliente();

            if (cli != null) {
                this.clientes.add(cli);
                if (cli.getNombre().toLowerCase().contains(this.buscarNombre.getText().toLowerCase())) {
                    this.filtroClientes.add(cli);
                }
                this.tblClientes.refresh();
            }

        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }

    }

    @FXML
    private void seleccionarCliente(MouseEvent event) {

        Cliente cli = tblClientes.getSelectionModel().getSelectedItem();

    }

    @FXML
    private void modificarCliente(ActionEvent event) {

        Cliente cli = tblClientes.getSelectionModel().getSelectedItem();

        if (cli == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar una cliente");
            alert.showAndWait();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaVentanaModificar.fxml"));

                Parent root = loader.load();

                ControladorVentanaModificar controlador = loader.getController();
                controlador.initAtributtes(clientes, cli);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setResizable(false);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.showAndWait();

                Cliente aux = controlador.getCliente();

                if (aux != null) {
                    if (!aux.getNombre().toLowerCase().contains(this.buscarNombre.getText().toLowerCase())) {
                        this.filtroClientes.remove(aux);
                    }
                    this.tblClientes.refresh();
                }

            } catch (IOException ex) {
                Logger.getLogger(ControladorVentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    private void eliminarCliente(ActionEvent event) {

        Cliente cli = tblClientes.getSelectionModel().getSelectedItem();

        if (cli == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar una cliente");
            alert.showAndWait();
        } else {
            this.clientes.remove(cli);
            this.filtroClientes.remove(cli);
            this.tblClientes.refresh();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Información");
            alert.setContentText("Se ha borrado el cliente");
            alert.showAndWait();
        }

    }

    @FXML
    private void buscarNombre(KeyEvent event) {

        String nombre = this.buscarNombre.getText();

        if (nombre.isEmpty()) {
            this.tblClientes.setItems(clientes);
        } else {
            this.filtroClientes.clear();

            for (Cliente cli : this.clientes) {
                if (cli.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                    this.filtroClientes.add(cli);
                }
            }

            this.tblClientes.setItems(filtroClientes);

        }

    }

    @FXML
    private void salir(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaVentanaInicio.fxml"));

            Parent root = loader.load();

            ControladorVentanaInicio controlador = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();

            Stage myStage = (Stage) this.btnSalir.getScene().getWindow();
            myStage.close();

        } catch (IOException ex) {
            Logger.getLogger(ControladorVentanaInicio.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    private void hoverSalidaA(MouseEvent event) {
        btnAgregar.setStyle("-fx-background-color: #8B0000");
    }

    @FXML
    private void hoverEntradaA(MouseEvent event) {
        btnAgregar.setStyle("-fx-background-color: #bd0000");
    }

    @FXML
    private void hoverSalidaM(MouseEvent event) {
        btnModificar.setStyle("-fx-background-color: #8B0000");
    }

    @FXML
    private void hoverEntradaM(MouseEvent event) {
        btnModificar.setStyle("-fx-background-color: #bd0000");
    }

    @FXML
    private void hoverSalidaE(MouseEvent event) {
        btnEliminar.setStyle("-fx-background-color: #8B0000");
    }

    @FXML
    private void hoverEntradaE(MouseEvent event) {
        btnEliminar.setStyle("-fx-background-color: #bd0000");
    }

    @FXML
    private void hoverSalidaS(MouseEvent event) {
        btnSalir.setStyle("-fx-background-color: #8B0000");
    }

    @FXML
    private void hoverEntradaS(MouseEvent event) {
        btnSalir.setStyle("-fx-background-color: #bd0000");
    }

}
