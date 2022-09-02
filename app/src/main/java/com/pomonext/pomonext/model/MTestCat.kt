package com.pomonext.pomonext.model


data class MTestCat(
    var id: String? = null,
    var title: String? = null,
    var date: String? = null,
    var pageNumber: String = "0",
    var pomoCardsList: List<MPomoCard> = emptyList()
) {

}
