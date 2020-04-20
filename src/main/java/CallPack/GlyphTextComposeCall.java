package CallPack;

import DependencePack.Glyph;
import DependencePack.GlyphTextCompositor;

import java.util.LinkedList;
import java.util.concurrent.Callable;

public class GlyphTextComposeCall implements Callable<LinkedList<Glyph>> {

    private LinkedList<String> linkedListStart;

    public GlyphTextComposeCall(LinkedList<String> linkedListStart) {
        this.linkedListStart = linkedListStart;
    }

    @Override
    public LinkedList<Glyph> call() throws Exception {
        LinkedList<Glyph> linkedListGlyph = new LinkedList<>();
        GlyphTextCompositor gtCompositor = new GlyphTextCompositor(linkedListStart);
        gtCompositor.compose(linkedListGlyph);
        return linkedListGlyph;
    }
}
