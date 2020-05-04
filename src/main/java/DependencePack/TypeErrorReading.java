/**
 * Перечисление для более удобной работы с ошибками чтения из файла.
 * Должны использоваться для передачи более полной информации пользователю.
 */
package DependencePack;

public enum TypeErrorReading {
    NF("Файл не найден"),
    NR("Файл недоступен для чтения"),
    ER("Ошибка чтения файла"),
    ENF("Ошибка получения имени файла"),
    NE("Ошибок нет");

    private String stateError;

    TypeErrorReading(String stateError) {
        this.stateError = stateError;
    }

    public String getStateError() {
        return stateError;
    }
}
