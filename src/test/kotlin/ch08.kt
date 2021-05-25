import com.google.gson.Gson
import kotlin.properties.Delegates

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalStateException


/**
 *
 * 위임이란 '어떤 메서드의 처리를 다른 인스턴스의 메서드에 맡긴다'는 의미
 *
 */

interface Dialable {
    fun dial(number: String): String
}

class Phone : Dialable {

    override fun dial(number: String) = "Dialing $number..."

}

interface Snappable {
    fun takePicture(): String
}

class Camera : Snappable {

    override fun takePicture(): String {
        return "Taking picture..."
    }
}


// SmartPhone 클래스를 포함한 객체에 위임
//class SmartPhone (
//    private val phone: Dialable = Phone(),
//    private val camera: Snappable = Camera()
//) : Dialable by phone, Snappable by camera
//
//class SmartPhoneTest {
//
//    private val smartPhone: SmartPhone = SmartPhone()
//
//    @Test
//    fun `Dialing delegates to internal phone`() {
//
//        assertEquals("Dialing 555-1234...",
//
//            smartPhone.dial("555-1234")
//        )
//
//    }
//
//    @Test
//    fun `Taking picture delegates to internal camera`() {
//
//        assertEquals("Taking picture...",
//            smartPhone.takePicture()
//        )
//
//    }
//
//}


// 값이 널이 될수 없게 만들기
// 문제 : 처음 접근이 일어나기 전에 값이 초기화되지 않았다면 예외를 던지고 싶을때
// 해법 : notNull 함수 이용

var shouldNotBeNull: String by Delegates.notNull<String>()


// 예제 8-8 notNull 대리자 동작 확인

class NotNullFuncTest {


    @Test
    fun `uninitialized value thorws exception`() {

        assertThrows<IllegalStateException> { shouldNotBeNull }

    }

    @Test
    fun `initialize value then retrieve it`() {

        shouldNotBeNull = "Hello, World!"
        assertDoesNotThrow { shouldNotBeNull }
        assertEquals("Hello, World!", shouldNotBeNull)

    }



}



// observable 변경 감지


// vetoable 변경의 적용 여부 결정할 때


// 8-11 observable과 vetoable 함수의 사용법
var watched: Int by Delegates.observable(1) {
    prop, old, new -> println("${prop.name} changed from $old to $new")
}

var checked: Int by Delegates.vetoable(0) {
    prop, old, new -> println("Trying to change ${prop.name} from $old to $new")
    new >= 0
}

// 8-12 watched 변수 테스트
//class WatchedTest {
//
//    @Test
//    fun `watched variable prints old and new values`() {
//        assertEquals(1, watched)
//        watched *= 2
//        assertEquals(2, watched)
//        watched *= 2
//        assertEquals(4, watched)
//    }
//
//
//}


data class Project(val map: MutableMap<String, Any?>) {
    val name: String by map
    var priority: Int by map
    var completed: Boolean by map
}



class Chapter08 {



    //8-12 watched 변수 테스트
    @Test
    fun `watched variable prints old and new values`() {
        assertEquals(1, watched)
        watched *= 2
        assertEquals(2, watched)
        watched *= 2
        assertEquals(4, watched)
    }

    //8-13 vetoable checked 변수 변경 테스트
    @Test
    fun `veto values less than zero`() {

        assertAll(
            { assertEquals(0, checked) },
            { checked = 42; assertEquals(42, checked)},
            { checked = 17; assertEquals(17, checked)}
        )

    }

    //대리자로서 Map 제공
    //문제 여러 값이 들어 있는 맵(map)을 제공해 객체를 초기화하고 싶음
    //해법 코틀린 맵에는 대리자가 되는 데 필요한 getValue와 setValue 함수 구현이 있음

    //예제 8-17 맵으로 Project 인스턴스 생성하기
    @Test
    fun `use map delegate for Project`() {

        val project = Project(
            mutableMapOf(

                "name" to "Learn Kotlin",
                "priority" to 5,
                "completed" to true


            )
        )

        assertAll(
            { assertEquals("Learn Kotlin", project.name) },
            { assertEquals(5, project.priority) },
            { assertTrue(project.completed) }
        )

    }

    private fun getMapFromJSON() =
        Gson().fromJson<MutableMap<String, Any?>>(
            """{ "name":"Learn Kotlin", "priority":5, "completed":true}""",
            MutableMap::class.java
        )


    @Test
    fun `create project from map parsed from JSON string`() {

        val project = Project(getMapFromJSON())
        assertAll(
            { assertEquals("Learn Kotlin", project.name )},
            { assertEquals(5, project.priority)},
            { assertTrue(project.completed) }
        )

    }


    @Test
    fun `todo test`() {

        val exception = assertThrows<NotImplementedError> {
            TODO("seriously, finish this")
        }

        assertEquals("An operation is not implemented: seriously, finish this",
        exception.message)

    }
}