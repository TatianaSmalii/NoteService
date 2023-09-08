fun main() {
//    val note1 = Note(0, "Первая заметка", comments = true)
//    val note2 = Note(1, "Вторая заметка", 2, 2, 2, "0", "0")
//    val com1 = Comment(0, "Привет", "Татьяна", 1, true)
//    val com2 = Comment(1, "Пока", visibility =)


}

//Задача реализовать методы для заметок Note c help generic(это обобщение) и коллекций (mutableListOf)
interface Element {
    //создание интерфейса
    var id: Int
}

data class Note(//сама заметка
    override var id: Int,//переопределение id  с help generic
    val title: String,
    val text: String,
    val date: Int,
    val comments: MutableList<Comment>//=mutableListOf() //Уровень доступа комментирования к заметке
) : Element //два первых параметра в классе наследуются от интерфейса //первый generic

data class Comment(
    override var id: Int,
    val noteId: Int,
    val text: String,
    val date: Int,
    var isDelete: Boolean = true // false - удален
) : Element//два первых параметра в классе наследуются от интерфейса //второй generic

open class ParentService<T : Element>(
    val elements: MutableList<T>) {
    fun add(element: T): T {//добавление элемента
        elements += element
        return elements.last()
    }

    fun get(): MutableList<T> = elements //выдача массива с элементами
    fun edit(element: T): T {//отредактитровать элемент
        val editElement =
            elements.find { it.id == element.id } ?: throw ElementNotFindException("id - ${element.id} не найден")
        if (editElement is Comment && editElement.isDelete) {
            throw RuntimeException("Комментарий удален")
        }
        editElement.id = element.id
        return editElement
    }
    fun delete(element: Int): Boolean {
        val searchElement = elements.find { it.id == element } ?: return false
        return if (searchElement is Comment) {
            searchElement.isDelete = true
            true
        } else {
            elements.remove(searchElement)
        }
    }
    fun getById(element: Int): T {//верни id
        return elements.find { it.id == element } ?: throw ElementNotFindException("id - $element не найден")
    }
}

class CommentService (list: MutableList<Comment>): ParentService<Comment>(list){
    fun restoreComment (commentId : Int) : Boolean{//востановление записи
        val restoreElement = elements.find { it.id == commentId && it.isDelete} ?: return false
        restoreElement.isDelete = false
        return true
    }
}
object NoteService {
    private val noteService = ParentService<Note>(mutableListOf())
    fun clear() {noteService.elements.clear() }
    fun add(elem: Note) = noteService.add(elem)
    fun addComment(note: Note, comment: Comment) = CommentService(noteService.getById(note.id).comments).add(comment)
    fun delete(idNote: Int) = noteService.delete(idNote)
    fun edit(note: Note) = noteService.edit(note)
    // fun edit2(note: Note)  : Note {  return noteService.edit(note) }
    fun deleteComment(note: Note, comment: Comment) = CommentService(noteService.getById(note.id).comments).delete(comment.id)
    fun editComment(note: Note, comment: Comment) = CommentService(noteService.getById(note.id).comments).edit(comment)
    fun getNotes(): MutableList<Note> {return noteService.get()}
    fun getById(idNote: Int): Note {return noteService.getById(idNote)}
    fun getComments(idNote: Int): MutableList<Comment> {return noteService.getById(idNote).comments}
    fun restoreComment(idNote: Int, idComment: Int) = CommentService(noteService.getById(idNote).comments).restoreComment(idComment)
}
class ElementNotFindException(msg: String) : RuntimeException(msg)


