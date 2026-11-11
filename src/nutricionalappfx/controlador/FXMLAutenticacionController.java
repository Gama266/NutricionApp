/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package nutricionalappfx.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import nutricionalappfx.dominio.AutenticacionDM;
import nutricionalappfx.dominio.pojo.Autenticacion;
import nutricionalappfx.modelo.dao.AutenticacionDAO;
import nutricionalappfx.utilidades.Utilidades;

/**
 * FXML Controller class
 *
 * @author Gama
 */
public class FXMLAutenticacionController implements Initializable {

    @FXML
    private TextField tfNoPersonal;
    @FXML
    private TextField psPassword;
    @FXML
    private Label lbErrorNoPersonal;
    @FXML
    private Label lbErrorContrasenia;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    private boolean sonCamposValidos(){
         boolean esValido = true;
        lbErrorNoPersonal.setText("");
        lbErrorContrasenia.setText("");
        if(tfNoPersonal.getText().isEmpty()){
            lbErrorNoPersonal.setText("El numero de personal es obligatorio");
            esValido = false;
        }
        if(psPassword.getText().isEmpty()){
            lbErrorContrasenia.setText("La contraseña es obligatoria");
            esValido = false;
        }
        return esValido;
    }
    
    private void validarCredenciales(String noPersonal, String password){
        AutenticacionDM autenticacionDM = new AutenticacionDM(new AutenticacionDAO());
        try{
            Autenticacion respuesta = autenticacionDM.verificarCredencialesColaborador(noPersonal, password);
            if(respuesta.isCredencialesCorrectas() && respuesta.isActivo()){
                Utilidades.mostrarAlertaSimple("Credenciales Corectas", respuesta.getMensaje(), Alert.AlertType.INFORMATION);
                //TODO Enviar a la pantalla principal
            }else{
                Utilidades.mostrarAlertaSimple("Lo sentimos :( ", respuesta.getMensaje(), Alert.AlertType.WARNING);
            }
        }catch(RuntimeException e){
            Utilidades.mostrarAlertaSimple("Error ", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleBotonIngresar(ActionEvent event) {
        String noPersonal = tfNoPersonal.getText();
        String password = psPassword.getText();
        if(sonCamposValidos()){
            validarCredenciales(noPersonal, password);
        }
       
    }
    
}
