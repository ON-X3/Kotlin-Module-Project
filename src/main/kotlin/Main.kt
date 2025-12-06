
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
