package com.gmail.gbmarkovsky.le.dces;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ru.ipo.dces.exceptions.GeneralRequestFailureException;
import ru.ipo.dces.pluginapi.Plugin;
import ru.ipo.dces.pluginapi.PluginEnvironment;
import ru.ipo.dces.utils.ResultUtils;

import com.gmail.gbmarkovsky.le.exec.CircuitExecutor;
import com.gmail.gbmarkovsky.le.exec.Task;
import com.gmail.gbmarkovsky.le.gui.CircuitEditorPanel;
import com.gmail.gbmarkovsky.le.io.CircuitLoadException;
import com.gmail.gbmarkovsky.le.io.CircuitSerializer;
import com.gmail.gbmarkovsky.le.views.CircuitView;

public class LogicEditorPlugin extends JPanel implements Plugin {
	private static final long serialVersionUID = -4163685287000861846L;

	private final PluginEnvironment env;
	
	private final CircuitEditorPanel circuitEditorPanel;
	private JButton submit;
    
	public LogicEditorPlugin(final PluginEnvironment env) {
		super(new BorderLayout());
		this.env = env;
		this.circuitEditorPanel = new CircuitEditorPanel();
		submit = new JButton("Отправить решение");
		add(this.circuitEditorPanel, BorderLayout.CENTER);
		add(submit, BorderLayout.SOUTH);
		add(this.env.getStatementPanel(), BorderLayout.NORTH);
		submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
			       //Срабатывает при нажатии на кнопку

		        //убедиться, что участник действительно собирается отправить решение.
		        if (JOptionPane.showConfirmDialog(
		                LogicEditorPlugin.this,
		                "Вы уверены, что хотите отправить решение?",
		                "Отправка решения",
		                JOptionPane.YES_NO_OPTION
		        ) != JOptionPane.YES_OPTION)
		            return;

		        //TODO проверим ответ участника
		        ByteArrayOutputStream out = CircuitSerializer.write(circuitEditorPanel.getCircuitEditor().getCircuitView());
		        String string = out.toString();
		       
		        InputStream answer = env.getExtendedProblem().getReadStream("right answer");
		        
		        Task task = new Task(answer);
		        CircuitExecutor circuitExecutor = new CircuitExecutor(task);
						
		        boolean userIsRight = circuitExecutor.execute(circuitEditorPanel.getCircuitEditor().getCircuit());

		        try {
		        	env.submitSolution(
		                    //пользуемся утилитной функцией для создания HashMap:
		                    // "color" -> введенный цвет, "result" -> результат сравнения ответа с правильным
		                    ResultUtils.newMap(
		                            "schema", //цвет пользователя
		                            string,

		                            "answer", //ответ, посланный пользователем. Для демонстрационных целей используется answer вместо accepted
		                            userIsRight ? "1" : "0"
		                    )
		            );
		            JOptionPane.showMessageDialog(LogicEditorPlugin.this, "Решение отослано");
		        } catch (GeneralRequestFailureException e1) {
		            JOptionPane.showMessageDialog(LogicEditorPlugin.this, "Не удалось отправить решение");
		        }
			}
		});
		loadProblem();
	}

	@Override
	public void activate() {
		 System.out.println("Plugin activated");
	}

	@Override
	public void deactivate() {
		System.out.println("Plugin deactivated");
	}

	@Override
	public JPanel getPanel() {
		return this;
	}
	
	private void loadProblem() {
	   	InputStream schema = env.getExtendedProblem().getReadStream("start schema");
	   	CircuitView circuitView = null;
	   	try {
			circuitView = CircuitSerializer.parse(schema);
		} catch (CircuitLoadException e) {
			e.printStackTrace();
		}
		circuitView.getCircuit().addPropertyChangeListener(circuitView);
		circuitEditorPanel.getCircuitEditor().setCircuit(circuitView.getCircuit());
		circuitEditorPanel.getCircuitEditor().setCircuitView(circuitView);
		circuitEditorPanel.getCircuitEditor().repaint();
	}
}
