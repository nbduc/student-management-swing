/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package verifier;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author duc
 */
public class NotEmptyVerifier extends InputVerifier{
    String textFieldName;
    public NotEmptyVerifier(String textFieldName){
        this.textFieldName = textFieldName;
    }
    @Override
    public boolean verify(JComponent input) {
        String text = ((JTextField)input).getText();
        if(text.length() == 0) {
            JOptionPane.showMessageDialog(null, textFieldName+" không được để trống!", "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
