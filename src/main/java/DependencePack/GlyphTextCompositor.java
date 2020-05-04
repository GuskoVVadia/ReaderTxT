/**
 * Задача класса - построить ArrayList, другими словами композицию текстовых единиц из предоставляемых данных.
 */

package DependencePack;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class GlyphTextCompositor {

    public GlyphTextCompositor(){
    }

    public void compose(ArrayList<GlyphText> compositionListGlyph, ArrayList<String> startListString) {

        for (int i = 0; i < startListString.size(); i++) {
            compositionListGlyph.add(new GlyphText.Builder(startListString.get(i)).sizeFont(16).build());
        }
    }
}
