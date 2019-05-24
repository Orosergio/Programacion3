/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
/**
 * @author Sergio Orozco
 */
public class Vista extends javax.swing.JFrame {
    public static int intCod=0;
    public static int intFila, intColumna;
    public static Lista miLista=new Lista();
    public static Lista miAlineado=new Lista();
    public static String datos, alineado, strNombreAr;
    DefaultTableModel tm ;
    TableModel tmInicial;
    String sCopiado,sTipoLetra="Tahoma";
    String simbolo;
    String vctAbc[]=new String[27];//vector para el llenado de la busqueda de celda
    int itOpcion=0, itNegrita=0, itCursiva=0,itSubrayado=0;//variable pivote de ayuda, ricardo perez 1255
    int iTamañoLetra=12;
    int itX=0,itY=0;  //variables para obtener las cooredenadas de seleccion en la tabla
    /**
     * Creates new form Vista
     */
    public Vista() {
        initComponents();
        construirTabla();
        this.getContentPane().setBackground(new java.awt.Color(184,242,230));        
        tmInicial=tblexcel.getModel();
        //codigo para barra horizontal, hecho por ricardo perez 1255
        tblexcel.setAutoResizeMode(tblexcel.AUTO_RESIZE_OFF);
        tblexcel.setPreferredScrollableViewportSize(Toolkit.getDefaultToolkit().getScreenSize());
        //oculta algunos obtjetos 
        this.cmbcodigo.setVisible(false);
        this.lblsel.setVisible(false);
        this.cmblistado.setVisible(false);
        btnabrireli.setVisible(false); 
        //limpia algunos combobox 
        this.cmbcol.removeAllItems();
        this.cmbfil.removeAllItems();
        //llamado de metodos para llenado de combobox, de los 2 grupos hecho por ricardo perez 1255
        llenadocmbArchivos();
        EncabezadoNoFilas();
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
    }
        
    private void construirTabla (){
      JTableHeader jtableheader=tblexcel.getTableHeader();
      jtableheader.setDefaultRenderer(new EncabezadoTabla());
      tblexcel.setTableHeader(jtableheader);

    }
    //hecho por ricardo perez 0901-17-1255
    public void negrita(){
        if(itNegrita==0){//si la variable pivote es scero, colocara negrita
            itCursiva=0; itSubrayado=0;//cambio de dato en variable pivote
            tblexcel.setFont(new java.awt.Font(sTipoLetra,0, iTamañoLetra));
            tblexcel.setFont(new java.awt.Font(sTipoLetra,Font.BOLD, iTamañoLetra));
            itNegrita=1;//cambio de dato en variable pivote
        }else{//si la variable no es cero quitara lo negrita
            itNegrita=0;//cambio de dato en variable pivote
            tblexcel.setFont(new java.awt.Font(sTipoLetra,0, iTamañoLetra));
        }
    }
    
    //hecho por ricardo perez 0901-17-1255
    public void cursiva(){
        if(itCursiva==0){//si la variable pivote es cero, colocara cursiva
            itNegrita=0;itSubrayado=0;//cambio de dato en variable pivote
            tblexcel.setFont(new java.awt.Font(sTipoLetra,0, iTamañoLetra));
            tblexcel.setFont(new java.awt.Font(sTipoLetra,Font.ITALIC, iTamañoLetra));
            itCursiva=1;//cambio de dato en variable pivote
        }else{//si la variable pivote es diferente de cero, quitara cursiva
            itCursiva=0;//cambio de dato en variable pivote
            tblexcel.setFont(new java.awt.Font(sTipoLetra,0, iTamañoLetra));
        }
    }
    
    //hecho por ricardo perez 0901-17-1255
    public void subrayado(){
       if(itSubrayado==0){    //si la variable pivote es cero, colocara el subrayado 
            itNegrita=0;//cambio de dato en variable pivote
            itCursiva=0;//cambio de dato en variable pivote
            tblexcel.setFont(new java.awt.Font(sTipoLetra,0, iTamañoLetra));
            Font font = tblexcel.getFont();
            Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
            attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            tblexcel.setFont(font.deriveFont(attributes));
            itSubrayado=1;//cambio de dato en variable pivote
    }else{//si la variable pivote es diferente de cero, quitara el subrayado
            Font font = tblexcel.getFont();
            Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
            attributes.put(TextAttribute.UNDERLINE, -1);
            tblexcel.setFont(font.deriveFont(attributes));
            itSubrayado=0;//cambio de dato en variable pivote
        }
    }
    
    //hecho por ricardo perez 0901-17-1255
    public void ocultarobjetos(){
        //oculta cierto elementos al no ser utilizados    
        cmblistado.setVisible(false);
        btnabrireli.setVisible(false);
        this.lblsel.setVisible(false);
    }
    
    //hecho por ricardo perez 0901-17-1255
    public void llenadocmbArchivos(){
        //limpia lo que tienen los combobox
        this.cmblistado.removeAllItems();
        this.cmbcodigo.removeAllItems();
        try{
            //conexion de datos
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/excel", "root", "");
            PreparedStatement pst = cn.prepareStatement("SELECT * FROM tblarchivo");
            ResultSet rs = pst.executeQuery();
            boolean r=rs.next();
            while(r){
                //ingresa los datos de la consulta en los combobox
                this.cmbcodigo.addItem(Encriptacion.Desencriptar(rs.getString("codarch")));
                this.cmblistado.addItem(Encriptacion.Desencriptar(rs.getString("nombre")));
                r=rs.next();            
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Base de datos no enlazada");
        }
    }
    
   //hecho por ricardo perez 0901-17-1255 
   public void cmbllenado(){
        for(int i=1; i<=50;i++){// se llena los combobox de fila
            cmbfil.addItem(String.valueOf(i));
        }
        //vector con las letras de las columnas
        vctAbc[1]="A";vctAbc[2]="B";vctAbc[3]="C";vctAbc[4]="D";vctAbc[5]="E";vctAbc[6]="F";vctAbc[7]="G";vctAbc[8]="H";vctAbc[9]="I";vctAbc[10]="J";vctAbc[11]="K";vctAbc[12]="L";vctAbc[13]="M";
        vctAbc[14]="N";vctAbc[15]="O";vctAbc[16]="P";vctAbc[17]="Q";vctAbc[18]="R";vctAbc[19]="S";vctAbc[20]="T";vctAbc[21]="U";vctAbc[22]="V";vctAbc[23]="W";vctAbc[24]="X";vctAbc[25]="Y";vctAbc[26]="Z";      
        cmbcol.addItem(String.valueOf(""));// como la primera columna contiene las filas no mostrara nada
        for(int i=1; i<=26;i++){      //primera lista de columnas     
            cmbcol.addItem(String.valueOf(vctAbc[i]));
        }
        for(int i=1; i<=15;i++){//segunda lista con las columnas 
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblexcel = new javax.swing.JTable();
        txtBarra = new javax.swing.JTextField();
        cmbfil = new javax.swing.JComboBox<>();
        cmbcol = new javax.swing.JComboBox<>();
        cmblistado = new javax.swing.JComboBox<>();
        cmbcodigo = new javax.swing.JComboBox<>();
        btnabrireli = new javax.swing.JButton();
        lblsel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        btnFile = new javax.swing.JMenu();
        jMenuItem26 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem25 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        bntNumber = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
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
        btnSize = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenuItem20 = new javax.swing.JMenuItem();
        btnBold = new javax.swing.JMenu();
        btnUnder = new javax.swing.JMenu();
        btnItalic = new javax.swing.JMenu();
        btnCopy = new javax.swing.JMenu();
        btnPaste = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        btnOper = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        btnHelp = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Excel");

        tblexcel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA", "AB", "AC", "AD", "AE", "AF", "AG", "AH", "AI", "AJ", "AK"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
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
        if (tblexcel.getColumnModel().getColumnCount() > 0) {
            tblexcel.getColumnModel().getColumn(0).setPreferredWidth(30);
        }

        txtBarra.setEditable(false);
        txtBarra.setBackground(new java.awt.Color(255, 255, 255));
        txtBarra.setFont(new java.awt.Font("Palatino Linotype", 0, 14)); // NOI18N
        txtBarra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtBarra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBarraKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBarraKeyReleased(evt);
            }
        });

        cmbfil.setFont(new java.awt.Font("Palatino Linotype", 0, 14)); // NOI18N
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

        cmbcol.setFont(new java.awt.Font("Palatino Linotype", 0, 14)); // NOI18N
        cmbcol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbcol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbcolActionPerformed(evt);
            }
        });

        cmblistado.setFont(new java.awt.Font("Palatino Linotype", 0, 14)); // NOI18N
        cmblistado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmblistado.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmblistadoItemStateChanged(evt);
            }
        });

        cmbcodigo.setFont(new java.awt.Font("Palatino Linotype", 0, 14)); // NOI18N
        cmbcodigo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnabrireli.setFont(new java.awt.Font("Palatino Linotype", 0, 14)); // NOI18N
        btnabrireli.setText("Eliminar");
        btnabrireli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnabrireliActionPerformed(evt);
            }
        });

        lblsel.setFont(new java.awt.Font("Palatino Linotype", 0, 14)); // NOI18N
        lblsel.setText("Seleccione");

        jMenuBar1.setBackground(new java.awt.Color(174, 217, 224));

        btnFile.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/archivo2.png"))); // NOI18N
        btnFile.setText("Archivo");
        btnFile.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem26.setBackground(new java.awt.Color(255, 204, 204));
        jMenuItem26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/add-file.png"))); // NOI18N
        jMenuItem26.setText("Nuevo");
        jMenuItem26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem26ActionPerformed(evt);
            }
        });
        btnFile.add(jMenuItem26);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/folder1.png"))); // NOI18N
        jMenuItem3.setText("Abrir");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        btnFile.add(jMenuItem3);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/save.png"))); // NOI18N
        jMenuItem2.setText("Guardar");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        btnFile.add(jMenuItem2);

        jMenuItem25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/archivo.png"))); // NOI18N
        jMenuItem25.setText("Eliminar");
        jMenuItem25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem25ActionPerformed(evt);
            }
        });
        btnFile.add(jMenuItem25);

        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logout.png"))); // NOI18N
        jMenuItem4.setText("Cerrar");
        btnFile.add(jMenuItem4);

        jMenuBar1.add(btnFile);

        bntNumber.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        bntNumber.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/flecha.png"))); // NOI18N
        bntNumber.setText("Número");
        bntNumber.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fraccion.png"))); // NOI18N
        jMenuItem10.setText("Fracción 1/2");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        bntNumber.add(jMenuItem10);

        jMenuItem11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/moneda.png"))); // NOI18N
        jMenuItem11.setText("Moneda  Q.");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        bntNumber.add(jMenuItem11);

        jMenuItem12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/general.png"))); // NOI18N
        jMenuItem12.setText("General");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        bntNumber.add(jMenuItem12);

        jMenuItem13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/porcentaje.png"))); // NOI18N
        jMenuItem13.setText("Porcentaje %");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        bntNumber.add(jMenuItem13);

        jMenuBar1.add(bntNumber);

        jMenu5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/letra.png"))); // NOI18N
        jMenu5.setText("Letra");
        jMenu5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

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

        jMenuBar1.add(jMenu5);

        jMenu7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jMenu7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/align2.png"))); // NOI18N
        jMenu7.setText("Alineado");
        jMenu7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

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

        jMenuBar1.add(jMenu7);

        btnSize.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/size.png"))); // NOI18N
        btnSize.setText("Tamaño de Letra");
        btnSize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem1.setText("12");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        btnSize.add(jMenuItem1);

        jMenuItem19.setText("15");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        btnSize.add(jMenuItem19);

        jMenuItem20.setText("20");
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        btnSize.add(jMenuItem20);

        jMenuBar1.add(btnSize);

        btnBold.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnBold.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/bold.png"))); // NOI18N
        btnBold.setText("Negrita");
        btnBold.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBold.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBoldMouseClicked(evt);
            }
        });
        jMenuBar1.add(btnBold);

        btnUnder.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnUnder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/underline.png"))); // NOI18N
        btnUnder.setText("Subrayado");
        btnUnder.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUnder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUnderMouseClicked(evt);
            }
        });
        jMenuBar1.add(btnUnder);

        btnItalic.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnItalic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/italix.png"))); // NOI18N
        btnItalic.setText("Cursiva");
        btnItalic.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnItalic.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnItalicMouseClicked(evt);
            }
        });
        jMenuBar1.add(btnItalic);

        btnCopy.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCopy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pst.png"))); // NOI18N
        btnCopy.setText("Copiar");
        btnCopy.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCopy.setFont(new java.awt.Font("Palatino Linotype", 0, 12)); // NOI18N
        btnCopy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCopyMouseClicked(evt);
            }
        });
        jMenuBar1.add(btnCopy);

        btnPaste.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPaste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cpy.png"))); // NOI18N
        btnPaste.setText("Pegar");
        btnPaste.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPaste.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPasteMouseClicked(evt);
            }
        });
        jMenuBar1.add(btnPaste);

        jMenu1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cortar.png"))); // NOI18N
        jMenu1.setText("Cortar");
        jMenu1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuBar1.add(jMenu1);

        btnOper.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnOper.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/calculator.png"))); // NOI18N
        btnOper.setText("Operaciones");
        btnOper.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/sigma (1).png"))); // NOI18N
        jMenuItem6.setText("Suma");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        btnOper.add(jMenuItem6);

        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/multiply.png"))); // NOI18N
        jMenuItem7.setText("Productoria");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        btnOper.add(jMenuItem7);

        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/average.png"))); // NOI18N
        jMenuItem8.setText("Promedio");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        btnOper.add(jMenuItem8);

        jMenuBar1.add(btnOper);

        btnHelp.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnHelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/apoyo-tecnico.png"))); // NOI18N
        btnHelp.setText("Ayuda");
        btnHelp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem9.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F12, 0));
        jMenuItem9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/libro.png"))); // NOI18N
        jMenuItem9.setText("Manual de Usuario");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        btnHelp.add(jMenuItem9);

        jMenuBar1.add(btnHelp);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmbcol, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cmbfil, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtBarra))
            .addGroup(layout.createSequentialGroup()
                .addGap(397, 397, 397)
                .addComponent(lblsel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmblistado, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnabrireli)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 504, Short.MAX_VALUE)
                .addComponent(cmbcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBarra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbcol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblsel)
                        .addComponent(cmblistado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnabrireli))
                    .addComponent(cmbcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 654, Short.MAX_VALUE))
        );

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
          String sMostrarValor=Float.toString(intvalormostrar);
         tblexcel.setValueAt(sMostrarValor+"%", intFila, intColumna);
       
       }else if(infodatos.matches("\\d+\\/\\d+")){
           generalm();
            infodatos=String.valueOf(tm.getValueAt(tblexcel.getSelectedRow(),tblexcel.getSelectedColumn()));     
         float Intporcen=Float.parseFloat(infodatos);
         float intvalormostrar=Intporcen*100;
              String sMostrarValor=Float.toString(intvalormostrar);
         tblexcel.setValueAt(sMostrarValor+"%", intFila, intColumna);
       }else{
            infodatos=String.valueOf(tm.getValueAt(tblexcel.getSelectedRow(),tblexcel.getSelectedColumn()));     
         double Intporcen=Double.parseDouble(infodatos);
         double intvalormostrar=Intporcen*100;
         String sMostrarValor=Double.toString(intvalormostrar);
         tblexcel.setValueAt(sMostrarValor+"%", intFila, intColumna);
         
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
            //Validacion por si no tuviera punto decimal itY no se quede en bucle
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
        tblexcel.setValueAt(nuevodato, intFila, intColumna);
        
       } else  if(infodatos.matches("\\d+\\.\\d+\\%")){
            //COMPROBACION DE PORCENTAJE
      int intsigno=0,intlast=infodatos.length();
      while(infodatos.charAt(intsigno)!='%'){
            intsigno++;
            //Validacion por si no tuviera punto decimal itY no se quede en bucle
            if(intsigno==infodatos.length()){
                break;
            }
        } 
           
          double flnumero=Double.parseDouble(infodatos.substring(0, intsigno-1));
          double intTotal2=flnumero/100;
          tblexcel.setValueAt(Double.toString(intTotal2), intFila, intColumna);
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
            //Validacion por si no tuviera punto decimal itY no se quede en bucle
            if(p==infodatos.length()){
                break;
            }
        } 
        //envio de parametro
       double decimal=Double.parseDouble(infodatos.substring(p+1, infodatos.length()));
       ControllerGeneral control=new ControllerGeneral(decimal);
       JOptionPane.showMessageDialog(null, control.toFraccion(decimal));
       String SValorMostrar=control.toFraccion(decimal);
       System.out.println(SValorMostrar.toString());
       tblexcel.setValueAt(SValorMostrar, intFila, intColumna);
       String infodatos2=String.valueOf(tm.getValueAt(tblexcel.getSelectedRow(),tblexcel.getSelectedColumn()));
       }else  if(infodatos.matches("\\d+\\.\\d+\\%")){
            //COMPROBACION DE PORCENTAJE
      int intsigno=0,intlast=infodatos.length();
      while(infodatos.charAt(intsigno)!='%'){
            intsigno++;
            //Validacion por si no tuviera punto decimal itY no se quede en bucle
            if(intsigno==infodatos.length()){
                break;
            }
        } 
          double flnumero=Double.parseDouble(infodatos.substring(0, intsigno-1));
          double intTotal2=flnumero/100;
          double decimal=intTotal2;
          ControllerGeneral control=new ControllerGeneral(decimal);
          String SValorMostrar=control.toFraccion(decimal);
          tblexcel.setValueAt(SValorMostrar, intFila, intColumna);
      }
       else{
       double decimal=Double.parseDouble(infodatos);
       ControllerGeneral control=new ControllerGeneral(decimal);
       JOptionPane.showMessageDialog(null, control.toFraccion(decimal));
       String SValorMostrar=control.toFraccion(decimal);
       System.out.println(SValorMostrar.toString());
       tblexcel.setValueAt(SValorMostrar, intFila, intColumna);
       String infodatos2=String.valueOf(tm.getValueAt(tblexcel.getSelectedRow(),tblexcel.getSelectedColumn()));
           
       }   
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
            //Validacion por si no tuviera punto decimal itY no se quede en bucle
            if(p==infodatos.length()){
                break;
            }
        } 
//envio de parametro
       double normal=Double.parseDouble(infodatos.substring(p+1, infodatos.length()));
       tblexcel.setValueAt(Double.toString(normal), intFila, intColumna);
       } else  if(infodatos.matches("\\d+\\.\\d+\\%")){
            //COMPROBACION DE PORCENTAJE
      int intsigno=0,intlast=infodatos.length();
      while(infodatos.charAt(intsigno)!='%'){
            intsigno++;
            //Validacion por si no tuviera punto decimal itY no se quede en bucle
            if(intsigno==infodatos.length()){
                break;
            }
        } 
           
          double flnumero=Double.parseDouble(infodatos.substring(0, intsigno-1));
          double intTotal2=flnumero/100;
          tblexcel.setValueAt(Double.toString(intTotal2), intFila, intColumna);
      }
       
       else{
          int o=0;
        while(infodatos.charAt(o)!='/'){
            o++;
            //Validacion por si no tuviera punto decimal itY no se quede en bucle
            if(o==infodatos.length()){
                break;
            }
        } 
        double numer=Double.parseDouble(infodatos.substring(0, o));
        double denom=Double.parseDouble(infodatos.substring(o+1, infodatos.length()));
        double total=0;
           System.out.println("daots: "+numer+" denom: "+denom);
        total=numer/denom;
        tblexcel.setValueAt(Double.toString(total), intFila, intColumna);
        
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
    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
       Porcentaje();
       VerificarVacio();
       Lista();
       System.out.println(miLista.Listar());
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
        fractoria();
        VerificarVacio();
        Lista();
        System.out.println(miLista.Listar());
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // TODO add your handling code here:.
        moneda();
        Lista();
        VerificarVacio();
       System.out.println(miLista.Listar());
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
         sTipoLetra="Palatino Linotype";
        tblexcel.setFont(new java.awt.Font(sTipoLetra,0, iTamañoLetra));      
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
       iTamañoLetra=15;
       tblexcel.setFont(new java.awt.Font(sTipoLetra,0, iTamañoLetra));  
       TamañoCol(iTamañoLetra);
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem22ActionPerformed
        //alinea la celda a la derecha
      alineado="2";
      ListaAlienado();
      System.out.println(miAlineado.ListarAlineado());
     DefaultTableCellRenderer modelocentrar = new DefaultTableCellRenderer();
      modelocentrar.setHorizontalAlignment(SwingConstants.RIGHT);
      tblexcel.getColumnModel().getColumn(intColumna).setCellRenderer(modelocentrar);       
    }//GEN-LAST:event_jMenuItem22ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
  //alinea la celda a la izquierda
     alineado="1";
     ListaAlienado();
    System.out.println(miAlineado.ListarAlineado());
     DefaultTableCellRenderer modelocentrar = new DefaultTableCellRenderer();
      modelocentrar.setHorizontalAlignment(SwingConstants.LEFT);
      tblexcel.getColumnModel().getColumn(intColumna).setCellRenderer(modelocentrar);            
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem21ActionPerformed
     //alinea la celda al centro
      alineado="3";
      ListaAlienado();
      System.out.println(miAlineado.ListarAlineado());
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
    
    //hecho por ricardo perez 0901-17-1255
    private void cmbfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbfilActionPerformed
        //se obtinen los indeces de los combobox, el cual nos permite saber la fila itY columna
        intColumna=cmbcol.getSelectedIndex();
        intFila=cmbfil.getSelectedIndex();    
        if (tblexcel.isEditing()) {
            //se cambiara a la ubicacion deseada para poder modificar texto alli
            tblexcel.editCellAt(intFila,intColumna);
            tblexcel.changeSelection(intFila,intColumna,false, false);       
            tblexcel.requestFocus();     
        }else{
             tblexcel.changeSelection(intFila,intColumna,false, false);       
             tblexcel.requestFocus(); 
        }   
    }//GEN-LAST:event_cmbfilActionPerformed

    //hecho por ricardo perez 0901-17-1255
    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
       //muestra un combobox itY un boton para que pueda seleccionar el archivo que desea abrir        
       itOpcion=1;
       btnabrireli.setText("Abrir");
       btnabrireli.setVisible(true);
       this.lblsel.setVisible(true);
       cmblistado.setVisible(true);
       NuevoArchivo();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    //hecho por ricardo perez 0901-17-1255
    private void cmblistadoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmblistadoItemStateChanged
        try{ cmbcodigo.setSelectedIndex(cmblistado.getSelectedIndex());}// se cambia el indice del combo junto con el otro
        catch(Exception e){
        }
    }//GEN-LAST:event_cmblistadoItemStateChanged

    //hecho por ricardo perez 0901-17-1255
    private void btnabrireliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnabrireliActionPerformed
        //dependiendo si eleigio eliminar o abrir, eso esta en la variabel itOpcion,
        //entonces entrara en el if itY luego de realizar la accion vuelve a cerrar el combobox itY el boton
        if(itOpcion==1){               
        try{//obtencion de datos
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/excel", "root", "");
            PreparedStatement pst = cn.prepareStatement("SELECT * FROM `tblcontenido` WHERE codarch='"+Encriptacion.Encriptar(String.valueOf(cmbcodigo.getSelectedItem()))+"';");
            ResultSet rs = pst.executeQuery();
            boolean r=rs.next();
            while(r){//obtencion de base de datos
               tblexcel.setValueAt(Encriptacion.Desencriptar(rs.getString("contenido")), Integer.parseInt(Encriptacion.Desencriptar(rs.getString("fila"))), Integer.parseInt(Encriptacion.Desencriptar(rs.getString("colum"))));
               miLista.insertarDato(new Celda(Encriptacion.Desencriptar(rs.getString("contenido")), Integer.parseInt(Encriptacion.Desencriptar(rs.getString("fila"))), Integer.parseInt(Encriptacion.Desencriptar(rs.getString("colum")))));//agrega a lista
               r=rs.next();
               itX++;               
            }
            this.setTitle(cmblistado.getSelectedItem().toString());
            intCod=Integer.parseInt(cmbcodigo.getSelectedItem().toString());
            strNombreAr=this.cmblistado.getSelectedItem().toString();
            System.out.println(miLista.Listar());  
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"le dio un Error fatal "+e);
        }
        try{//obtencion de datos
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/excel", "root", "");
            PreparedStatement pst = cn.prepareStatement("SELECT * FROM `tblarchivo` WHERE codarch='"+Encriptacion.Encriptar(String.valueOf(cmbcodigo.getSelectedItem()))+"';");
            ResultSet rs = pst.executeQuery();
            boolean r=rs.next();
            while(r){          //obtencion de propiedades de la tabla          
               itNegrita=Integer.parseInt(Encriptacion.Desencriptar(rs.getString("negrita")));
               itCursiva=Integer.parseInt(Encriptacion.Desencriptar(rs.getString("cursiva")));
               itSubrayado=Integer.parseInt(Encriptacion.Desencriptar(rs.getString("subrayada")));
               sTipoLetra=String.valueOf(Encriptacion.Desencriptar(rs.getString("tipoletra")));
               iTamañoLetra=Integer.parseInt(Encriptacion.Desencriptar(rs.getString("tamano")));
                r=rs.next();
                itX++;               
            }
            //se aplica el tamaño itY tipo de letra         
            tblexcel.setFont(new java.awt.Font(sTipoLetra,0, iTamañoLetra)); 
            //se colocan las caracteristicas de cada cosa
            if(itNegrita==1){
            itNegrita=0;
            negrita();//lamado del metodo
            }            
             if(itCursiva==1){
            itCursiva=0;
            cursiva();//lamado del metodo
            } 
            if(itSubrayado==1){
            itSubrayado=0;
            subrayado();//lamado del metodo
            }                     
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"le dio un Error fatal "+e);
        }
        ConsultaAlinear();//metodo vara aliniear los datos, segun lo guardado en la bd
        }
        if(itOpcion==2){
             int itres, iterror=0;//variables para obtencion de error
             itres=  JOptionPane.showConfirmDialog(this,"¿Esta seguro de eliminar los datos?","Eliminar",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
             if(itres==JOptionPane.YES_OPTION){//si esta seguro de eliminar
                try{//elimina de la base de datos de acuerdo con el codigo del archivo
                    Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/excel", "root", "");
                    PreparedStatement pst = cn.prepareStatement("DELETE FROM `tblalineacion` WHERE codarch='"+Encriptacion.Desencriptar(String.valueOf(cmbcodigo.getSelectedItem()))+"';");
                    pst.executeUpdate();            
                }catch (Exception e){
                   JOptionPane.showMessageDialog(null,"le dio un error "+e);
                } 
            try{//elimina de la base de datos de acuerdo con el codigo del archivo
                Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/excel", "root", "");
                PreparedStatement pst = cn.prepareStatement("DELETE FROM `tblcontenido` WHERE codarch='"+Encriptacion.Desencriptar(String.valueOf(cmbcodigo.getSelectedItem()))+"';");
                pst.executeUpdate(); 
                iterror=1;
            }catch (Exception e){
                JOptionPane.showMessageDialog(null,"le dio un error "+e);
            }
         try{//elimina de la base de datos de acuerdo con el codigo del archivo
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/excel", "root", "");
            PreparedStatement pst = cn.prepareStatement("DELETE FROM `tblarchivo` WHERE codarch='"+Encriptacion.Desencriptar(String.valueOf(cmbcodigo.getSelectedItem()))+"';");
            pst.executeUpdate();  
            iterror=1+iterror;
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"le dio un error "+e);
        }
        if(iterror==2){                  
             JOptionPane.showMessageDialog(null,"Dato Eliminado con exito");// si no hubieron errores se muestra que se elimino todo
        }
        llenadocmbArchivos();//actualiza los combobox de los archivos
       }     
    }  //se ocultan algunos objetos que ya no se usaran
        cmblistado.setVisible(false);
        btnabrireli.setVisible(false);
        this.lblsel.setVisible(false);
    }//GEN-LAST:event_btnabrireliActionPerformed
    
    //hecho por ricardo perez 1255
    private void jMenuItem25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem25ActionPerformed
        //muestra un combobox itY un boton para que pueda seleccionar el archivo que desea eliminar       
       itOpcion=2;
       btnabrireli.setText("Eliminar");
       btnabrireli.setVisible(true);
       cmblistado.setVisible(true);
       this.lblsel.setVisible(true);
    }//GEN-LAST:event_jMenuItem25ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        //Hecho por Bryan Aguirre(Colossatrox)0901-17-646
        Guardar();
        ocultarobjetos();
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
        TamañoCol(iTamañoLetra);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed
       iTamañoLetra=20;
       tblexcel.setFont(new java.awt.Font(sTipoLetra,0, iTamañoLetra)); 
       TamañoCol(iTamañoLetra);
    }//GEN-LAST:event_jMenuItem20ActionPerformed

    private void tblexcelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblexcelKeyTyped

    }//GEN-LAST:event_tblexcelKeyTyped

    private void tblexcelKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblexcelKeyReleased
        //se vuelve a centrar en la misma celda
        try{
            //método para obtener fila itY columna al moverse con las flechas del teclado
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
        // muestra la ubicacion actual en los combobox
        //hecho por ricardo perez 1255: lo siguiente
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
        //método para obtener fila itY columna al dar click a la celda
        intColumna=tblexcel.getSelectedColumn();
        intFila=tblexcel.getSelectedRow();
        tm=(DefaultTableModel) tblexcel.getModel();
        datos=String.valueOf(tm.getValueAt(tblexcel.getSelectedRow(),tblexcel.getSelectedColumn()));
        System.out.println(datos);
        VerificarVacio();
        Lista();
        //limpia barra principal
        try{
        this.txtBarra.setText((String) tm.getValueAt(tblexcel.getSelectedRow(),tblexcel.getSelectedColumn()));
        }catch(Exception e){}
        //hecho por ricardo perez 1255: lo siguiente
        cmbcol.setSelectedIndex(intColumna);
        cmbfil.setSelectedIndex(intFila);
    }//GEN-LAST:event_tblexcelMouseClicked

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        new ManualdeUsuario().setVisible(true);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
      generalm();
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem26ActionPerformed
        //Hecho por Bryan Aguirre(Colossatrox)0901-17-646
        NuevoArchivo();
        ocultarobjetos();
    }//GEN-LAST:event_jMenuItem26ActionPerformed

    private void btnItalicMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnItalicMouseClicked
        //hecho por ricardo perez 1255
        // TODO add your handling code here:
        cursiva();
    }//GEN-LAST:event_btnItalicMouseClicked

    private void btnBoldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBoldMouseClicked
          //hecho por ricardo perez 1255
          // TODO add your handling code here:
          negrita(); 
    }//GEN-LAST:event_btnBoldMouseClicked

    private void btnUnderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUnderMouseClicked
        //hecho por ricardo perez 1255
        subrayado();     
    }//GEN-LAST:event_btnUnderMouseClicked

    private void btnCopyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCopyMouseClicked
        // TODO add your handling code here:
        if (tm.getValueAt(intFila, intColumna)==null) {
            sCopiado="";
        }else{
            sCopiado=String.valueOf(this.tm.getValueAt(intFila,intColumna)); 
        }
    }//GEN-LAST:event_btnCopyMouseClicked

    private void btnPasteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPasteMouseClicked
        // TODO add your handling code here:
         System.out.println(sCopiado+"-"+intFila+"-"+intColumna);            
        tblexcel.setValueAt(sCopiado, intFila, intColumna);
        tblexcel.requestFocus();          
        tblexcel.editCellAt(intFila,intColumna);       
        VerificarVacio();   
        this.txtBarra.setText(datos); 
        
        Lista();
        System.out.println(miLista.Listar());
    }//GEN-LAST:event_btnPasteMouseClicked

    //hecho por Bryan Aguirre(Colossatrox) 0901-17-646
    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        Operaciones(1);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    //hecho por Bryan Aguirre(Colossatrox) 0901-17-646
    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        Operaciones(2);
    }//GEN-LAST:event_jMenuItem7ActionPerformed
    
    //hecho por Bryan Aguirre(Colossatrox) 0901-17-646
    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        Operaciones(3);
    }//GEN-LAST:event_jMenuItem8ActionPerformed
    
    //hecho por Bryan Aguirre(Colossatrox) 0901-17-646
    //método para realizar operaciones de sumatoria, productoria y promedio
    public void Operaciones(int op){
        //declaración de variables
        int intColumas,intFilass[], intCantDatos=0, intUltFila=0;
        Double dblSuma=0.0, dblProductoria=1.0, dblProm=0.0;
        String strDatos="";
        //se obtiene el número de columna que se tiene seleccionado
        intColumas=tblexcel.getSelectedColumn();
        //se obtiene el número de filas seleccionadas y se guarda en un vector de tipo int
        intFilass=tblexcel.getSelectedRows();
        //ciclo desde 0 hasta menor al tamaño del vector intFilass para obtener sus valores
        for (int i = 0; i < intFilass.length; i++) {
            try{
                //si lo obtenido de la celda es nulo se guarda la variable como vacío
                if (tblexcel.getValueAt(intFilass[i], intColumas)==null) {
                    strDatos="";
                // sino se guarda con el valor obtenido de la celda
                }else{
                    strDatos=tblexcel.getValueAt(intFilass[i], intColumas).toString().trim();
                }
            }catch(Exception e){
            }
            //si strDatos no está vacía
            if (!strDatos.equals("")) {
                //la variable se guarda con el valor obtenido de llamar al método CambiarValor
                strDatos=CambiarValor(strDatos);
                //si la variable contiene solo números o números decimales
                if (strDatos.matches("\\d+")|| strDatos.matches("\\d+.\\d+")) {
                    //se suma uno a la cantidad de datos para luego usarla en promedio
                    intCantDatos++;
                    //la variable de suma va sumando los valores obtenidos
                    dblSuma+=Double.parseDouble(strDatos);
                    //la variable de productoria va multiplicando los valores obtenidos
                    dblProductoria*=Double.parseDouble(strDatos);
                }
            }
            //se guarda el último valor obtenido del vector 
            intUltFila=intFilass[i];
        }
        //se saca el promedio con la suma divido la cantidad de datos
        dblProm=dblSuma/intCantDatos;
        switch(op){
            //si la opción obtenida es uno escribe abajo de la selección el resultado de la suma
            case 1: tblexcel.setValueAt(dblSuma.toString(), intUltFila+1, intColumas);
                break;
            //si la opción obtenida es dos escribe abajo de la selección el resultado de la productoria
            case 2: tblexcel.setValueAt(dblProductoria.toString(), intUltFila+1, intColumas);
                break;
            //si la opción obtenida es tre escribe abajo de la selección el resultado del promedio
            case 3: tblexcel.setValueAt(dblProm.toString(), intUltFila+1, intColumas);
                break;
        }
        //pide que la tabla se vuelva a enfocar
        tblexcel.requestFocus();          
        tblexcel.editCellAt(intUltFila+1,intColumas);
        //se cambian los valores globales de intFila e intColumna
        intFila=intUltFila+1;
        intColumna=intColumas;
        //se llaman los 2 siguientes métodos para agregar los resultados a la lista
        VerificarVacio();   
        Lista();
        System.out.println(miLista.Listar());
    }
    
    //hecho por Bryan Aguirre(Colossatrox) 0901-17-646
    //método para cambiar el número si tiene porcentaje, moneda o fracción
    public String CambiarValor(String texto){
        //si el primer caracter del texto obtenido es una Q
        if(texto.charAt(0)=='Q'){           
            int p=0;
            while(texto.charAt(p)!='Q'){
                p++;
                //Validacion por si no tuviera punto decimal itY no se quede en bucle
                if(p==texto.length()){
                    break;
                }
            } 
            //se guarda en una variable el valor obtenido despues de la Q
            double normal=Double.parseDouble(texto.substring(p+1, texto.length()));
            //devuelve el valor de normal
            return String.valueOf(normal);
        //verificación si el texto obtenido es porcentaje    
        }else  if(texto.matches("\\d+\\.\\d+\\%")){
            //declaración de variables
            int intsigno=0,intlast=texto.length();
            //mientras el caracter del texto no sea porcentaje
            while(texto.charAt(intsigno)!='%'){
                //se suma uno 
                intsigno++;
                //Validacion por si no tuviera punto decimal itY no se quede en bucle
                if(intsigno==texto.length()){
                    break;
                }
            }
            //se guarda en una variable el texto desde 0 hasta que encuentre el % -1
            double flnumero=Double.parseDouble(texto.substring(0, intsigno-1));
            //se divide el numero entre 100 para sacar el valor 
            double intTotal2=flnumero/100;
            //se devuelve la variable
            return String.valueOf(intTotal2);
        //verificación si el texto es fracción
        }else if(texto.matches("\\d+\\/\\d+")){
            int o=0;
            //mientras el caracter no tenga la diagonal
            while(texto.charAt(o)!='/'){
                o++;
                //Validacion por si no tuviera punto decimal itY no se quede en bucle
                if(o==texto.length()){
                    break;
                }
            } 
            //se guarda en una variable el texto desde 0 hasta donde encuentre la diagonal
            double numer=Double.parseDouble(texto.substring(0, o));
            //se guarda el denominador del texto obtenido de despues de la diagonal hasta el final
            double denom=Double.parseDouble(texto.substring(o+1, texto.length()));
            double total=0;
            //se saca el número dividiendo el numerador entre el denominador
            total=numer/denom;
            //se devuelve la variable
            return String.valueOf(total);
        //sino tiene ningún formato de los anteriores solo devuelve el texto normal
        }else{
            return texto;
        }
    }
    
    //hecho por Bryan Aguirre(Colossatrox) 0901-17-646
    //método para ajustar el tamaño de las columnas
    public void TamañoCol(int tamaño){
        for (int i = 0; i <= tblexcel.getRowCount(); i++) {
            tblexcel.setRowHeight(i, tamaño);
        }
    }
    
    //hecho por Bryan Aguirre(Colossatrox) 0901-17-646
    //método para crear un nuevo archivo
    public void NuevoArchivo(){
        //Pregunta si desea guardar el archivo, si si, llama el método de guardar
        if (JOptionPane.showConfirmDialog(rootPane, "¿Desea guardar el archivo en el que está actualmente trabajando?","Guardar actual", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            Guardar();
        }
        //se pone la barra del frame vacía
        this.setTitle("");
        //se llama el método para que pare de editar la celda
        this.DetenerEditarCelda();
        //ciclo para limpiar todo el contenido de la tabla
        for (int i = 0; i < tblexcel.getRowCount(); i++) {
            for (int j = 0; j < tblexcel.getColumnCount(); j++) {
                //pone en blanco cada celda de la tabla
                tblexcel.setValueAt("", i, j);
            }
        }
        //limpia el contenido de la barra donde se escribe
        txtBarra.setText("");
        //se llama el método
        EncabezadoNoFilas();
        //se pone la tabla con la letra y tamaño por defecto
        tblexcel.setFont(new java.awt.Font("Tahoma",0, 12));
        iTamañoLetra=12;
        sTipoLetra="Tahoma";
        //se vacía la lista del contenido de las celdas
        miLista.vaciarLista();
        //se vacía la lista del contenido de las alineaciones
        miAlineado.vaciarListadeAlineacion();
    }
    
    //hecho por Bryan Aguirre(Colossatrox) 0901-17-646
    //método para guardar las celdas escritas en la lista
    public void Lista(){
        try{
            //si la columa es diferente de 0
            if(intColumna!=0){
                //si no se encuentra el nodo de la fila y posicion actual de las variables
                if (miLista.obtenerPos(intFila, intColumna)==-1) {
                    //si la columna no es igual a -1
                    if (intColumna!=-1) {
                        //se inserta el dato de la celda en la lista
                        miLista.insertarDato(new Celda(datos,intFila,intColumna));   
                    }
                }
                //si el largo del contenido de la variable es mayor o igual a 1
                if (datos.length()>=1) {
                    //se modifica el nodo, buscándolo por fila y columna, se modifica por el dato nuevo
                    miLista.modifyPorFilaColumna(intFila, intColumna, datos);
                }else{
                    //sino se elimina el nodo obteniendo la posicion por la fila y columna
                    miLista.eliminarCelda(miLista.obtenerPos(intFila, intColumna));
                }
            }
        }catch(Exception e){
            System.out.println("no tiene que guarda en la lista");
        }
    }
    public void VerificarVacio(){
        try{
            if (tm.getValueAt(intFila, intColumna)==null) {
                datos="";
            }else{
                datos=String.valueOf(this.tm.getValueAt(intFila,intColumna)); 
            }
        }catch(Exception e){
        }

    }
    public void ListaAlienado(){
           if (miAlineado.obtenerColumna(intColumna)==-1) {
                miAlineado.insertarAlineado(new Alinear(alineado,intColumna));
            }
           if (alineado.length()>=1) {
               miAlineado.modificarPorColumna(intColumna, alineado);              
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
    
    //hecho por Bryan Aguirre(Colossatrox) 0901-17-646
    //método para obtener el código del archivo con el que se va a guardar
    public void obtenerCodigoAr(){
         try{
               //Conección con la base de datos
               Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/excel", "root", "");
               PreparedStatement pst = cn.prepareStatement("select * from tblarchivo");
               ResultSet rs = pst.executeQuery();
               boolean r=rs.next();
               //mientras encuentre datos en la tabla itY campo especificado
               while(r){
                   //intCod servirá para código automático por lo que se busca el más grande guardado en la base de datos
                   if (intCod< Integer.parseInt(Encriptacion.Desencriptar(rs.getString("codarch")))) {
                       intCod=Integer.parseInt(Encriptacion.Desencriptar(rs.getString("codarch")));
                   }
                   r=rs.next();
               }
               //se suma uno
               intCod++;
           }catch (Exception e){
               System.out.println("No conecta");
           }
    }
    
    //hecho por Bryan Aguirre(Colossatrox) 0901-17-646
    //método para guardar la lista en el archivo
    public void Guardar(){
        //si la barra del programa no es igual al nombre de la variable
        if (!this.getTitle().equals(strNombreAr)) {
            //se obtiene el código con el que se va a guardar   
            obtenerCodigoAr();
            //se pide que ingrese el nombre del archivo con el que se quiere guardar
            strNombreAr=JOptionPane.showInputDialog(null,"Ingrese el nombre del archivo con que desea guardar","Nombre Archivo",JOptionPane.QUESTION_MESSAGE+JOptionPane.OK_OPTION);
            try{
                //Conección con la base de datos
                Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/excel", "root", "");
                PreparedStatement pst = cn.prepareStatement("INSERT INTO `tblarchivo`(`codarch`, `nombre`,`negrita`, `cursiva`, `subrayada`,`tamano`, `tipoletra`) VALUES (?,?,?,?,?,?,?)");
                pst.setString(1, String.valueOf(Encriptacion.Encriptar(String.valueOf(intCod))));
                pst.setString(2, Encriptacion.Encriptar(String.valueOf(strNombreAr.trim())));
                pst.setString(3, String.valueOf(Encriptacion.Encriptar(String.valueOf(itNegrita))));
                pst.setString(4, String.valueOf(Encriptacion.Encriptar(String.valueOf(itCursiva))));
                pst.setString(5, String.valueOf(Encriptacion.Encriptar(String.valueOf(itSubrayado))));
                pst.setString(6, String.valueOf(Encriptacion.Encriptar(String.valueOf(iTamañoLetra))));
                pst.setString(7, String.valueOf(Encriptacion.Encriptar(String.valueOf(sTipoLetra))));
                pst.executeUpdate();
                //se agregan los datos ingresados a la base de datos 
                JOptionPane.showMessageDialog(this, "Datos ingresados correctamente","ÉXITO",JOptionPane.INFORMATION_MESSAGE);
                this.setTitle(strNombreAr);
            }catch (Exception e){
                System.out.println("le dio un error ar " +e);
            }
        //sino
        }else{
            //modifica la base de datos con los nuevos datos ingresados a la tabla
            try{
                Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/excel", "root", "");
                PreparedStatement pst = cn.prepareStatement("update tblarchivo set tamano= ?, tipoletra= ?, negrita= ?, cursiva= ?, subrayada= ? where codarch= '"+Encriptacion.Encriptar(String.valueOf(intCod))+"'");
                pst.setString(1, String.valueOf(Encriptacion.Encriptar(String.valueOf(iTamañoLetra))));
                pst.setString(2, String.valueOf(Encriptacion.Encriptar(String.valueOf(sTipoLetra))));
                pst.setString(3, String.valueOf(Encriptacion.Encriptar(String.valueOf(itNegrita))));
                pst.setString(4, String.valueOf(Encriptacion.Encriptar(String.valueOf(itCursiva))));   
                pst.setString(5, String.valueOf(Encriptacion.Encriptar(String.valueOf(itSubrayado))));   
                pst.executeUpdate(); 
            }catch (Exception e){
                JOptionPane.showMessageDialog(null,"le dio un error modificacion"+e);
            }
            //elimina el contenido guardado para poder guardar el nuevo
            try{
                Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/excel", "root", "");
                PreparedStatement pst = cn.prepareStatement("DELETE FROM `tblcontenido` WHERE codarch='"+Encriptacion.Encriptar(String.valueOf(intCod))+"';");
                pst.executeUpdate(); 
            }catch (Exception e){
                JOptionPane.showMessageDialog(null,"le dio un error "+e);
            }
            //elimina el contenido guardado de alineación para poder guardar el nuevo
            try{
                Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/excel", "root", "");
                PreparedStatement pst = cn.prepareStatement("DELETE FROM `tblalineacion` WHERE codarch='"+Encriptacion.Encriptar(String.valueOf(intCod))+"';");
                pst.executeUpdate(); 
            }catch (Exception e){
                JOptionPane.showMessageDialog(null,"le dio un error "+e);
            }
        }
        //ciclo desde 0 hasta uno menos a la longitud de la lista
        for (int i = 0; i < miLista.contar(); i++) {
            //se guarda en una variable el contenido del nodo
            String strCont=miLista.obtenerNodo(i);
            //se divide el contenido por partes, dividiendolo por ; 
            String[] strPartes=strCont.split(";");
            //se guarda la segunda división como la fila
            int fila=Integer.parseInt(strPartes[1]);
            //se guarda la tercera división como la columna
            int col=Integer.parseInt(strPartes[2]);
            //se guarda la primera parte como el contenido
            String cont=strPartes[0];
            try{
                //Conección con la base de datos
                Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/excel", "root", "");
                PreparedStatement pst = cn.prepareStatement("INSERT INTO `tblcontenido`(`fila`, `colum`, `codarch`, `contenido`) VALUES (?,?,?,?)");
                pst.setString(1, String.valueOf(Encriptacion.Encriptar(String.valueOf(fila))));
                pst.setString(2, String.valueOf(Encriptacion.Encriptar(String.valueOf(col))));
                pst.setString(3, String.valueOf(Encriptacion.Encriptar(String.valueOf(intCod))));
                pst.setString(4, String.valueOf(Encriptacion.Encriptar(String.valueOf(cont))));               
                pst.executeUpdate();
                //se agregan los datos ingresados a la base de datos 
                JOptionPane.showMessageDialog(this, "Datos ingresados correctamente","ÉXITO",JOptionPane.INFORMATION_MESSAGE);
            }catch (Exception e){
                System.out.println("celda ya guardada"+e);
            }
        }
        //ciclo desde 0 hasta la longitud de la lista de alineado
        for (int i = 0; i < miAlineado.Tamaño(); i++) {
            //se guarda en una variable el contenido del nodo
            String sContar=miAlineado.obtenerNodoAlienar(i);
            //se divide el contenido por partes, dividiendolo por ; 
            String[] sPartes=sContar.split(";");   
            //se guarda la segunda división como la fila
            int icol=Integer.parseInt(sPartes[1]);
            //se guarda la primera división como la fila
            String cont=sPartes[0];
            try{
                //Conección con la base de datos
                Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/excel", "root", "");
                PreparedStatement pst = cn.prepareStatement("insert into tblalineacion (codalinea,nombre,codarch) values(?,?,?)");
                pst.setString(1, String.valueOf(Encriptacion.Encriptar(String.valueOf(icol))));
                pst.setString(2, String.valueOf(Encriptacion.Encriptar(String.valueOf(cont)))); 
                pst.setString(3, String.valueOf(Encriptacion.Encriptar(String.valueOf(intCod))));
                pst.executeUpdate();
                //se agregan los datos ingresados a la base de datos 
                JOptionPane.showMessageDialog(this, "Datos ingresados correctamente","ÉXITO",JOptionPane.INFORMATION_MESSAGE);
            }catch (Exception e){
                System.out.println("le dio un errorsote "+e);
            }
        }
        //se actualiza el combo para abrir archivos
        llenadocmbArchivos();
    }
    
    //hecho por Bryan Aguirre(Colossatrox) 0901-17-646
    //método para poner los números de filas
    public void EncabezadoNoFilas(){
        for(int i=0; i<tblexcel.getRowCount();i++){
          tblexcel.setValueAt(i+1, i, 0);//coloca el numero de fila
        }
    }
    public void ConsultaAlinear(){
        try{//obtencion de datos
            int iAlinear,integerColumna;
              Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/excel", "root", "");
              PreparedStatement pst = cn.prepareStatement("SELECT * FROM `tblalineacion` WHERE codarch='"+Encriptacion.Encriptar(String.valueOf(cmbcodigo.getSelectedItem()))+"';");
              ResultSet rs = pst.executeQuery();
              boolean r=rs.next();
              while(r){     
                integerColumna=Integer.parseInt(Encriptacion.Desencriptar(rs.getString("codalinea")));
                iAlinear=Integer.parseInt(Encriptacion.Desencriptar(rs.getString("nombre")));
                  if (iAlinear==1) {
                       DefaultTableCellRenderer modelocentrar = new DefaultTableCellRenderer();
                       modelocentrar.setHorizontalAlignment(SwingConstants.LEFT);
                       tblexcel.getColumnModel().getColumn(integerColumna).setCellRenderer(modelocentrar);  
                  }else if (iAlinear==2) {
                      DefaultTableCellRenderer modelocentrar = new DefaultTableCellRenderer();
                       modelocentrar.setHorizontalAlignment(SwingConstants.RIGHT);
                       tblexcel.getColumnModel().getColumn(integerColumna).setCellRenderer(modelocentrar);  
                  }else if (iAlinear==3) {
                       DefaultTableCellRenderer modelocentrar = new DefaultTableCellRenderer();
                       modelocentrar.setHorizontalAlignment(SwingConstants.CENTER);
                       tblexcel.getColumnModel().getColumn(integerColumna).setCellRenderer(modelocentrar);  
                  }
                  miAlineado.insertarAlineado(new Alinear(Encriptacion.Desencriptar(rs.getString("nombre")), Integer.parseInt(Encriptacion.Desencriptar(rs.getString("codalinea")))));
                  r=rs.next();

              }
               System.out.println(miAlineado.ListarAlineado());  
          }catch (Exception e){
              JOptionPane.showMessageDialog(null,"le dio un Error fatal "+e);
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
      
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Vista().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu bntNumber;
    private javax.swing.JMenu btnBold;
    private javax.swing.JMenu btnCopy;
    private javax.swing.JMenu btnFile;
    private javax.swing.JMenu btnHelp;
    private javax.swing.JMenu btnItalic;
    private javax.swing.JMenu btnOper;
    private javax.swing.JMenu btnPaste;
    private javax.swing.JMenu btnSize;
    private javax.swing.JMenu btnUnder;
    private javax.swing.JButton btnabrireli;
    private javax.swing.JComboBox<String> cmbcodigo;
    private javax.swing.JComboBox<String> cmbcol;
    private javax.swing.JComboBox<String> cmbfil;
    private javax.swing.JComboBox<String> cmblistado;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu5;
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
    private javax.swing.JTable tblexcel;
    private javax.swing.JTextField txtBarra;
    // End of variables declaration//GEN-END:variables
}
