

interface Dialable {
    fun dial(number: String): String
}

class Phone : Dialable {

    override fun dial(number: String) = "Dialing $number..."

}

interface Snappable {
    fun takePicture(): String
}

