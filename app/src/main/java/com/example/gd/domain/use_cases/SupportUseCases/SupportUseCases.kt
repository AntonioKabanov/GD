package com.example.gd.domain.use_cases.SupportUseCases

data class SupportUseCases(
    val getAllMessages: GetAllMessages,
    val getUserMessages: GetUserMessages,
    val sendAnswer: SendAnswer,
    val sendMessage: SendMessage
)
