package CallPack;

import DependencePack.UnitReadingFile;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.concurrent.Callable;

public class ReadingCall implements Callable<LinkedList<String>> {

    private Path pathFileCall;

    public ReadingCall(Path pathFileCall) {
        this.pathFileCall = pathFileCall;
    }

    @Override
    public LinkedList<String> call() throws Exception {
        return new UnitReadingFile().getSymbolComposition(this.pathFileCall);
    }
}
