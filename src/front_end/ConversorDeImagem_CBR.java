package front_end;

import back_end.Back;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Miguel
 */
public class ConversorDeImagem_CBR extends Application {
    
    private Stage window;
    private Back backEnd;
    public static boolean returnErro = false;
    
    @Override
    public void start(Stage primaryStage) {
       this.window = primaryStage;
       this.window.setTitle("Conversor de Imagens para .CBR");
       this.window.setResizable(false);
       
       Label label = new Label("Diretório padrão:");
       TextField textField = new TextField("/Users/Miguel/Desktop/Berserk/Baixados agora em .cbr/Berserk");
       Button button = new Button("Novo diretório padrão");
       Button button2 = new Button("Converter Imagens em .cbr");
       
       StackPane layout = new StackPane();
       layout.getChildren().addAll(label, textField, button, button2);
       
       StackPane.setAlignment(label, Pos.TOP_LEFT);
       StackPane.setMargin(textField, new Insets(-130, 10, 10, 10));
       StackPane.setMargin(button, new Insets(-70, 168, 10, 10));
       StackPane.setAlignment(button2, Pos.BOTTOM_RIGHT);
       
       this.backEnd = new Back();
       button.setOnAction(e -> {
           if (textField.getText() != null && !"".equals(textField.getText())) {
               this.backEnd.setDirectory(textField.getText());
           }
       });
       
       button2.setOnAction(e -> this.backEnd.convertImagesInCBR()); 
       
       Scene scene = new Scene(layout, 300, 200);
       
       this.window.setScene(scene);
       this.window.show();
    }

    public static void callJOptionPaneErrorMensage() {
        ConversorDeImagem_CBR.returnErro = true;
        JOptionPane.showMessageDialog(null, "Algo deu errado...", "Mensagem de Erro", JOptionPane.ERROR_MESSAGE);
    }
    
    public static void callJOptionPaneConfirmMensage() {
        JOptionPane.showMessageDialog(null, "Convertido com sucesso!", "Mensagem de Confirmação", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
