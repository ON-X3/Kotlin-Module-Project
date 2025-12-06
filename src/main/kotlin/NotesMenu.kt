import java.util.Scanner

class NotesMenu : Menu() {
    var currentIndexOfArchive = 0
    var currentArchive: Archive? = null

    init {
        onZeroItem = {
            createNote()
            updateMenu()
            startMenu()
        }
        onBetweenItem = { i ->
            (nextMenu as NoteMenu).currentNote = currentArchive!!.notes[i]
            (nextMenu as NoteMenu).menuTitle = "Заметка '${currentArchive!!.notes[i].name}'"
            (nextMenu as NoteMenu).startMenu()
        }
        onLastItem = { prevMenu?.startMenu() }
    }

    fun updateMenu() {
        menuTitle = "Архив '${currentArchive?.name}'. Список заметок"
        menuItems.clear()
        menuItems.add("Создать заметку")
        if (currentArchive?.notes!!.size > 0) {
            for (note in currentArchive!!.notes) {
                menuItems.add(note.name)
            }
        }
        menuItems.add("Назад к выбору архивов")
    }

    fun createNote() {
        if (currentArchive!!.notes.size > Int.MAX_VALUE-2) {
            println("Заметок в архиве слишком много. Невозможно создать больше")
            return
        }

        println("Введите название заметки:")
        var text: String
        var name = Scanner(System.`in`).nextLine()
        while (true) {
            if (name.trim().isEmpty()) {
                println("Название заметки не может быть пустым. Введите корректное название:")
                name = Scanner(System.`in`).nextLine()
            } else {
                println("Введите текст заметки:")
                text = Scanner(System.`in`).nextLine()
                while (true) {
                    if (text.trim().isEmpty()) {
                        println("Текст заметки не может быть пустым. Введите корректное содержание:")
                        text = Scanner(System.`in`).nextLine()
                    } else {
                        currentArchive?.notes!!.add(Note(name, text))
                        return
                    }
                }
            }
        }
    }
}