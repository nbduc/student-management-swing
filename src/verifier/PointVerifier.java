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
public class PointVerifier extends InputVerifier {
    @Override
    public boolean verify(JComponent input) {
        String text = ((JTextField)input).getText();
        try {
            float value = Float.parseFloat(text);
            if(value > 10 || value < 0){
                JOptionPane.showMessageDialog(null, "Điểm phải trong khoảng từ 0 đến 10.", "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Điểm phải là số.", "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
