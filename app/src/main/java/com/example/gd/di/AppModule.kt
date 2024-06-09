package com.example.gd.di

import com.example.gd.data.AdminRepositoryImpl
import com.example.gd.domain.repositories.AuthRepository
import com.example.gd.data.AuthRepositoryImpl
import com.example.gd.data.CategoryRepositoryImpl
import com.example.gd.data.OrderRepositoryImpl
import com.example.gd.data.PointRepositoryImpl
import com.example.gd.data.ProductRepositoryImpl
import com.example.gd.data.SupportRepositoryImpl
import com.example.gd.data.UserRepositoryImpl
import com.example.gd.domain.repositories.AdminRepository
import com.example.gd.domain.repositories.CategoryRepository
import com.example.gd.domain.repositories.OrderRepository
import com.example.gd.domain.repositories.PointRepository
import com.example.gd.domain.repositories.ProductRepository
import com.example.gd.domain.repositories.SupportRepository
import com.example.gd.domain.repositories.UserRepository
import com.example.gd.domain.use_cases.AdminUseCases.AdminUseCases
import com.example.gd.domain.use_cases.AdminUseCases.EditAnotherUser
import com.example.gd.domain.use_cases.AuthUseCases.AuthenticationUseCases
import com.example.gd.domain.use_cases.AuthUseCases.FirebaseAuthState
import com.example.gd.domain.use_cases.AuthUseCases.FirebaseDeleteUser
import com.example.gd.domain.use_cases.AuthUseCases.FirebaseSignIn
import com.example.gd.domain.use_cases.AuthUseCases.FirebaseSignOut
import com.example.gd.domain.use_cases.AuthUseCases.FirebaseSignUp
import com.example.gd.domain.use_cases.AuthUseCases.IsUserAuthenticated
import com.example.gd.domain.use_cases.CategoryUseCases.AddNewCategory
import com.example.gd.domain.use_cases.CategoryUseCases.CategoryUseCases
import com.example.gd.domain.use_cases.CategoryUseCases.DeleteCategory
import com.example.gd.domain.use_cases.CategoryUseCases.GetCategoryList
import com.example.gd.domain.use_cases.OrderUseCases.AcceptOrder
import com.example.gd.domain.use_cases.OrderUseCases.ArrangeOrder
import com.example.gd.domain.use_cases.OrderUseCases.CancelOrder
import com.example.gd.domain.use_cases.OrderUseCases.GetOrderList
import com.example.gd.domain.use_cases.OrderUseCases.GetOrderListByUser
import com.example.gd.domain.use_cases.OrderUseCases.OrderUseCases
import com.example.gd.domain.use_cases.PointUseCases.GetPoint
import com.example.gd.domain.use_cases.PointUseCases.GetPointList
import com.example.gd.domain.use_cases.PointUseCases.PointUseCases
import com.example.gd.domain.use_cases.PointUseCases.SetPoint
import com.example.gd.domain.use_cases.ProductUseCases.AddProduct
import com.example.gd.domain.use_cases.ProductUseCases.AddProductInFavorite
import com.example.gd.domain.use_cases.ProductUseCases.AddProductInOrder
import com.example.gd.domain.use_cases.ProductUseCases.DeleteFromOrder
import com.example.gd.domain.use_cases.ProductUseCases.DeleteProduct
import com.example.gd.domain.use_cases.ProductUseCases.GetFavoriteById
import com.example.gd.domain.use_cases.ProductUseCases.GetOrderById
import com.example.gd.domain.use_cases.ProductUseCases.GetProductList
import com.example.gd.domain.use_cases.ProductUseCases.GetProductsByCategory
import com.example.gd.domain.use_cases.ProductUseCases.ProductUseCases
import com.example.gd.domain.use_cases.SupportUseCases.GetAllMessages
import com.example.gd.domain.use_cases.SupportUseCases.GetUserMessages
import com.example.gd.domain.use_cases.SupportUseCases.SendAnswer
import com.example.gd.domain.use_cases.SupportUseCases.SendMessage
import com.example.gd.domain.use_cases.SupportUseCases.SupportUseCases
import com.example.gd.domain.use_cases.UserUseCases.GetUserDetails
import com.example.gd.domain.use_cases.UserUseCases.SetUserDetails
import com.example.gd.domain.use_cases.UserUseCases.UserUseCases
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesFirebaseFirestore() = FirebaseFirestore.getInstance()

//    @Provides
//    @Singleton
//    fun providesFirebaseStorage() = FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun providesAuthRepository(firebaseAuth: FirebaseAuth, database: FirebaseFirestore): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth, database)
    }

    @Provides
    @Singleton
    fun provideAuthUseCases(repository: AuthRepository) = AuthenticationUseCases(
        isUserAuthenticated = IsUserAuthenticated(repository = repository),
        firebaseAuthState = FirebaseAuthState(repository = repository),
        firebaseSignIn = FirebaseSignIn(repository = repository),
        firebaseSignOut = FirebaseSignOut(repository = repository),
        firebaseSignUp = FirebaseSignUp(repository = repository),
        firebaseDeleteUser = FirebaseDeleteUser(repository = repository)
    )

    @Provides
    @Singleton
    fun provideUserRepository(database: FirebaseFirestore): UserRepository {
        return UserRepositoryImpl(database = database)
    }

    @Provides
    @Singleton
    fun provideUserUseCases(repository: UserRepository) = UserUseCases(
        getUserDetails = GetUserDetails(repository = repository),
        setUserDetails = SetUserDetails(repository = repository)
    )

    @Provides
    @Singleton
    fun provideAdminRepository(database: FirebaseFirestore): AdminRepository {
        return AdminRepositoryImpl(database = database)
    }

    @Provides
    @Singleton
    fun provideAdminUseCases(repository: AdminRepository) = AdminUseCases(
        editAnotherUser = EditAnotherUser(repository = repository)
    )

    @Provides
    @Singleton
    fun provideCategoryRepository(database: FirebaseFirestore): CategoryRepository {
        return CategoryRepositoryImpl(database = database)
    }

    @Provides
    @Singleton
    fun provideCategoryUseCases(repository: CategoryRepository) = CategoryUseCases(
        addNewCategory = AddNewCategory(repository = repository),
        deleteCategory = DeleteCategory(repository = repository),
        getCategoryList = GetCategoryList(repository = repository)
    )

    @Provides
    @Singleton
    fun provideProductRepository(database: FirebaseFirestore): ProductRepository {
        return ProductRepositoryImpl(database = database)
    }

    @Provides
    @Singleton
    fun provideProductUseCases(repository: ProductRepository) = ProductUseCases(
        addProduct = AddProduct(repository = repository),
        addProductInFavorite = AddProductInFavorite(repository = repository),
        addProductInOrder = AddProductInOrder(repository = repository),
        deleteProduct = DeleteProduct(repository = repository),
        getFavoriteById = GetFavoriteById(repository = repository),
        getOrderById = GetOrderById(repository = repository),
        getProductList = GetProductList(repository = repository),
        getProductsByCategory = GetProductsByCategory(repository = repository),
        deleteFromOrder = DeleteFromOrder(repository = repository)
    )

    @Provides
    @Singleton
    fun provideOrderRepository(database: FirebaseFirestore): OrderRepository {
        return OrderRepositoryImpl(database = database)
    }

    @Provides
    @Singleton
    fun provideOrderUseCases(repository: OrderRepository) = OrderUseCases(
        getOrderList = GetOrderList(repository = repository),
        arrangeOrder = ArrangeOrder(repository = repository),
        acceptOrder = AcceptOrder(repository = repository),
        cancelOrder = CancelOrder(repository = repository),
        getOrderListByUser = GetOrderListByUser(repository = repository)
    )

    @Provides
    @Singleton
    fun providePointRepository(database: FirebaseFirestore): PointRepository {
        return PointRepositoryImpl(database = database)
    }

    @Provides
    @Singleton
    fun providePointUseCases(repository: PointRepository) = PointUseCases(
        getPointList = GetPointList(repository = repository),
        setPoint = SetPoint(repository = repository),
        getPoint = GetPoint(repository = repository)
    )

    @Provides
    @Singleton
    fun provideSupportRepository(database: FirebaseFirestore): SupportRepository {
        return SupportRepositoryImpl(database = database)
    }

    @Provides
    @Singleton
    fun provideSupportUseCases(repository: SupportRepository) = SupportUseCases(
        getAllMessages = GetAllMessages(repository = repository),
        getUserMessages = GetUserMessages(repository = repository),
        sendMessage = SendMessage(repository = repository),
        sendAnswer = SendAnswer(repository = repository)
    )
}