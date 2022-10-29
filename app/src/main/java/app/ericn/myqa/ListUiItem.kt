package app.ericn.myqa

sealed class ListUiItem(
) {
    data class Text(
        val question: String,
        val answer: String
    ): ListUiItem()
    data class MultiChoice(
        val question: QuestionEntity,
        val answers: List<AnswerEntity>,
        val options: List<OptionEntity>
    ): ListUiItem()
}