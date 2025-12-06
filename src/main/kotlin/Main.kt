import java.util.Scanner

fun main(args: Array<String>) {

    val archivesMenu = ArchivesMenu()
    val notesMenu = NotesMenu()
    val noteMenu = NoteMenu()

    archivesMenu.nextMenu = notesMenu
    notesMenu.nextMenu = noteMenu
    notesMenu.prevMenu = archivesMenu
    noteMenu.prevMenu = notesMenu

    archivesMenu.startMenu()
}

abstract class Menu {
    var onZeroItem: () -> Unit = {}
    var onLastItem: () -> Unit = {}
    var onBetweenItem: (i: Int) -> Unit = {}
    var menuItems: ArrayList<String> = arrayListOf()
    var menuTitle: String = ""
    var nextMenu: Menu? = null
    var prevMenu: Menu? = null

    fun startMenu() {
        println("$menuTitle:")
        showMenu()
        readInput()
    }

    fun showMenu() {

        for (i in menuItems.indices) {
            println("${i}. ${menuItems[i]}")
        }
    }

    fun readInput() {
        var userInput: String
        while (true) {
            userInput = Scanner(System.`in`).nextLine()
            if (!userInput.all { it.isDigit() }) {
                println("Необходимо вводить только цифры, соответствующие пункту меню")
                showMenu()
            } else if (userInput == "") {
                println("Команда не может быть пустой. Введите цифру, соответствующую пункту меню")
                showMenu()
            } else if (userInput.toInt() !in 0 until menuItems.size) {
                println("Такого пункта меню нет, выберите существующий пункт меню")
                showMenu()
            } else {
                selectItem(userInput, onZeroItem, onLastItem, onBetweenItem)
                return
            }
        }
    }

    fun selectItem(
        userInput: String, onZeroItem: () -> Unit,
        onLastItem: () -> Unit,
        onBetweenItem: (i: Int) -> Unit
    ) {
        when (userInput.toInt()) {
            0 -> onZeroItem()
            menuItems.size - 1 -> onLastItem()
            else -> onBetweenItem(userInput.toInt() - 1)
        }
    }
}

class ArchivesMenu : Menu() {
    var archives: ArrayList<Archive> = arrayListOf()

    init {
        menuItems.add("Создать архив")
        menuItems.add("Выход")
        menuTitle = "Список архивов"
        onZeroItem = {
            createArchive()
            startMenu()
        }
        onBetweenItem = { i ->
            (nextMenu as NotesMenu).currentArchive = archives[i]
            (nextMenu as NotesMenu).updateMenu()
            (nextMenu as NotesMenu).currentIndexOfArchive = i
            (nextMenu as NotesMenu).startMenu()

        }
    }

    fun createArchive() {
        println("Введите название нового архива")
        var name = Scanner(System.`in`).nextLine()
        while (true) {
            if (name == "") {
                println("Название архива не может быть пустым. Введите корректное название:")
                name = Scanner(System.`in`).nextLine()
            } else {
                archives.add(Archive(name))
                menuItems.add((menuItems.size - 1), name)
                return
            }
        }
    }
}

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
        println("Введите название заметки:")
        var text: String
        var name = Scanner(System.`in`).nextLine()
        while (true) {
            if (name == "") {
                println("Название заметки не может быть пустым. Введите корректное название:")
                name = Scanner(System.`in`).nextLine()
            } else {
                println("Введите текст заметки:")
                text = Scanner(System.`in`).nextLine()
                while (true) {
                    if (text == "") {
                        println("Текст заметки не может быть пустым. Введите корректное содержание:")
                        text = Scanner(System.`in`).nextLine()
                    } else {
                        currentArchive?.notes!!.add(Note(name, text))
                        println("заметка создана")
                        return
                    }
                }
            }
        }
    }
}

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

class Archive(val name: String) {
    var notes: ArrayList<Note> = arrayListOf()
}

class Note(val name: String, var text: String)
