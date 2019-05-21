/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excel;
import java.awt.Font;
import java.sql.*;
import java.awt.event.KeyEvent;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.CellEditor;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Sergio Orozco
 */
public class Vista extends javax.swing.JFrame {
     public static int intCod=0;
    public static int intFila, intColumna;
    public static Lista miLista=new Lista();
    public static String datos;
    DefaultTableModel tm;
    String sCopiado,sTipoLetra;
    String simbolo;
     String vctAbc[]=new String[27];//vector para el llenado de la busqueda de celda
     int itOp=0, itNegr=0, itCursiva=0,itSubrayado=0;//variable pivote de ayuda
     int iTamañoLetra=12;
      int x=0,y=0;        //variables para obtener las cooredenadas de seleccion en la tabla
    /**
     * Creates new form Vista
     */
    public Vista() {
        try{
            //Conección con la base de datos
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/excel", "root", "");
            PreparedStatement pst = cn.prepareStatement("select * from tblarchivo");
            ResultSet rs = pst.executeQuery();
            boolean r=rs.next();
            //mientras encuentre datos en la tabla y campo especificado
            while(r){
                //intCod servirá para código automático por lo que se busca el más grande guardado en la base de datos
                if (intCod< Integer.parseInt(rs.getString("codarch"))) {
                    intCod=Integer.parseInt(rs.getString("codarch"));
                }
                r=rs.next();
            }
            intCod++;
            System.out.println(intCod);
        }catch (Exception e){
            System.out.println("No conecta");
        }
        initComponents();
        //oculta algunos obtjetos
        this.cmbcodigo.setVisible(false);
        this.lblsel.setVisible(false);
        this.cmblistado.setVisible(false);
        btnabrireli.setVisible(false); 
        //limpia algunos combobox
        this.cmbcol.removeAllItems();
        this.cmbfil.removeAllItems();
        //llamado de metodos para llenado de combobox
        llenadocmbArchivos();
        cmbllenado();      
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()  ){
                if("Windows".equals(info.getName())){
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            ex.getMessage();
        }
     
        /*miLista.insertarPrincipio(new Celda("10", 3, 3));
        JOptionPane.showMessageDialog(this, miLista.Listar());
        miLista.modifyPorFilaColumna(3,3,"hi");
        JOptionPane.showMessageDialog(this, miLista.Listar());*/
    }
    public void negrita(){
        if(itNegr==0){
            tblexcel.setFont(new java.awt.Font(sTipoLetra,Font.BOLD, iTamañoLetra));
            itNegr=1;
        }else{
            itNegr=0;
        tblexcel.setFont(new java.awt.Font(sTipoLetra,0, iTamañoLetra));
        }
    }
        public void cursiva(){
        if(itCursiva==0){
            tblexcel.setFont(new java.awt.Font(sTipoLetra,Font.ITALIC, iTamañoLetra));
            itCursiva=1;
        }else{
            itCursiva=0;
        tblexcel.setFont(new java.awt.Font(sTipoLetra,0, iTamañoLetra));
        }
    }
        public void subrayado(){
           if(itSubrayado==0){           
    Font font = tblexcel.getFont();
    Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
    tblexcel.setFont(font.deriveFont(attributes));
    itSubrayado=1;
        }else{
            Font font = tblexcel.getFont();
    Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
    attributes.put(TextAttribute.UNDERLINE, -1);
    tblexcel.setFont(font.deriveFont(attributes));
    itSubrayado=0;
            }
        }
    public void llenadocmbArchivos(){
     this.cmblistado.removeAllItems();
      this.cmbcodigo.removeAllItems();
        try{
            //conexion de datos
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/excel", "root", "");
            PreparedStatement pst = cn.prepareStatement("SELECT * FROM tblarchivo");
            ResultSet rs = pst.executeQuery();
            boolean r=rs.next();
            while(r){
                this.cmbcodigo.addItem(rs.getString("codarch"));
                this.cmblistado.addItem(rs.getString("nombre"));
                r=rs.next();            
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Base de datos no enlazada");
        }
    }
   public void cmbllenado(){
      for(int i=0; i<=20;i++){
      cmbfil.addItem(String.valueOf(i));
      }
      vctAbc[0]="A";vctAbc[1]="B";vctAbc[2]="C";vctAbc[3]="D";vctAbc[4]="E";vctAbc[5]="F";vctAbc[6]="G";vctAbc[7]="H";vctAbc[8]="I";vctAbc[9]="J";vctAbc[10]="K";vctAbc[11]="L";vctAbc[12]="M";
      vctAbc[13]="N";vctAbc[14]="O";vctAbc[15]="P";vctAbc[16]="Q";vctAbc[17]="R";vctAbc[18]="S";vctAbc[19]="T";vctAbc[20]="U";vctAbc[21]="V";vctAbc[22]="W";vctAbc[23]="X";vctAbc[24]="Y";vctAbc[25]="Z";      
       for(int i=0; i<=25;i++){
        cmbcol.addItem(String.valueOf(vctAbc[i]));
      }
        for(int i=0; i<=15;i++){
        cmbcol.addItem(String.valueOf("A"+vctAbc[i]));
      }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblexcel = new javax.swing.JTable();
        txtBarra = new javax.swing.JTextField();
        cmbfil = new javax.swing.JComboBox<>();
        cmbcol = new javax.swing.JComboBox<>();
        cmblistado = new javax.swing.JComboBox<>();
        cmbcodigo = new javax.swing.JComboBox<>();
        btnabrireli = new javax.swing.JButton();
        lblsel = new javax.swing.JLabel();
        numer = new javax.swing.JTextField();
        denom = new javax.swing.JTextField();
        calcular = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem26 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem25 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem21 = new javax.swing.JMenuItem();
        jMenuItem22 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem24 = new javax.swing.JMenuItem();
        jMenuItem23 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 690, -1, -1));

        tblexcel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA", "AB", "AC", "AD", "AE", "AF", "AG", "AH", "AI", "AJ", "AK"
            }
        ));
        tblexcel.setAutoscrolls(false);
        tblexcel.setColumnSelectionAllowed(true);
        tblexcel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblexcelMouseClicked(evt);
            }
        });
        tblexcel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblexcelKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblexcelKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tblexcelKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(tblexcel);
        tblexcel.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 1228, 550));

        txtBarra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBarraKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBarraKeyReleased(evt);
            }
        });
        getContentPane().add(txtBarra, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 1090, -1));

        cmbfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbfil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                cmbfilMouseReleased(evt);
            }
        });
        cmbfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbfilActionPerformed(evt);
            }
        });
        cmbfil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cmbfilKeyReleased(evt);
            }
        });
        getContentPane().add(cmbfil, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 80, -1));

        cmbcol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbcol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbcolActionPerformed(evt);
            }
        });
        getContentPane().add(cmbcol, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 80, -1));

        cmblistado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmblistado.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmblistadoItemStateChanged(evt);
            }
        });
        getContentPane().add(cmblistado, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 60, 170, -1));

        cmbcodigo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(cmbcodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 50, 170, -1));

        btnabrireli.setText("Eliminar");
        btnabrireli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnabrireliActionPerformed(evt);
            }
        });
        getContentPane().add(btnabrireli, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 60, -1, -1));

        lblsel.setText("Seleccione");
        getContentPane().add(lblsel, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 60, -1, -1));
        getContentPane().add(numer, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 70, 50, -1));
        getContentPane().add(denom, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 100, 50, -1));

        calcular.setText("Calcular");
        calcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcularActionPerformed(evt);
            }
        });
        getContentPane().add(calcular, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 80, -1, -1));

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/file.png"))); // NOI18N
        jMenu1.setText("Archivo");

        jMenuItem26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/add-file.png"))); // NOI18N
        jMenuItem26.setText("Nuevo");
        jMenu1.add(jMenuItem26);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/folder1.png"))); // NOI18N
        jMenuItem3.setText("Abrir");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/save.png"))); // NOI18N
        jMenuItem2.setText("Guardar");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/archivo.png"))); // NOI18N
        jMenuItem25.setText("Eliminar");
        jMenuItem25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem25ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem25);

        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logout.png"))); // NOI18N
        jMenuItem4.setText("Cerrar");
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/home.png"))); // NOI18N
        jMenu2.setText("Inicio");

        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/letra.png"))); // NOI18N
        jMenu5.setText("Letra");

        jMenuItem16.setText("Calibri");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem16);

        jMenuItem14.setText("Palatino Linotype");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem14);

        jMenuItem17.setText("Arial");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem17);

        jMenuItem18.setText("Century");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem18);

        jMenuItem15.setText("Comic Sans Ms");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem15);

        jMenu2.add(jMenu5);

        jMenu7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/align2.png"))); // NOI18N
        jMenu7.setText("Alineado");

        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/left.png"))); // NOI18N
        jMenuItem5.setText("Izquierda");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem5);

        jMenuItem21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/center.png"))); // NOI18N
        jMenuItem21.setText("Centro");
        jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem21ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem21);

        jMenuItem22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/right.png"))); // NOI18N
        jMenuItem22.setText("Derecha");
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem22);

        jMenu2.add(jMenu7);

        jMenu6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/size.png"))); // NOI18N
        jMenu6.setText("Tamaño de Letra");

        jMenuItem1.setText("12");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem1);

        jMenuItem19.setText("15");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem19);

        jMenuItem20.setText("20");
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem20);

        jMenu2.add(jMenu6);

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_K, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/italix.png"))); // NOI18N
        jMenuItem6.setText("K");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem6);

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/bold.png"))); // NOI18N
        jMenuItem7.setText("N");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem7);

        jMenuItem8.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/underline.png"))); // NOI18N
        jMenuItem8.setText("S");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem8);

        jMenuItem24.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cpy.png"))); // NOI18N
        jMenuItem24.setText("Pegar");
        jMenuItem24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem24ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem24);

        jMenuItem23.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pst.png"))); // NOI18N
        jMenuItem23.setText("Copiar");
        jMenuItem23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem23ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem23);

        jMenuBar1.add(jMenu2);

        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/numero.png"))); // NOI18N
        jMenu4.setText("Número");

        jMenuItem10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fraccion.png"))); // NOI18N
        jMenuItem10.setText("Fracción 1/2");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem10);

        jMenuItem11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/moneda.png"))); // NOI18N
        jMenuItem11.setText("Moneda  Q.");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem11);

        jMenuItem12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/general.png"))); // NOI18N
        jMenuItem12.setText("General");
        jMenu4.add(jMenuItem12);

        jMenuItem13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/porcentaje.png"))); // NOI18N
        jMenuItem13.setText("Porcentaje %");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem13);

        jMenuBar1.add(jMenu4);

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ayuda.png"))); // NOI18N
        jMenu3.setText("Ayuda");

        jMenuItem9.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F12, 0));
        jMenuItem9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/libro.png"))); // NOI18N
        jMenuItem9.setText("Manual de Usuario");
        jMenu3.add(jMenuItem9);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

 
public void Porcentaje(){
    
       String infodatos=String.valueOf(tm.getValueAt(tblexcel.getSelectedRow(),tblexcel.getSelectedColumn()));     
       intColumna=tblexcel.getSelectedColumn();
       intFila=tblexcel.getSelectedRow();
    
            if(infodatos.charAt(0)=='Q'){
         generalm();
         infodatos=String.valueOf(tm.getValueAt(tblexcel.getSelectedRow(),tblexcel.getSelectedColumn()));     
         float Intporcen=Float.parseFloat(infodatos);
         float intvalormostrar=Intporcen*100;
          tblexcel.setValueAt(intvalormostrar, intFila, intColumna);
       
       }else if(infodatos.matches("\\d+\\/\\d+")){
           generalm();
            infodatos=String.valueOf(tm.getValueAt(tblexcel.getSelectedRow(),tblexcel.getSelectedColumn()));     
         float Intporcen=Float.parseFloat(infodatos);
         float intvalormostrar=Intporcen*100;
         tblexcel.setValueAt(intvalormostrar, intFila, intColumna);
       }else{
            infodatos=String.valueOf(tm.getValueAt(tblexcel.getSelectedRow(),tblexcel.getSelectedColumn()));     
         float Intporcen=Float.parseFloat(infodatos);
         float intvalormostrar=Intporcen*100;
         tblexcel.setValueAt(intvalormostrar, intFila, intColumna);
         
       } 
    
}
public void moneda(){
        //Agrega a cada valor simbolo de moneda
       String infodatos=String.valueOf(tm.getValueAt(tblexcel.getSelectedRow(),tblexcel.getSelectedColumn()));     
       intColumna=tblexcel.getSelectedColumn();
       intFila=tblexcel.getSelectedRow();
       
       String regex="\\d+\\.\\d+";
       /*Pattern pattern = Pattern.compile(regex);
       Matcher matcher=pattern.matcher(infodatos);
       System.out.println(matcher.find());*/
                
       if(infodatos.matches("\\d+")){
           tblexcel.setValueAt("Q"+infodatos+".00", intFila, intColumna);
       }
       
       if(infodatos.matches(regex)){
           
           infodatos=String.valueOf(tm.getValueAt(tblexcel.getSelectedRow(),tblexcel.getSelectedColumn()));     
           simbolo="Q"+infodatos;
           tblexcel.setValueAt(simbolo, intFila, intColumna);
       }    
       
       //CONVIRTIENDO FRACCION A MONEDA------------------------------------------------------------------------
          if(infodatos.matches("\\d+\\/\\d+")){
           
          int o=0;
        while(infodatos.charAt(o)!='/'){
            o++;
            //Validacion por si no tuviera punto decimal y no se quede en bucle
            if(o==infodatos.length()){
                break;
            }
        } 
        
        float numer=Integer.parseInt(infodatos.substring(0, o));
        float denom=Integer.parseInt(infodatos.substring(o+1, infodatos.length()));
        
        float total=0;
        total=numer/denom;
                
        String nuevodato=Float.toString(total);
        
                
        
           System.out.println("num: "+numer+" denom: "+denom+" total: "+total);
        tblexcel.setValueAt(total, intFila, intColumna);
        
       }
          
    }



public void fractoria(){
        //Agrega a cada valor simbolo de moneda
        String infodatos=String.valueOf(tm.getValueAt(tblexcel.getSelectedRow(),tblexcel.getSelectedColumn()));     
       intColumna=tblexcel.getSelectedColumn();
       intFila=tblexcel.getSelectedRow();
           
       
                 
       if(infodatos.charAt(0)=='Q'){
          
           int p=0;
        while(infodatos.charAt(p)!='Q'){
            p++;
            //Validacion por si no tuviera punto decimal y no se quede en bucle
            if(p==infodatos.length()){
                break;
            }
        } 
        
        //envio de parametro
        double decimal=Double.parseDouble(infodatos.substring(p+1, infodatos.length()));
        
       ControllerGeneral control=new ControllerGeneral(decimal);
       JOptionPane.showMessageDialog(null, control.toFraccion(decimal));
       tblexcel.setValueAt(control.toFraccion(decimal), intFila, intColumna);
       
        String infodatos2=String.valueOf(tm.getValueAt(tblexcel.getSelectedRow(),tblexcel.getSelectedColumn()));
           
           
           
       }else{
           
            double decimal=Double.parseDouble(infodatos);
        
       ControllerGeneral control=new ControllerGeneral(decimal);
       JOptionPane.showMessageDialog(null, control.toFraccion(decimal));
       tblexcel.setValueAt(control.toFraccion(decimal), intFila, intColumna);
       
        String infodatos2=String.valueOf(tm.getValueAt(tblexcel.getSelectedRow(),tblexcel.getSelectedColumn()));
           
       }   
       
       /*int i=0;
        while(infodatos2.charAt(i)!='/'){
            i++;
            //Validacion por si no tuviera punto decimal y no se quede en bucle
            if(i==infodatos2.length()){
                break;
            }
        }*/
       
        /*fraccion frac = new fraccion(Integer.parseInt(numer.getText()),Integer.parseInt(denom.getText()));
        JOptionPane.showMessageDialog(null, frac.toString());*/
      /////////////////////////////////////////////
    }
    
public void generalm(){
//Guardando un valor de la celda en variable string
     String infodatos=String.valueOf(tm.getValueAt(tblexcel.getSelectedRow(),tblexcel.getSelectedColumn()));     
       intColumna=tblexcel.getSelectedColumn();
       intFila=tblexcel.getSelectedRow();
       
          //Recorrido del String
       if(infodatos.charAt(0)=='Q'){
           
                 int p=0;
        while(infodatos.charAt(p)!='Q'){
            p++;
            //Validacion por si no tuviera punto decimal y no se quede en bucle
            if(p==infodatos.length()){
                break;
            }
        } 
        
        //envio de parametro
        
        double normal=Double.parseDouble(infodatos.substring(p+1, infodatos.length()));
        
       tblexcel.setValueAt(normal, intFila, intColumna);
       
       }else{
          int o=0;
        while(infodatos.charAt(o)!='/'){
            o++;
            //Validacion por si no tuviera punto decimal y no se quede en bucle
            if(o==infodatos.length()){
                break;
            }
        } 
        
        float numer=Integer.parseInt(infodatos.substring(0, o));
        float denom=Integer.parseInt(infodatos.substring(o+1, infodatos.length()));
        
        float total=0;
        total=numer/denom;
        float total2=5/2;
           System.out.println("num: "+numer+" denom: "+denom+" total: "+total+" total2: "+total2);
        tblexcel.setValueAt(total, intFila, intColumna);
        
       }
       
          
        
       
}


    
    public void AlinearDerecha(){
   //alinea la celda a la derecha
     DefaultTableCellRenderer modelocentrar = new DefaultTableCellRenderer();
      modelocentrar.setHorizontalAlignment(SwingConstants.RIGHT);
      tblexcel.getColumnModel().getColumn(intColumna).setCellRenderer(modelocentrar);       

}
public void AlinearIzquierda(){
   //alinea la celda a la izquierda
     DefaultTableCellRenderer modelocentrar = new DefaultTableCellRenderer();
      modelocentrar.setHorizontalAlignment(SwingConstants.LEFT);
      tblexcel.getColumnModel().getColumn(intColumna).setCellRenderer(modelocentrar);       
    
}
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     tblexcel.setValueAt("asdad", 1, 1);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
        fractoria();
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // TODO add your handling code here:.
        moneda();
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
         sTipoLetra="Palatino Linotype";
        tblexcel.setFont(new java.awt.Font(sTipoLetra,0, iTamañoLetra));      
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
 
       iTamañoLetra=15;
       tblexcel.setFont(new java.awt.Font(sTipoLetra,0, iTamañoLetra));  
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem22ActionPerformed
      //alinea la celda a la izquierda
     DefaultTableCellRenderer modelocentrar = new DefaultTableCellRenderer();
      modelocentrar.setHorizontalAlignment(SwingConstants.RIGHT);
      tblexcel.getColumnModel().getColumn(intColumna).setCellRenderer(modelocentrar);       
    }//GEN-LAST:event_jMenuItem22ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
      //alinea la celda a la izquierda
     DefaultTableCellRenderer modelocentrar = new DefaultTableCellRenderer();
      modelocentrar.setHorizontalAlignment(SwingConstants.LEFT);
      tblexcel.getColumnModel().getColumn(intColumna).setCellRenderer(modelocentrar);       
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem21ActionPerformed
     //alinea la celda a la izquierda
     DefaultTableCellRenderer modelocentrar = new DefaultTableCellRenderer();
      modelocentrar.setHorizontalAlignment(SwingConstants.CENTER);
      tblexcel.getColumnModel().getColumn(intColumna).setCellRenderer(modelocentrar);       
    }//GEN-LAST:event_jMenuItem21ActionPerformed

    private void txtBarraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBarraKeyReleased
      tblexcel.setValueAt(this.txtBarra.getText(), intFila, intColumna);  
      VerificarVacio();            
      Lista();
      System.out.println(miLista.Listar());      
    }//GEN-LAST:event_txtBarraKeyReleased

    private void jMenuItem23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem23ActionPerformed
         if (tm.getValueAt(intFila, intColumna)==null) {
            sCopiado="";
        }else{
            sCopiado=String.valueOf(this.tm.getValueAt(intFila,intColumna)); 
        }  
    }//GEN-LAST:event_jMenuItem23ActionPerformed

    private void jMenuItem24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem24ActionPerformed
      
        System.out.println(sCopiado+"-"+intFila+"-"+intColumna);            
        tblexcel.setValueAt(sCopiado, intFila, intColumna);
        tblexcel.requestFocus();          
        tblexcel.editCellAt(intFila,intColumna);       
        VerificarVacio();   
        this.txtBarra.setText(datos); 
        
        Lista();
        System.out.println(miLista.Listar());     
    }//GEN-LAST:event_jMenuItem24ActionPerformed

    private void cmbfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbfilActionPerformed
      intColumna=cmbcol.getSelectedIndex();
    intFila=cmbfil.getSelectedIndex();
    
        if (tblexcel.isEditing()) {
            tblexcel.editCellAt(intFila,intColumna);
            tblexcel.changeSelection(intFila,intColumna,false, false);       
            tblexcel.requestFocus();     
        }else{
             tblexcel.changeSelection(intFila,intColumna,false, false);       
             tblexcel.requestFocus(); 
        }   
    }//GEN-LAST:event_cmbfilActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
      //muestra un combobox y un boton para que pueda seleccionar el archivo que desea abrir        
       itOp=1;
       btnabrireli.setText("Abrir");
       btnabrireli.setVisible(true);
       this.lblsel.setVisible(true);
       cmblistado.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void cmblistadoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmblistadoItemStateChanged
        try{ cmbcodigo.setSelectedIndex(cmblistado.getSelectedIndex());}// se cambia el indice del combo
        catch(Exception e){
        }
    }//GEN-LAST:event_cmblistadoItemStateChanged

    private void btnabrireliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnabrireliActionPerformed
        //dependiendo si eleigio eliminar o abrir, eso esta en la variabel itOp,
        //entonces entrara en el if y luego de realizar la accion vuelve a cerrar el combobox y el boton
        if(itOp==1){            
        try{//obtencion de datos
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/excel", "root", "");
            PreparedStatement pst = cn.prepareStatement("SELECT * FROM `tblcontenido` WHERE codarch="+cmbcodigo.getSelectedItem()+";");
            ResultSet rs = pst.executeQuery();
            boolean r=rs.next();
            while(r){       
               tblexcel.setValueAt(rs.getString("contenido"), Integer.parseInt(rs.getString("fila")), Integer.parseInt(rs.getString("colum")));
               miLista.insertarDato(new Celda(rs.getString("contenido"), Integer.parseInt(rs.getString("fila")), Integer.parseInt(rs.getString("colum"))));
                r=rs.next();
                x++;               
            }                   
            System.out.println(miLista.Listar());  
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"le dio un Error fatal "+e);
        }
        try{//obtencion de datos
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/excel", "root", "");
            PreparedStatement pst = cn.prepareStatement("SELECT * FROM `tblarchivo` WHERE codarch="+cmbcodigo.getSelectedItem()+";");
            ResultSet rs = pst.executeQuery();
            boolean r=rs.next();
            while(r){                    
               itNegr=Integer.parseInt(rs.getString("negrita"));
               itCursiva=Integer.parseInt(rs.getString("cursiva"));
               itSubrayado=Integer.parseInt(rs.getString("subrayada"));
                r=rs.next();
                x++;               
            }
            if(itNegr==1){
            itNegr=0;
            negrita();
            }            
             if(itCursiva==1){
            itCursiva=0;
            cursiva();
            } 
            if(itSubrayado==1){
            itSubrayado=0;
            cursiva();
            } 
        
            System.out.println(miLista.Listar());  
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"le dio un Error fatal "+e);
        }
        }
        if(itOp==2){
     int itres, iterror=0;
     itres=  JOptionPane.showConfirmDialog(this,"¿Esta seguro de eliminar los datos?","Eliminar",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
     if(itres==JOptionPane.YES_OPTION){
               try{
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/excel", "root", "");
            PreparedStatement pst = cn.prepareStatement("DELETE FROM `tblcontenido` WHERE codarch="+cmbcodigo.getSelectedItem()+";");
            pst.executeUpdate(); 
            iterror=1;
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"le dio un error "+e);
     }
         try{
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/excel", "root", "");
            PreparedStatement pst = cn.prepareStatement("DELETE FROM `tblarchivo` WHERE codarch="+cmbcodigo.getSelectedItem()+";");
            pst.executeUpdate();  
            iterror=1+iterror;
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"le dio un error "+e);
        }
         if(iterror==2){                  
         JOptionPane.showMessageDialog(null,"Dato Eliminado con exito");
         }
         llenadocmbArchivos();
     }     
        }  
        cmblistado.setVisible(false);
        btnabrireli.setVisible(false);
        this.lblsel.setVisible(false);
    }//GEN-LAST:event_btnabrireliActionPerformed

    private void jMenuItem25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem25ActionPerformed
    //muestra un combobox y un boton para que pueda seleccionar el archivo que desea eliminar       
       itOp=2;
       btnabrireli.setText("Eliminar");
       btnabrireli.setVisible(true);
       cmblistado.setVisible(true);
       this.lblsel.setVisible(true);
    }//GEN-LAST:event_jMenuItem25ActionPerformed

    private void calcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcularActionPerformed
        // TODO add your handling code here:
        fraccion frac = new fraccion(Integer.parseInt(numer.getText()),Integer.parseInt(denom.getText()));
        JOptionPane.showMessageDialog(null, frac.toString());
        
    }//GEN-LAST:event_calcularActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        String strNombreAr=JOptionPane.showInputDialog(null,"Ingrese el nombre del archivo con que desea guardar","Nombre Archivo",JOptionPane.QUESTION_MESSAGE);
        try{
            JOptionPane.showMessageDialog(this, intCod+"  "+itNegr+"  "+ itCursiva+" "+itSubrayado,"ÉXITO",JOptionPane.INFORMATION_MESSAGE);
            //Conección con la base de datos
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/excel", "root", "");
            PreparedStatement pst = cn.prepareStatement("INSERT INTO `tblarchivo`(`codarch`, `nombre`,`negrita`, `cursiva`, `subrayada`) VALUES (?,?,?,?,?)");
            pst.setString(1, String.valueOf(intCod));
            pst.setString(2, strNombreAr.trim());
            pst.setString(3, String.valueOf(itNegr));
            pst.setString(4, String.valueOf(itCursiva));
            pst.setString(5, String.valueOf(itSubrayado));
            pst.executeUpdate();
            //se agregan los datos ingresados a la base de datos 
            JOptionPane.showMessageDialog(this, "Datos ingresados correctamente","ÉXITO",JOptionPane.INFORMATION_MESSAGE);
        }catch (Exception e){
            System.out.println("le dio un error");
        }        
        for (int i = 0; i < miLista.contar(); i++) {
            String strCont=miLista.obtenerNodo(i);
            String[] strPartes=strCont.split(";");
            int fila=Integer.parseInt(strPartes[1]);
            int col=Integer.parseInt(strPartes[2]);
            String cont=strPartes[0];
            System.out.println("contenido "+cont+" fila "+fila+" columna "+col);
            try{
                //Conección con la base de datos
                Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/excel", "root", "");
                PreparedStatement pst = cn.prepareStatement("INSERT INTO `tblcontenido`(`fila`, `colum`, `codarch`, `contenido`) VALUES (?,?,?,?)");
                pst.setString(1, String.valueOf(fila));
                pst.setString(2, String.valueOf(col));
                pst.setString(3, String.valueOf(intCod));
                pst.setString(4, String.valueOf(cont));               
                pst.executeUpdate();
                //se agregan los datos ingresados a la base de datos 
                JOptionPane.showMessageDialog(this, "Datos ingresados correctamente","ÉXITO",JOptionPane.INFORMATION_MESSAGE);
            }catch (Exception e){
                System.out.println("le dio un error "+e);
            }
            //JOptionPane.showMessageDialog(null, miLista.obtenerNodo(i));
        }
        llenadocmbArchivos();
        intCod++;
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void cmbcolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbcolActionPerformed
 
    }//GEN-LAST:event_cmbcolActionPerformed

    private void cmbfilKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbfilKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbfilKeyReleased

    private void cmbfilMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbfilMouseReleased
         DetenerEditarCelda();
    }//GEN-LAST:event_cmbfilMouseReleased

    private void txtBarraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBarraKeyPressed
       if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                 intFila=(tblexcel.getSelectedRow())+1;
                 tblexcel.requestFocus();
                 System.out.println(intFila+" -"+intColumna+"-");
      }
    }//GEN-LAST:event_txtBarraKeyPressed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        sTipoLetra="Calibri";
        tblexcel.setFont(new java.awt.Font(sTipoLetra,0, iTamañoLetra)); 
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
           sTipoLetra="Arial";
        tblexcel.setFont(new java.awt.Font(sTipoLetra,0, iTamañoLetra)); 
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
       sTipoLetra="Century";
        tblexcel.setFont(new java.awt.Font(sTipoLetra,0, iTamañoLetra)); 
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        sTipoLetra="Comic Sans Ms";
        tblexcel.setFont(new java.awt.Font(sTipoLetra,0, iTamañoLetra)); 
      
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
           iTamañoLetra=12;
        tblexcel.setFont(new java.awt.Font(sTipoLetra,0, iTamañoLetra));   
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed
       iTamañoLetra=20;
       tblexcel.setFont(new java.awt.Font(sTipoLetra,0, iTamañoLetra));  
    }//GEN-LAST:event_jMenuItem20ActionPerformed

    private void tblexcelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblexcelKeyTyped

    }//GEN-LAST:event_tblexcelKeyTyped

    private void tblexcelKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblexcelKeyReleased
        //se vuelve a centrar en la misma celda
        try{
            //método para obtener fila y columna al moverse con las flechas del teclado
            //arriba o abajo
            if (evt.getKeyCode() == 38 || evt.getKeyCode()== 40) {
                intFila=tblexcel.getSelectedRow();
                Lista();
            }
            //izquiera o derecha
            if (evt.getKeyCode() == 37 || evt.getKeyCode()== 39) {
                intColumna=tblexcel.getSelectedColumn();
                Lista();
            }
            /* pa mientras//obtiene la letra que ingreso
            String dato=(String) evt.getKeyText(evt.getKeyCode());
            //verifica si el dato es numero o letra
            int ascii= dato.charAt(0);
            if ((ascii >= 48) && (ascii <= 57)|| (ascii==84)) {
                AlinearDerecha();
            }else{
                AlinearIzquierda();
            }      */
            if (tblexcel.isEditing()) {
                tblexcel.requestFocus();
                // edita la celda
                tblexcel.editCellAt(intFila,intColumna);
            }else{
                tblexcel.requestFocus();
            }

            //Obtiene el dato de la celda
            tm=(DefaultTableModel) tblexcel.getModel();
        }catch(Exception ex){

        }
        VerificarVacio();
        this.txtBarra.setText(datos);
        Lista();
        System.out.println(datos);
        System.out.println(miLista.Listar());
        cmbcol.setSelectedIndex(intColumna);
        cmbfil.setSelectedIndex(intFila);
        tblexcel.requestFocus();
        //
    }//GEN-LAST:event_tblexcelKeyReleased

    private void tblexcelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblexcelKeyPressed
        //Si le da enter cambia el focus a la celda de abajo
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            intFila=(tblexcel.getSelectedRow())+1;
            tblexcel.requestFocus();
            System.out.println(intFila+" -"+intColumna+"-");
        }
    }//GEN-LAST:event_tblexcelKeyPressed

    private void tblexcelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblexcelMouseClicked
        // TODO add your handling code here:
        //método para obtener fila y columna al dar click a la celda
        intColumna=tblexcel.getSelectedColumn();
        intFila=tblexcel.getSelectedRow();
        tm=(DefaultTableModel) tblexcel.getModel();
        datos=String.valueOf(tm.getValueAt(tblexcel.getSelectedRow(),tblexcel.getSelectedColumn()));
        System.out.println(datos);

        Lista();
        //limpia barra principal
        this.txtBarra.setText((String) tm.getValueAt(tblexcel.getSelectedRow(),tblexcel.getSelectedColumn()));
        cmbcol.setSelectedIndex(intColumna);
        cmbfil.setSelectedIndex(intFila);
    }//GEN-LAST:event_tblexcelMouseClicked

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        itCursiva=1;
        cursiva();
        negrita(); 
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
            itNegr=1;
            negrita();
        cursiva();
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
                subrayado();
    }//GEN-LAST:event_jMenuItem8ActionPerformed
       public void Lista(){
           if (miLista.obtenerPos(intFila, intColumna)==-1) {
                miLista.insertarDato(new Celda(datos,intFila,intColumna));
            }
           if (datos.length()>=1) {
               miLista.modifyPorFilaColumna(intFila, intColumna, datos);
           }else{
               miLista.eliminarCelda(miLista.obtenerPos(intFila, intColumna));
           }
       }
            public void VerificarVacio(){
           if (tm.getValueAt(intFila, intColumna)==null) {
               datos="";
           }else{
               datos=String.valueOf(this.tm.getValueAt(intFila,intColumna)); 
           }
       
       }
       public void DetenerEditarCelda(){
          CellEditor cellEditor = tblexcel.getCellEditor();           
             if (cellEditor != null) {          
               if (cellEditor.getCellEditorValue() != null) {
                   cellEditor.stopCellEditing();
               } else {
                   cellEditor.cancelCellEditing();
               }
           }
       }
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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Vista().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnabrireli;
    private javax.swing.JButton calcular;
    private javax.swing.JComboBox<String> cmbcodigo;
    private javax.swing.JComboBox<String> cmbcol;
    private javax.swing.JComboBox<String> cmbfil;
    private javax.swing.JComboBox<String> cmblistado;
    private javax.swing.JTextField denom;
    private javax.swing.JButton jButton1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem24;
    private javax.swing.JMenuItem jMenuItem25;
    private javax.swing.JMenuItem jMenuItem26;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblsel;
    private javax.swing.JTextField numer;
    private javax.swing.JTable tblexcel;
    private javax.swing.JTextField txtBarra;
    // End of variables declaration//GEN-END:variables
}
