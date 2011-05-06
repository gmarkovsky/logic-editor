package com.gmail.gbmarkovsky.le.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

import com.gmail.gbmarkovsky.le.circuit.Circuit;
import com.gmail.gbmarkovsky.le.io.CircuitLoadException;
import com.gmail.gbmarkovsky.le.io.CircuitSerializer;
import com.gmail.gbmarkovsky.le.views.CircuitView;

/**
 * Окно редактора логических схем.
 * @author george
 *
 */
public class LogicEditorFrame extends JFrame {
	private static final String TITLE = "Редактор логических схем";
	private static final long serialVersionUID = 8808280554536347790L;
	private static final int WIDTH = 750;
	private static final int HEIGHT = 500;
	
	private File currentDirectory;
	
	private CircuitEditorPanel editorPanel;
	
	public LogicEditorFrame() {
		setTitle(TITLE);
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		editorPanel = new CircuitEditorPanel();
		add(editorPanel);
		initMainMenu();
	}

	
	private void initMainMenu() {
		JMenuBar menuBar  = new JMenuBar();
		JMenu mFile = new JMenu("Файл");
		JMenu mEdit = new JMenu("Правка");
		JMenuItem miNew = new JMenuItem("Новый");
		miNew.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Circuit circuit = new Circuit();
				CircuitView circuitView = new CircuitView(circuit);
				editorPanel.getCircuitEditor().setCircuit(circuit);
				editorPanel.getCircuitEditor().setCircuitView(circuitView);
				editorPanel.getCircuitEditor().repaint();
				currentDirectory = null;
				LogicEditorFrame.this.setTitle(TITLE);
			}
		});
		JMenuItem miExit = new JMenuItem("Выход");
		miExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);				
			}
		});
		miExit.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_X, ActionEvent.ALT_MASK));
		
		JMenuItem miOpen = new JMenuItem("Открыть...");
		miOpen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				File file = null;
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.addChoosableFileFilter(new XmlFileFilter());
				fileChooser.setCurrentDirectory(currentDirectory);
				int returnVal = fileChooser.showOpenDialog(LogicEditorFrame.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					file = fileChooser.getSelectedFile();
					currentDirectory = file;
				} else {
					return;
				}
				FileInputStream fw = null;
				try {
					fw = new FileInputStream(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
				CircuitView circuitView = null;
				byte[] bytes = null;
				try {
					bytes = new byte[fw.available()];
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					fw.read(bytes);
				} catch (IOException e) {
					e.printStackTrace();
				}
				ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(bytes);
				try {
					circuitView = CircuitSerializer.parse(arrayInputStream);
				} catch (CircuitLoadException e) {
					JOptionPane.showMessageDialog(LogicEditorFrame.this,
							"Файл " + file.getName() + " имеет неверный формат.",
							"Ошибка", JOptionPane.ERROR_MESSAGE);
					return;
				}
				editorPanel.getCircuitEditor().setCircuit(circuitView.getCircuit());
				editorPanel.getCircuitEditor().setCircuitView(circuitView);
				editorPanel.getCircuitEditor().repaint();
			}
		});
		
		JMenuItem miSave = new JMenuItem("Сохранить");
		miSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				File file = null;
				if (currentDirectory == null) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.addChoosableFileFilter(new XmlFileFilter());
					fileChooser.setCurrentDirectory(currentDirectory);
					int returnVal = fileChooser.showSaveDialog(LogicEditorFrame.this);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						file = fileChooser.getSelectedFile();
						currentDirectory = file;
					} else {
						return;
					}
				} else {
					file = currentDirectory;
				}

				FileOutputStream fw = null;
				try {
					XmlFileFilter xmlFileFilter = new XmlFileFilter();
					if (xmlFileFilter.accept(file.getAbsoluteFile())) {
						fw = new FileOutputStream(file);
					} else {
						fw = new FileOutputStream(file.getAbsoluteFile() + ".xml");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					CircuitSerializer.write(editorPanel.getCircuitEditor().getCircuitView()).writeTo(fw);
				} catch (IOException e) {
					e.printStackTrace();
				}
				LogicEditorFrame.this.setTitle(currentDirectory.getAbsolutePath());
			}
		});
		
		JMenuItem miSaveAs = new JMenuItem("Сохранить как...");
		miSaveAs.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				File file = null;
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.addChoosableFileFilter(new XmlFileFilter());
				fileChooser.setCurrentDirectory(currentDirectory);
				int returnVal = fileChooser.showSaveDialog(LogicEditorFrame.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					file = fileChooser.getSelectedFile();
					currentDirectory = file;
				} else {
					return;
				}

				FileOutputStream fw = null;
				try {
					XmlFileFilter xmlFileFilter = new XmlFileFilter();
					if (xmlFileFilter.accept(file.getAbsoluteFile())) {
						fw = new FileOutputStream(file);
					} else {
						fw = new FileOutputStream(file.getAbsoluteFile() + ".xml");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					CircuitSerializer.write(editorPanel.getCircuitEditor().getCircuitView()).writeTo(fw);
				} catch (IOException e) {
					e.printStackTrace();
				}
				LogicEditorFrame.this.setTitle(currentDirectory.getAbsolutePath());
			}
		});
		mFile.add(miNew);
		mFile.add(miOpen);
		mFile.add(miSave);
		mFile.add(miSaveAs);
		mFile.add(new JSeparator());
		mFile.add(miExit);
		menuBar.add(mFile);
		
		JMenuItem miDelete = new JMenuItem("Удалить");
		miDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				editorPanel.getCircuitEditor().deleteSelectedElements();
				editorPanel.getCircuitEditor().deleteSelectedWire();
				editorPanel.getCircuitEditor().repaint();
			}
		});
		miDelete.setAccelerator(KeyStroke.getKeyStroke("DELETE"));
		mEdit.add(miDelete);
		menuBar.add(mEdit);
		
		setJMenuBar(menuBar);
	}
}

class XmlFileFilter extends FileFilter {

	@Override
	public boolean accept(File pathname) {
		if (pathname.isDirectory()) {
			return true;
		}
		
		String extension = Utils.getExtension(pathname);
		if (extension != null) {
			if (extension.equals(Utils.xml)) {
		        return true;
			} else {
				return false;
			}
		}
	    return false;
	}

	@Override
	public String getDescription() {
		return "xml files";
	}
}

class Utils {

    public final static String jpeg = "jpeg";
    public final static String jpg = "jpg";
    public final static String gif = "gif";
    public final static String tiff = "tiff";
    public final static String tif = "tif";
    public final static String png = "png";
    public final static String xml = "xml";
  
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}