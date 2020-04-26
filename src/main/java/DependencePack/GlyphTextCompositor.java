package DependencePack;

import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GlyphTextCompositor{

    private ExecutorService executorService;
    private AtomicInteger atomicInteger;

    public GlyphTextCompositor(){
        this.executorService = null;
        this.atomicInteger = new AtomicInteger(0);
    }

    public void compose(ArrayList<GlyphText> compositionListGlyph, final ArrayList<String> startListString) {

        executorService = Executors.newFixedThreadPool(3);

        while (atomicInteger.get() < startListString.size()) {
            Future<?> future = executorService.submit(new ReadWriteBlockStringTwo(atomicInteger, startListString, compositionListGlyph));
            try {
                future.get();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        executorService.shutdown();

        executorService = null;
        atomicInteger = null;
    }

    private boolean isDoneList(ArrayList<Future<?>> arrayList){
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).isDone()){
            } else {
                return false;
            }
        }
        return true;
    }
}
