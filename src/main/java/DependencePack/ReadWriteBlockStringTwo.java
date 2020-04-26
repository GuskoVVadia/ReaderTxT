package DependencePack;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ReadWriteBlockStringTwo implements Runnable{
    private static final int INNERLENGTH = 1_000;

    private int startI;
    private int maxI;
    private static ArrayList<String> startStringComposition;
    private static ArrayList<GlyphText> listGlyphs;

    public ReadWriteBlockStringTwo(AtomicInteger atomicInteger, ArrayList<String> startStringCompositionIn,
                                   ArrayList<GlyphText> listGlyphsIn){
        this.startI = atomicInteger.getAndAdd(INNERLENGTH);
        this.maxI = ((startI + INNERLENGTH) > startStringCompositionIn.size()) ? startStringCompositionIn.size() : (startI + INNERLENGTH);

        startStringComposition = startStringCompositionIn;
        listGlyphs = listGlyphsIn;
    }

    @Override
    public void run() {
        for (; startI < maxI; startI++){
            listGlyphs.add(startI, new GlyphText.Builder(startStringComposition.get(startI)).sizeFont(16).build());
        }
    }
}
