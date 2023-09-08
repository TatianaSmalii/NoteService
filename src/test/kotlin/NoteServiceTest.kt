import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class NoteServiceTest {
    private val note1 = Note(
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
    private val note2 = Note(
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
    private val note3 = Note(
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
    private val comment1 = Comment(noteId = 1, id = 1, text = "Коммент_1", date = 20230424)

    @Before
    fun clearBeforeTest() {
        NoteService.clear()
    }

    @Test
    fun addNote() {
        val result = NoteService.addElem(note1)
        kotlin.test.assertEquals(1, result)
    }

    @Test
    fun addComment() {
        val result = NoteService.addElem(comment1)
        kotlin.test.assertEquals(1, result)
    }
//    @Test
//    fun createComment() {
//        NoteService.addNote(note1)
//        val result = NoteService.createComment(1, "Коммент_1", 20230424)
//        kotlin.test.assertEquals(1, result)
//    }

//    @Test(expected = NoteNotFoundException::class)
//    fun shouldThrowComment() {
//        NoteService.addNote(note1)
//        NoteService.addNote(note2)
//        NoteService.createComment(5, "Коммент_1", 20230424)
//    }

    @Test
    fun deleteNote() {
        val result = NoteService.addElem(note1)
        NoteService.addElem(note2)
        NoteService.deleteNote(2)
        kotlin.test.assertEquals(1, result)
    }

    @Test(expected = ElemNotFoundException::class)
    fun shouldThrowDeleteNode() {
        NoteService.addElem(note1)
        NoteService.addElem(note2)
        NoteService.deleteNote(5)
    }

    @Test
    fun deleteCommentTrue() {
        NoteService.addElem(note1)
        NoteService.addElem(comment1)
        val result = NoteService.deleteComment(1)
        kotlin.test.assertEquals(1, result)

    }

    @Test(expected = ElemNotFoundException::class)
    fun deleteCommentFalse() {
        NoteService.addElem(note1)
        NoteService.addElem(comment1)
        val result = NoteService.deleteComment(3)
    }

    @Test
    fun editNote() {
        NoteService.addElem(note1)
        val result = NoteService.editNote(1, "Title edit", "Text edit")
        kotlin.test.assertEquals(1, result)
    }

    @Test(expected = ElemNotFoundException::class)
    fun shouldThrowEditNode() {
        NoteService.addElem(note1)
        NoteService.addElem(note2)
        NoteService.editNote(5, "title", "text")
    }

    @Test
    fun editCommentTrue() {
        NoteService.addElem(note1)
        NoteService.addElem(comment1)
        val result = NoteService.editComment(1, "Comment edit")
        kotlin.test.assertEquals(1, result)
    }

    @Test(expected = ElemNotFoundException::class)
    fun editCommentFalse() {
        NoteService.addElem(note1)
        NoteService.addElem(comment1)
        val result = NoteService.editComment(3, "Comment edit")
    }

    @Test
    fun getNoteById() {
        NoteService.addElem(note1)
        NoteService.addElem(note2)
        val result = NoteService.getNoteById(2)
        kotlin.test.assertEquals(note2, result)
    }

    @Test(expected = ElemNotFoundException::class)
    fun shouldThrowGetNoteById() {
        NoteService.addElem(note1)
        NoteService.getNoteById(2)
    }

    @Test
    fun restoreCommentTrue() {
        NoteService.addElem(note1)
        val commentId = NoteService.addElem(comment1)
        val result = NoteService.restoreComment(commentId)
        kotlin.test.assertTrue(result)
    }

    @Test
    fun restoreCommentFalse() {
        NoteService.addElem(note1)
        val commentId = NoteService.addElem(comment1)
        val result = NoteService.restoreComment(3)
        kotlin.test.assertFalse(result)
    }
}