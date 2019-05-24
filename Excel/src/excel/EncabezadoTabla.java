/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excel;
 
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
 
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

/**
 *
 * Clase hecho por Yavhé Sergio Enrique Orozco Valle
 *          0901-17-6739
 */ 
public class EncabezadoTabla implements TableCellRenderer {
     @Override //Le decimos que iremos a sobreesciribir
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
         //Activamos el jcomponent
        JComponent jcomponent = null;
         
        if( value instanceof String ) {
            jcomponent = new JLabel((String) value);
            ((JLabel)jcomponent).setHorizontalAlignment( SwingConstants.CENTER );
            ((JLabel)jcomponent).setSize( 40, jcomponent.getWidth() );   //Tamaño de ancho
            ((JLabel)jcomponent).setPreferredSize( new Dimension(6, jcomponent.getWidth())  );
        }         
        //Asingacion del color al borde
        //jcomponent.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(221, 211, 211)));
        jcomponent.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(255, 255, 255)));
        jcomponent.setOpaque(true);
        jcomponent.setBackground( new Color(94,100,114) ); //Asignacion de color a las columnas
        //jcomponent.setBackground( new Color(65,65,65) );
        jcomponent.setToolTipText("Tabla Seguimiento");
        jcomponent.setForeground(Color.white);
         
        return jcomponent;//Retorna el componente
    }
}
