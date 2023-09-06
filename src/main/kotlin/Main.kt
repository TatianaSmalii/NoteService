fun main() {

}

//Задача реализовать методы для заметок Note c help generic(это обобщение) и коллекций (mutableListOf)
interface Element <T>{
    //создание интерфейса
    open val id: Int
}

data class Note<T>(//сама заметка
    override val id: Int,//переопределение id  с help generic
    val text: String,//переопределение id  с help generic
    val date: Int,
    val privacy: Int,//уровень приватности
    val comments: Int, //Уровень доступа комментирования к заметке
    val privacyView: String, //Настройки приватности просмотра заметки в спец формате
    val privacyComment: String// Настройки приватности комментирования заметки в спец.формате
) : Element<T> //два первых параметра в классе наследуются от интерфейса //первый generic

data class Comment<T>(
    override val id: Int,
    val text: String,
    val autorId: String,
    val date: Int
) : Element<T>//два первых параметра в классе наследуются от интерфейса //второй generic

object NoteService {
    private val notes = mutableListOf<Note<T>>()
    private val comments = mutableListOf<Comment>()
    private val noteId =0
    private val commentsId = 0

    //реализация методов
    fun addNote (Note: T):T {//добавление заметки
       else (note is Note){
           notes += note
            return note.id
        }
    }
    fun addComment() {}
    fun delete() {}
    fun deleteComment (val ){}
    fun edit (//редактирует заметку текущего пользователя){}
    fun editComment (//редактирует указанный комментарий у заметки){}
    fun getNote (//возвращает список заметок){}
    fun getById (//возвращает заметку по ее id){}
    fun getComments (//возвращает список комментариев к заметке){}
    fun restoreComment (//){}
}

