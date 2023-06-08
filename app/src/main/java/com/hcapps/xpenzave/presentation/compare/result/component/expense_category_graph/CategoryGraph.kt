package com.hcapps.xpenzave.presentation.compare.result.component.expense_category_graph

data class CategoryGraph(
    val name: String,
    val percentage: Int
) {
    companion object {
        fun defaultCategoryGraphs() = listOf(
            CategoryGraph(name ="Total", percentage =  100),
            CategoryGraph(name ="Bills", percentage =  80),
            CategoryGraph(name ="Food", percentage =  60),
            CategoryGraph(name ="Clothes", percentage =  70),
            CategoryGraph(name ="Transport", percentage =  70),
            CategoryGraph(name ="Fun", percentage =  60),
            CategoryGraph(name ="Other", percentage =  80),
        )
    }
}
