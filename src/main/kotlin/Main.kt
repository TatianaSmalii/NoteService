import java.lang.RuntimeException
class ElemNotFoundException(message: String) : RuntimeException(message) {
}
fun main() {

    val note1 = Note(
        id = 1,
        title = "Заголовок 1",
        text = "Начало",
        date = 20230423,
        commentsCount = 0,
        readComments = 0,
        viewUrl = null,
        privacyView = null,
        canComment = false,
        textWiki = null
    )
    val note2 = Note(
        id = 2,
        title = "Заголовок 2",
        text = "Продолжение",
        date = 20230423,
        commentsCount = 0,
        readComments = 0,
        viewUrl = null,
        privacyView = null,
        canComment = false,
        textWiki = null
    )
    val note3 = Note(
        id = 3,
        title = "Заголовок 3",
        text = "Конец",
        date = 20230423,
        commentsCount = 0,
        readComments = 0,
        viewUrl = null,
        privacyView = null,
        canComment = false,
        textWiki = null
    )

    NoteService.addElem(note1)
    NoteService.addElem(note2)
    println(NoteService.getElem(null))
    NoteService.addElem(Comment(1, 1, 20230424, "Коммент_1"))
    //NoteService.createComment(1, "Коммент_2", 20230425)
    println(NoteService.getElem(null))
    println()
    println(NoteService.getElem(1))
    NoteService.deleteComment(1)
    println(NoteService.getElem(null))
    println()
    NoteService.restoreComment(1)
    println(NoteService.getElem(null))
    NoteService.deleteNote(1)
    println(NoteService.getElem(null))
    println(NoteService.getElem(1))

//    println(NoteService.getNoteById(1))
//    NoteService.deleteNote(1)
//    println(NoteService.getNotes())
//    NoteService.editNote(1, "Заметка редакция", "Изменение")
//    println(NoteService.getNotes())

}
object NoteService {
    private var notes = mutableListOf<Note>()
    private var comments = mutableListOf<Comment>()
    private var idNote = 1
    private var idComment = 1

    fun <T : ClassWithId> addElem(elem: T): Int {
        if (elem is Note) {
            notes += elem
            idNote++
            return elem.id
        } else if (elem is Comment) {
            val comment = Comment(idComment, elem.noteId, elem.date, elem.text)
            for (item in notes.listIterator()) {
                if (item.id == elem.noteId) {
                    comments += comment
                    idComment++
                    item.commentsCount++
                }
            }
            return comment.id
        }
        return -1
    }

//    fun createComment(noteId: Int, message: String, date: Int): Int {
//        for (item in notes.listIterator()) {
//            if (item.id == noteId) {
//                val comment = Comment(idComment, noteId, date, message)
//                comments.add(comment)
//                idComment++
//                item.commentsCount++
//                return comment.id
//            }
//        }
//        throw NoteNotFoundException("No note with id = $noteId")
//    }

    fun deleteNote(noteId: Int): Int {
        val toRemove = ArrayList<Comment>()
        for (item in notes.listIterator()) {
            if (item.id == noteId) {
                notes.removeAt(item.id - 1)
                for (item in comments.listIterator()) {
                    if (item.noteId == noteId) {
                        toRemove.add(item)
                    }
                }
                comments.removeAll(toRemove)
                return 1
            }
        }
        throw ElemNotFoundException("No element with id = $noteId")
    }

    fun deleteComment(commentId: Int): Int {
        for (item in comments.listIterator()) {
            if (item.id == commentId && item.visibility) {
                item.visibility = false
                //notes[item.noteId - 1].commentsCount--
                getNoteById(item.noteId).commentsCount--
                return 1
            }
        }
        throw ElemNotFoundException("No element with id = $commentId")
    }


//    fun <T : ClassWithId> edit(comNote: T): Int {
//        if (comNote is Note) {
//            for (item in notes.listIterator()) {
//                if (item.id == comNote.id) {
//                    item.title = comNote.title
//                    item.text = comNote.text
//                    return 1
//                }
//            }
//        } else if (comNote is Comment) {
//            for (item in comments.listIterator()) {
//                if (item.id == comNote.id && item.visibility) {
//                    item.text = comNote.text
//                    return 1
//                }
//            }
//        }
//        throw NoteNotFoundException("No note(comment) with id = ${comNote.id}")
//    }

    fun editNote(noteId: Int, title: String, text: String): Int {
        for (item in notes.listIterator()) {
            if (item.id == noteId) {
                item.title = title
                item.text = text
                return 1
            }
        }
        throw ElemNotFoundException("No element with id = $noteId")
    }

    fun editComment(commentId: Int, message: String): Int {
        for (item in comments.listIterator()) {
            if (item.id == commentId && item.visibility) {
                item.text = message
                return 1
            }
        }
        throw ElemNotFoundException("No element with id = $commentId")
    }

    fun getElem(id: Int?): MutableList<out ClassWithId> {
        return if (id == null) {
            notes
        } else {
            val result = ArrayList<Comment>()
            for (item in comments.listIterator()) {
                if (item.noteId == id && item.visibility) {
                    result += item
                }
            }
            result
        }
    }

//    fun getNotes(): MutableList<Note> {
//        return notes
//    }

    fun getNoteById(noteId: Int): Note {
        for (i in notes.indices) {
            if (notes[i].id == noteId)
                return notes[i]
        }
        throw ElemNotFoundException("No element with id = $noteId")
    }

//    fun getComments(noteId: Int): ArrayList<Comment> {
//        val result = ArrayList<Comment>()
//        for (item in comments.listIterator()) {
//            if (item.noteId == noteId && item.visibility) {
//                result.add(item)
//            }
//        }
//        return result
//    }

    fun restoreComment(commentId: Int): Boolean {
        for (item in comments.listIterator()) {
            if (item.id == commentId) {
                item.visibility = true
                notes[item.noteId - 1].commentsCount++
                return true
            }
        }
        return false
    }

    fun clear() {
        notes.removeAll(notes)
        comments.removeAll(comments)
        idNote = 1
        idComment = 1
    }
}
data class Note(
    override var id: Int,
    var title: String,
    var text: String,
    var date: Int,
    var commentsCount: Int,
    val readComments: Int,
    val viewUrl: String?,
    val privacyView: String?,
    val canComment: Boolean,
    val textWiki: String?
) : ClassWithId(id)

open class ClassWithId(open val id: Int)

data class Comment(
    override val id: Int,
    val noteId: Int,
    val date: Int,
    var text: String,
    var visibility: Boolean = true //  false - удален
) : ClassWithId(id)