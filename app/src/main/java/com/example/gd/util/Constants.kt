package com.example.gd.util

object Constants {
    const val SPLASH_SCREEN_DURATION = 0L

    const val MAX_POST_DESCRIPTION_LINES = 3

    const val MIN_USERNAME_LENGTH = 3
    const val MIN_PASSWORD_LENGTH = 3

    // Database
    const val COLLECTION_NAME_USERS = "users"
    const val COLLECTION_NAME_CATEGORIES = "categories"
    const val COLLECTION_NAME_PRODUCTS = "products"
    const val COLLECTION_NAME_ORDERS = "orders"
    const val COLLECTION_NAME_FEEDBACKS = "feedbacks"
    const val COLLECTION_NAME_POINTS = "points"
    const val COLLECTION_NAME_SUPPORT = "support"

    // orders и favorites будут привязаны к пользователю
    // favId - ид пользователя (у каждого пользователя свой список понравившегося)
    // orderId -
    // еще с доставкой поработать

    //Roles
    const val ROLE_ADMIN = "Admin"
    const val ROLE_MANAGER = "Manager"
    const val ROLE_USER = "User"

    //OrderTypes
    const val GET_IN_POINT = "Самовывоз"
    const val GET_BY_DELIVERY = "Доставка"

    //OrderStatus
    const val ORDER_CREATED = "Создан"
    const val ORDER_ACCEPT = "Принят"
    const val ORDER_CANCEL = "Отменен"
    const val ORDER_FINISH = "Выполнен"

}