
import java.util.Arrays;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *Planilla para calcular saldo liquido de 10 trabajadores usando valores random
 * @author Sergio Orozco Valle
 * 0901-17-6739
 */
public class MatricesArrays extends javax.swing.JFrame {

    /**
     * Creates new form MatricesArrays
     */
    String[][] strmatriz=new String[10][6],intsumafila;
    String [] strvectorLetras={"A","B","C","D","E"};
    int[] intvectorSumadep=new int [5];
    public MatricesArrays() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        TablaPrincipal = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaDepartamento = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TablaPrincipal.setFont(new java.awt.Font("Palatino Linotype", 0, 14)); // NOI18N
        TablaPrincipal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Nombres", "Salario Base", "Total Deducciones", "Total Percepciones", "Saldo Liquido", "Departamento"
            }
        ));
        jScrollPane1.setViewportView(TablaPrincipal);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 140, 690, 190));

        TablaDepartamento.setFont(new java.awt.Font("Palatino Linotype", 0, 14)); // NOI18N
        TablaDepartamento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Departamentos"
            }
        ));
        jScrollPane2.setViewportView(TablaDepartamento);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 160, 130, 130));

        jButton1.setFont(new java.awt.Font("Palatino Linotype", 0, 14)); // NOI18N
        jButton1.setText("Usar de nuevo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 400, -1, -1));

        jButton2.setFont(new java.awt.Font("Palatino Linotype", 0, 14)); // NOI18N
        jButton2.setText("Generar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 400, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    
        for (int i=0;i<10;i++){
            for(int j=1;j<4;j++){
              int intrandom=(int)(Math.random()*5000)+1000; //GENERANDO NÚMEROS RANDOM
                strmatriz[i][j]=String.valueOf(intrandom);   //LLENANDO MATRIZ CON NUMEROS RANDOM
                
            }
            //Operaciones aritmeticas con las 3 columnas de la matriz en posiciones [i][1-3]
            strmatriz[i][4]=String.valueOf(Integer.parseInt(strmatriz[i][1])-Integer.parseInt(strmatriz[i][2])+Integer.parseInt(strmatriz[i][3]));
            strmatriz[i][0]=JOptionPane.showInputDialog(null,"Nombre: ");
            int intRandomdep=(int)(Math.random()*5)+0;
            System.out.print(intRandomdep+" ");
            strmatriz[i][5]=strvectorLetras[intRandomdep];
            
            
            
        }
        //Verificando la letra y adicionandolo a el vector de suma
        for(int i=0;i<10;i++){
            if (strmatriz [i][5] == "A"){
            intvectorSumadep[0]+= Integer.parseInt(strmatriz[i][4]);    
            }
            if (strmatriz [i][5] == "B"){
            intvectorSumadep[1]+= Integer.parseInt(strmatriz[i][4]);
            }
            if (strmatriz [i][5] == "C"){
            intvectorSumadep[2]+= Integer.parseInt(strmatriz[i][4]);    
            }
            if (strmatriz [i][5] == "D"){
            intvectorSumadep[3]+= Integer.parseInt(strmatriz[i][4]); 
            }
            if (strmatriz [i][5] == "E"){
            intvectorSumadep[4]+=Integer.parseInt(strmatriz[i][4]);    
            }
        }
        
      //mandar datos a metodo para mostrarlos en la tabla 
       this.MostrarMatriz(strmatriz,strvectorLetras,intvectorSumadep);
    
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        for(int i=0;i<10;i++){
            for(int j=0;j<6;j++){
                strmatriz[i][j]="";
            }
        }
        for (int i=0;i<5;i++){
            intvectorSumadep[i]=0;
        }
        this.MostrarMatriz(strmatriz, strvectorLetras, intvectorSumadep);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MatricesArrays.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MatricesArrays.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MatricesArrays.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MatricesArrays.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MatricesArrays().setVisible(true);
            }
        });
    }
    
    
    /*Metodo creado para mostrar la matriz en el Jtable llamado TablaPrincipal y mostrar
        el vector el el JTable TablaDepartamento*/
    public void MostrarMatriz(String matrix[][],String vector[],int vecSuma[]){
        DefaultTableModel model = (DefaultTableModel) TablaPrincipal.getModel(); //Obteniendo modelo de JTable
        for(int i=0;i<10;i++){
            for(int j=0;j<6;j++){
                TablaPrincipal.setValueAt(strmatriz[i][j], i, j); //Asignando valores de la strmatriz al JTable
            }
        }
        
        DefaultTableModel dftVectormodel=(DefaultTableModel) TablaDepartamento.getModel(); //Obtener el model de JTable
        for (int i=0;i<5;i++){
            for(int j=0;j<2;j++){
            TablaDepartamento.setValueAt(vecSuma[i],i,0);  //Asignando valores al jtable  
            }
         }
    }
    
               
           
    
            
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TablaDepartamento;
    private javax.swing.JTable TablaPrincipal;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
