package CallPack;

import DependencePack.Glyph;
import DependencePack.TwoWordDecorator;

import java.util.LinkedList;
import java.util.concurrent.Callable;

public class TwoWordDecorCall implements Callable<LinkedList<Glyph>> {

    private LinkedList<Glyph> linkedListGlyph;

    public TwoWordDecorCall(LinkedList<Glyph> linkedListGlyph) {
        this.linkedListGlyph = linkedListGlyph;
    }

    @Override
    public LinkedList<Glyph> call() throws Exception {
        TwoWordDecorator twDecorator = new TwoWordDecorator();
        twDecorator.setComposition(this.linkedListGlyph);
        twDecorator.decorated();
        return linkedListGlyph;
    }
}
