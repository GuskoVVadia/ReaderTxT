/**
 * Задача класса - формирование страницы, а именно:
 *  получение начальных данных, путём запуска класса-считывателя
 *  обработка первоначальных данныхиз файла и формирование текстовых единиц
 *  по возможности избавляться от старых ссылок и ненужных объектов.
 *  запуск класса-декоратора, для поиска слов 2 через 2 и изменения размера шрифта.
 *  запуск класса, ответственного за формирование строк для страницы - строковый композитор -
 *  объект, формирует и отдаёт строки, при обращении.
 *  наполнение содержимого окна, через VBox.
 */

package DependencePack;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;

public class PageComposition {

    private RowGlyphCompositor rowCompositor;   //класс, формирующий строки

    private int startViewRow;   //переменная начальной строки на странице
    private double widthApp;    //значение ширины окна
    private double heightApp;   //значение высоты окна

    private VBox BoxApp;    //блок-родитель - куда именно наполнять содержимое страницы

    /**
     * Конструктор класса
     * @param path путь к текстовому файлу
     * @param VBoxApp блок-родитель, куда наполнять страницу
     * @param width требуеммая ширина страницы
     * @param height требуемая высота страницы
     */
    public PageComposition(Path path, VBox VBoxApp, double width, double height){

        //получаем начальные данные из файла, инкапсулированные в ArrayList
        ArrayList<String> startListString = new UnitReadingFile().getSymbolComposition(path);
        //формируем ArrayList - структуру данных, с которой будем работать дальше
        ArrayList<GlyphText> arrayListGlyph = new ArrayList<>(startListString.size());

        //инициализация класса-построителя текстовых единиц
        GlyphTextCompositor gtCompositor = new GlyphTextCompositor();
        //передача для работы классу нчальных данных и сформированного списка текстовых единиц для наполнения
        gtCompositor.compose(arrayListGlyph, startListString);
        //после выполнения своих задач list и класс "подчищаем"
        clearListString(startListString);
        gtCompositor = null;

        //Декорирование глифов соответственно ТЗ;
        //Создаём класс, ответственный за поиск двух слов в списке и их декорирование
        //в конструктор передаём величину на которую нужно увеличить шрифт нужных слов
        new TwoWordDecorator(3.0).decorated(arrayListGlyph);

        this.startViewRow = 0;
        this.heightApp = height;
        this.widthApp = width;
        //создание объекта ответственного за формирование строк на странице
        //для инициализации класса передаём данные прошедшие наполнение и изменения, и ширину окна,
        //как значение для максимальной длинны строк
        this.rowCompositor = new RowGlyphCompositor(arrayListGlyph, width);

        this.BoxApp = VBoxApp;
        this.viewPage(width, height);
    }

    /**
     * Метод отображения страницы в окне приложения, путём получения строк и формирование их по высоте
     * @param width требуемая ширина страницы.
     * @param height требуемая высота страницы
     */
    public void viewPage(double width, double height){

        //создаём list для хранения строк. Строки храняться в контейнерах HBox, который выводит имеющиеся объекты
        //горизонтально.
        ArrayList<HBox> arrayListHbox = new ArrayList<>();
        double heightTemp = 0;  //счётчик высоты строки

        //цикл начинает просчёт/запрос/показ строк с переменной начальной строки
        //и пока высота наполняющихся строк меньше высоты сцены приложения - цикл работает
        for (int i = startViewRow; heightTemp < height; i++) {

            LinkedList<Canvas> listCanvas = new LinkedList<>();
            //выход из цикла по превышению количества строк
            if (!this.rowCompositor.getRow(i, listCanvas)){
                break;
            }
            double max = 0;

            //расчитываем высоту строки по высоте самого высокого элемента в строке
            for (int j = 0; j <listCanvas.size() ; j++) {
                max = Math.max(max, listCanvas.get(j).getHeight());
            }

            heightTemp += max;

            //создание объекта для формирования строк на странице
            HBox hBox = new HBox();
            //установка выравнивания  - низ и слева-направо
            hBox.setAlignment(Pos.BOTTOM_LEFT);
            //установка скомпонованной строки в объект Hbox
            hBox.getChildren().addAll(listCanvas);
            arrayListHbox.add(hBox);
        }

        //если обращение к объекту происходит не в первый раз, т.е. объект наполнен строками, то - очищаем
        if (BoxApp.getChildren().size() > 0){
            BoxApp.getChildren().clear();
        }
        //заполнение вертикального компоновщика горизонтальными строками
        BoxApp.getChildren().addAll(arrayListHbox);
        //очистка строк
        arrayListHbox.clear();
    }

    /**
     * метод для скроллинга стриницы вверх, используется слушателем клавиатуры
     */
    public void upRow(){
        if (this.startViewRow > 0){ //если начальная строка страницы не выходит за предел начала массива
            this.startViewRow -= 1;
        }
        this.viewPage(this.widthApp, this.heightApp);
    }

    /**
     * метод для скроллинга стриницы вниз, используется слушателем клавиатуры
     */
    public void downRow(){
        //если начальная строка показа страниц не перешла предел максимальной строки из имеющихся
        if (this.startViewRow < this.rowCompositor.getLength() - 1) {
            this.startViewRow += 1;
        }
        this.viewPage(this.widthApp, this.heightApp);
    }

    //очизаем list после работы с ним
    private void clearListString(ArrayList<String> listString){
        for (int i = 0; i < listString.size(); i++) {
            listString.set(i, null);
        }
        listString.clear();
        listString = null;
    }

    /**
     * метод для установки высоты и ширины сцены и сопутствующим действиям при изменении ширины-высота сцены
     * @param height высота
     * @param width ширина
     */
    public void setDimensionScene(double height, double width){
        this.heightApp = height;
        this.widthApp = width;
        this.rowCompositor.setWidthPage(width);
        viewPage(this.widthApp, this.heightApp);
    }
}
