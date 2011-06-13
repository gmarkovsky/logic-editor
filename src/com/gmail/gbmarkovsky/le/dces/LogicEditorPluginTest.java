package com.gmail.gbmarkovsky.le.dces;

import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import ru.ipo.dces.debug.PluginBox;
import ru.ipo.dces.utils.ZipUtils;
import ru.ipo.problemsapi.ZipProblem;

public class LogicEditorPluginTest {

    public static void main(String[] args) throws IOException {

        //Создадим задачу. Это будет zip файл
        String problemFileName = "xor.problem";
        //Все нужные файлы уже есть в каталоге problem_example, их остается только заархивировать
        ZipUtils.zip(
                new File("resources/xorproblem"), //архивируемый каталог
                new File(problemFileName) //файл, куда сохраняется архив
        );

        //создаем окно для проверки плагина
        PluginBox box = new PluginBox(
                LogicEditorPlugin.class, //класс плагина
                new ZipProblem(problemFileName) //Создаем задачу на основе файла
        );

        box.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        box.setVisible(true);
    }
}
