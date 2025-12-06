import java.util.Scanner

class NoteMenu : Menu() {
    var currentNote: Note? = null

    init {
        menuTitle = "Заметка '${currentNote?.name}'"
        menuItems.add("Прочитать текущую заметку")
        menuItems.add("Добавить текст к текущей заметке")
        menuItems.add("Назад к выбору заметок")
        onZeroItem = {
            readNote()
            startMenu()
        }
        onBetweenItem = { i ->
            addText()
            startMenu()
        }
        onLastItem = { prevMenu?.startMenu() }
    }

    fun readNote() {
        println(currentNote?.text)
    }

    fun addText() {
        println("Введите добавляемый текст")
        val newText = Scanner(System.`in`).nextLine()
        currentNote?.text += "\n$newText"
    }

}