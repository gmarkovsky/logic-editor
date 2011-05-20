package com.gmail.gbmarkovsky.le.app;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.gmail.gbmarkovsky.le.gui.LogicEditorFrame;

/**
 * Приложение для моделирования логических схем.
 * @author george
 *
 */
public class LogicEditor {
	public static void main(String[] args) {
		    try {
		        UIManager.setLookAndFeel(
		            UIManager.getSystemLookAndFeelClassName());
		    } 
		    catch (UnsupportedLookAndFeelException e) {
		       
		    }
		    catch (ClassNotFoundException e) {
		       
		    }
		    catch (InstantiationException e) {
		       
		    }
		    catch (IllegalAccessException e) {
		       
		    }
		
		LogicEditorFrame editorFrame = new LogicEditorFrame();
		editorFrame.setVisible(true);
	}
}
