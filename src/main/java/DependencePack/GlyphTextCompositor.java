/**
 * Задача класса - построить ArrayList, другими словами композицию текстовых единиц из предоставляемых данных.
 */

package DependencePack;

import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GlyphTextCompositor{

    private ExecutorService executorService;    //сервис, предоставляющий потоки
    private AtomicInteger atomicInteger;    // Атомарное число- счётчик для разделения задач на части

    public GlyphTextCompositor(){
        this.executorService = Executors.newFixedThreadPool(3); //установка 3 потоков для работы
        this.atomicInteger = new AtomicInteger(0);  //установка счётчика в начальную позицию
    }

    /**
     * метод формируем GlyphText-единицу из String единицы и добавляет получившийся объект в ArrayList
     * @param compositionListGlyph  структура для хранения получившихся данных, т.е. GlyphText
     * @param startListString   структура, которая предоставляет данные, для создания GlyphText единиц
     */
    public void compose(ArrayList<GlyphText> compositionListGlyph, final ArrayList<String> startListString) {

        while (atomicInteger.get() < startListString.size()) {
            Future<?> future = executorService.submit(new ReadWriteBlockStringTwo(atomicInteger, startListString, compositionListGlyph));
            try {
                future.get();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        //по окончанию работы присваиваем null.
        executorService.shutdown();
        executorService = null;
        atomicInteger = null;
    }
}
