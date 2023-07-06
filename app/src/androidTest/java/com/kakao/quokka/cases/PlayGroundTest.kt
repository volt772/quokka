package com.kakao.quokka.cases

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kakao.quokka.preference.PrefManager
import com.kakao.quokka.utils.TestCoroutineRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import javax.inject.Inject


@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ExperimentalCoroutinesApi
@HiltAndroidTest
class PlayGroundTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Inject
    lateinit var prefManager: PrefManager

    @Before
    fun before() {
        hiltRule.inject()
    }

    @After
    fun after() {
    }

    @Test
    fun test_01_add_shared_key() {

        runBlocking {
            val url = "https://search1.kakaocdn.net/argon/138x78_80_pr/AAAAAAAA"
//            prefManager.addStringSet(url)
        }

        println("[TEST] probe : ==========================================================================================================================================")
    }


    companion object

}