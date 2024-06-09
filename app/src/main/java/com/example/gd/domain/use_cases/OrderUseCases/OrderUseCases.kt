package com.example.gd.domain.use_cases.OrderUseCases

data class OrderUseCases(
    val acceptOrder: AcceptOrder,
    val arrangeOrder: ArrangeOrder,
    val cancelOrder: CancelOrder,
    val getOrderList: GetOrderList,
    val getOrderListByUser: GetOrderListByUser
)
