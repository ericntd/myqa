package app.ericn.myqa

sealed class ListUiItem(
    open val question: String
) {
    data class Text(
        override val question: String,
        val answer: String
    ): ListUiItem(question)
    data class MultiChoice(
        override val question: String,
        val answers: List<String>,
        val options: List<String>
    ): ListUiItem(question)
}