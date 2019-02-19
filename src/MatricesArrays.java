
import java.text.DecimalFormat;
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
    String matriz[][]=new String[10][12],intsumafila;
    String [] vectorLetras={"A","B","C","D","E"};
    int vectorSumadep[]=new int [5];
    DecimalFormat formato=new DecimalFormat("#.00");
    public MatricesArrays() {
        initComponents();
        
    }
     
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        TablaDepartamento = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaPrincipal = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        TablaPrincipal.setFont(new java.awt.Font("Palatino Linotype", 0, 14)); // NOI18N
        TablaPrincipal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Código", "Nombres", "Salario Base", "Bonificación", "Comisión", "Descuento Judicial", "IGSS", "ISR", "Saldo Liquido", "Departamento", "saldo sin ISR", "Paga IGSS?"
            }
        ));
        jScrollPane1.setViewportView(TablaPrincipal);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 120, 1100, 190));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        random();
        asignacionLetras();
        iggs();
        ISRcalcular();
        for (int i=0;i<10;i++){
        
            //SALDO LIQUIDO
            matriz[i][8]=String.valueOf(formato.format(Integer.parseInt(matriz[i][2])+Integer.parseInt(matriz[i][3])+Integer.parseInt(matriz[i][4])-Integer.parseInt(matriz[i][5])-Float.parseFloat(matriz[i][6])-Float.parseFloat(matriz[i][7])));
     
        }
       
        
      
       this.MostrarMatriz(matriz,vectorLetras,vectorSumadep);
    
    }//GEN-LAST:event_jButton2ActionPerformed
 
public void random(){
         for (int i=0;i<10;i++){
            for(int j=2;j<6;j++){
              int intrandom=(int)(Math.random()*10000)+1000; //GENERANDO NÚMEROS RANDOM
                matriz[i][j]=String.valueOf(intrandom);   //LLENANDO MATRIZ CON NUMEROS RANDOM
                
            }}
    }
    
    public void iggs(){
        for(int i=0;i<10;i++){
               matriz[i][11]=JOptionPane.showInputDialog(null,"Paga IGSS?\n1.- SI\n2.- NO");
            if("1".equals(matriz[i][11]) ){
            matriz[i][6]=String.valueOf((Integer.parseInt(matriz[i][2])*4.83)/100);
        
            }else if("2".equals(matriz[i][11])){
            matriz[i][6]="0";
            }
            //CALCULO IGSS
        }
    }
    
    public void ISRcalcular(){
        for(int i=0;i<10;i++){
             //CALCULO SIN ISR....
            matriz[i][10]=String.valueOf(formato.format(Integer.parseInt(matriz[i][2])+Integer.parseInt(matriz[i][3])+Integer.parseInt(matriz[i][4])-Integer.parseInt(matriz[i][5])-Float.parseFloat(matriz[i][6])));
            //ASIGNACION DE CODIGO
            matriz[i][0]=String.valueOf(i+1);
            //CALCULO ISR
            if(Float.parseFloat(matriz[i][2])<5000){
               matriz[i][7]=String.valueOf((Float.parseFloat(matriz[i][2])*3)/100);
            }else if(Float.parseFloat(matriz[i][2])>5000&&Float.parseFloat(matriz[i][2])<10000){
                matriz[i][7]=String.valueOf((Float.parseFloat(matriz[i][2])*5)/100);
            }else if(Float.parseFloat(matriz[i][2])>10000){
                matriz[i][7]=String.valueOf((Float.parseFloat(matriz[i][2])*7)/100);
            }
            
        }
    }
public void asignacionLetras(){
    
        for(int i=0;i<10;i++){
             matriz[i][1]=JOptionPane.showInputDialog(null,"Nombre: ");
            int intRandomdep=(int)(Math.random()*5)+0;
            //ASIGNACION DE DEPARTAMENTO
            matriz[i][9]=vectorLetras[intRandomdep];
            
            if (matriz [i][9] == "A"){ 
            vectorSumadep[0]+= Integer.parseInt(matriz[i][4]);    
            }
            if (matriz [i][9] == "B"){
            vectorSumadep[1]+= Integer.parseInt(matriz[i][4]);
            }
            if (matriz [i][9] == "C"){
            vectorSumadep[2]+= Integer.parseInt(matriz[i][4]);    
            }
            if (matriz [i][9] == "D"){
            vectorSumadep[3]+= Integer.parseInt(matriz[i][4]); 
            }
            if (matriz [i][9] == "E"){
            vectorSumadep[4]+=Integer.parseInt(matriz[i][4]);    
            }
        }
}

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        for(int i=0;i<10;i++){
            for(int j=0;j<12;j++){
                matriz[i][j]="";
            }
        }
        for (int i=0;i<5;i++){
            vectorSumadep[i]=0;
        }
        this.MostrarMatriz(matriz, vectorLetras, vectorSumadep);
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
            for(int j=0;j<12;j++){
                TablaPrincipal.setValueAt(matriz[i][j], i, j); //Asignando valores de la matriz al JTable
            }
        }
        
        DefaultTableModel dftVectormodel=(DefaultTableModel) TablaDepartamento.getModel(); //Obtener el model de JTable
        for (int i=0;i<5;i++){
            for(int j=0;j<2;j++){
            TablaDepartamento.setValueAt(vecSuma[i],i,0);    
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
