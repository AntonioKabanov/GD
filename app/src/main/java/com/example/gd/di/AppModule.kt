package com.example.gd.di

import com.example.gd.data.AdminRepositoryImpl
import com.example.gd.domain.repositories.AuthRepository
import com.example.gd.data.AuthRepositoryImpl
import com.example.gd.data.CategoryRepositoryImpl
import com.example.gd.data.UserRepositoryImpl
import com.example.gd.domain.repositories.AdminRepository
import com.example.gd.domain.repositories.CategoryRepository
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
}