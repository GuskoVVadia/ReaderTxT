/**
 * Класс - формирующий задачу для исполнителя
 */
package DependencePack;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ReadWriteBlockStringTwo implements Runnable{
    private static final int INNERLENGTH = 1_000;   //константа длинны блока

    private int startI; //переменная начальной точки
    private int maxI;   //переменная максимального значения
    private static ArrayList<String> startStringComposition;    //начальная страктура данных String
    private static ArrayList<GlyphText> listGlyphs; //list куда будут записаны получившиеся объекты

    /**
     * Коструктор класса
     * @param atomicInteger атомарная единица начальной точки отсчёта
     * @param startStringCompositionIn начальная страктура данных String
     * @param listGlyphsIn list куда будут записаны получившиеся объекты
     */
    public ReadWriteBlockStringTwo(AtomicInteger atomicInteger, ArrayList<String> startStringCompositionIn,
                                   ArrayList<GlyphText> listGlyphsIn){
        this.startI = atomicInteger.getAndAdd(INNERLENGTH);
        this.maxI = ((startI + INNERLENGTH) > startStringCompositionIn.size()) ? startStringCompositionIn.size() : (startI + INNERLENGTH);

        startStringComposition = startStringCompositionIn;
        listGlyphs = listGlyphsIn;
    }

    /**
     * метод, проходящийся по отрезку начального list данных, полученные объекты записывает в конечный list
     */
    @Override
    public void run() {
        for (; startI < maxI; startI++){
            listGlyphs.add(startI, new GlyphText.Builder(startStringComposition.get(startI)).sizeFont(16).build());
        }
    }
}
