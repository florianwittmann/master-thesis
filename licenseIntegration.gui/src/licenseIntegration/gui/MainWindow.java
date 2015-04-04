package licenseIntegration.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import org.jvalue.licenseintegration.common.LicenseVectorJSONLoader;
import org.jvalue.licenseintegration.modell.IllegalLicenseCombinationException;
import org.jvalue.licenseintegration.modell.LicenseAttribute;
import org.jvalue.licenseintegration.modell.LicenseVector;
 
public class MainWindow implements ActionListener
{
	final static String lineSeparator = System.getProperty("line.separator");

	
	 JFrame mainWindow;
	 JMenuItem addLicenseMenuItem;
	 JFileChooser chooser;
	 JMenuItem resetLicensesMenuItem;
	 JLabel label;
	 
     JTable table = new JTable();
	 ArrayList<LicenseVector> licenseVectors = new ArrayList<LicenseVector>();
	 ArrayList<String> tableHeaders = new ArrayList<String>();
     public static void main(String[] args)
    {  
    	 new MainWindow();
 
    }
     
     public MainWindow() {
         mainWindow = new JFrame("LicenseIntegration");   
         mainWindow.setSize(700,500);
         //mainWindow.add(new JLabel("Beispiel JLabel"));

         JMenuBar menuBar = new JMenuBar();

         //Build the first menu.
         JMenu menu = new JMenu("Licenses");
         menu.setMnemonic(KeyEvent.VK_F);
         menuBar.add(menu);

         //a group of JMenuItems
         addLicenseMenuItem = new JMenuItem("Add",
                                  KeyEvent.VK_A);
         addLicenseMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                 KeyEvent.VK_A, ActionEvent.ALT_MASK));
         addLicenseMenuItem.addActionListener(this);
         menu.add(addLicenseMenuItem);
         
         resetLicensesMenuItem = new JMenuItem("Reset",
                 KeyEvent.VK_R);
         resetLicensesMenuItem.setAccelerator(KeyStroke.getKeyStroke(
        		 KeyEvent.VK_R, ActionEvent.ALT_MASK));
         resetLicensesMenuItem.addActionListener(this);
         menu.add(resetLicensesMenuItem);
         mainWindow.add(menuBar, BorderLayout.NORTH);
         
         label = new JLabel("No license yet. Add the first license to start.",SwingConstants.CENTER);
         
         table = new JTable(0,0);
		 table.setVisible(false);
                  
         table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
         JScrollPane scrollPane = new JScrollPane(table,  JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
         
         
         mainWindow.add(scrollPane, BorderLayout.CENTER);
         mainWindow.add(label, BorderLayout.SOUTH);

         table.setFillsViewportHeight(true);
         mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         mainWindow.setVisible(true);
         
         chooser = new JFileChooser();
     }

    private void redrawLicenseTable() {
    	StringBuilder error = null;
    	
    	 if(licenseVectors.size()==0) {
    		 table.setVisible(false);
    		 table.setModel(new DefaultTableModel(0,0));
    		 label.setVisible(true);

    		 return;
    	 } else {
    		 table.setVisible(true);
    		 label.setVisible(false);

    	 }
    	 int count = licenseVectors.size() == 1 ? 2 : licenseVectors.size()+2;
    	 DefaultTableModel tableModel = new DefaultTableModel(0,count) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
    	 };
    	 
    	 tableModel.setColumnCount(count);

    	 LicenseVector licenseVector = null;
    	 for(int i=0; i< count-1; i++) {
    		 LicenseVector colLicenseVector;
    		 if(i==licenseVectors.size()) {
        		 colLicenseVector = licenseVector;
    		 } else {
        		 colLicenseVector = licenseVectors.get(i);
        		 try {
					licenseVector = licenseVector==null ?
							 colLicenseVector : licenseVector.combineWith(colLicenseVector);
				} catch (IllegalLicenseCombinationException e) {
					
					error = new StringBuilder();
					
					
					error.append("Illegal license combination!" + lineSeparator);
					error.append(e.getMessage() + lineSeparator);

					if(i==1) {
						error.append("Conflicting: " + tableHeaders.get(i-1) + " and " +  tableHeaders.get(i)  + lineSeparator);
					} else {
						error.append("Successfully combined licenses " + 1 + "-" + i + " to following licensevector:" + lineSeparator);
						error.append(licenseVector + lineSeparator);
						error.append("Conflict in combining this with " + tableHeaders.get(i) + lineSeparator );
						
					}
					licenseVector = null;
				}
    		 }
    		 
    		 ArrayList<LicenseAttribute> attributes = new ArrayList<LicenseAttribute>();
    		 if(colLicenseVector != null) 
    			 attributes =colLicenseVector.getAttributes();
    		 
    		 for(int j = 0; j<attributes.size(); j++) {
    			 int tableRow = getOrCreateTableRowFor(tableModel, attributes.get(j));
    	    	 tableModel.setValueAt(attributes.get(j).getValue().toString(), tableRow, i+1);
    		 }
    	 }
    	 
    	 
    	 table.setModel(tableModel);
         JTableHeader th = table.getTableHeader();
         TableColumnModel tcm = th.getColumnModel();
         th.setReorderingAllowed(false);
    	 TableColumnModel colModel = table.getColumnModel();
    	 for(int i=0; i<count;i++) {
    		 colModel.getColumn(i).setPreferredWidth(150);
    		 
    		 String headerValue;
    		 if(i==0) {
    			 headerValue="Attribute";
    		 } else if(count > 2 && i==count-1) {
    			 headerValue="Combined result";
    		 } else {
    			 headerValue = tableHeaders.get(i-1);
    		 }
    		 tcm.getColumn(i).setHeaderValue(headerValue);
    	 }
         th.repaint();
         if(error!=null)
				JOptionPane.showMessageDialog(mainWindow, error.toString());
    }
     
     
     
	private int getOrCreateTableRowFor(DefaultTableModel tableModel, LicenseAttribute licenseAttribute) {
		
		for(int i=0; i< tableModel.getRowCount(); i++) {
			if(tableModel.getValueAt(i, 0).toString().equals(licenseAttribute.getName())) {
				return i;
			}
		}
		int rowCount = tableModel.getRowCount();
		tableModel.setRowCount(rowCount+1);
		tableModel.setValueAt(licenseAttribute.getName(), rowCount, 0);
		return rowCount;
		
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {

        if (event.getSource() == addLicenseMenuItem){
            int returnVal = chooser.showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                	tableHeaders.add(chooser.getSelectedFile().getName());
                    addLicense(chooser.getSelectedFile().getAbsolutePath());
         			redrawLicenseTable();

            }       
         } else if (event.getSource() == resetLicensesMenuItem){
        	 licenseVectors.clear();
        	 tableHeaders.clear();
  			 redrawLicenseTable();

          }
		
	}

	private void addLicense(String filePath) {
		try {
			LicenseVector vector = new LicenseVectorJSONLoader().loadLicenseVectorFromFile(filePath);
			licenseVectors.add(vector);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}