package com.andrev133.habittracker

enum class TypeEnum {
    BAD,
    GOOD,
    NEUTRAL
}

fun TypeEnum.toResRadioBtn() = when (this) {
    TypeEnum.BAD -> R.id.habitEditorTypeBad
    TypeEnum.GOOD -> R.id.habitEditorTypeGood
    TypeEnum.NEUTRAL -> R.id.habitEditorTypeNeutral
}

fun TypeEnum.toResString() = when (this) {
    TypeEnum.BAD -> R.string.bad_habit
    TypeEnum.GOOD -> R.string.good_habit
    TypeEnum.NEUTRAL -> R.string.neutral_habit
}

fun Int.toTypeEnum() = when (this) {
    R.id.habitEditorTypeBad -> TypeEnum.BAD
    R.id.habitEditorTypeGood -> TypeEnum.GOOD
    R.id.habitEditorTypeNeutral -> TypeEnum.NEUTRAL
    else -> throw Exception("Unknown Type")
}