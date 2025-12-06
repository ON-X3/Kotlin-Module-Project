import java.util.Scanner

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
        if (archives.size > Int.MAX_VALUE-2) {
            println("Архивов слишком много. Невозможно создать больше")
            return
        }
        println("Введите название нового архива")
        var name = Scanner(System.`in`).nextLine()
        while (true) {
            if (name.isBlank()) {
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