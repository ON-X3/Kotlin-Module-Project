import java.util.Scanner

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
            when {
                (!userInput.all { it.isDigit() }) -> {
                        println("Необходимо вводить только цифры, соответствующие пункту меню")
                        showMenu()
                    }

                (userInput.isEmpty()) -> {
                    println("Команда не может быть пустой. Введите цифру, соответствующую пункту меню")
                    showMenu()
                }

                (userInput.length > 10 || userInput.toLong() > Int.MAX_VALUE) -> {
                    println("Введенное значение слишком большое. Выберите существующий пункт меню")
                    showMenu()
                }

                (userInput.toInt() !in 0 until menuItems.size) -> {
                    println("Такого пункта меню нет, выберите существующий пункт меню")
                    showMenu()
                }

                else -> {
                    selectItem(userInput, onZeroItem, onLastItem, onBetweenItem)
                    return
                }
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