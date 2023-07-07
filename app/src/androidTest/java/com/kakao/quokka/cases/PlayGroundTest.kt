package com.kakao.quokka.cases

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kakao.quokka.ext.splitKey
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
            val prefs = mutableSetOf<String>().also { _set ->
                _set.add("a1||10000")
                _set.add("a2||20000")
                _set.add("a3||30000")
                _set.add("a4||40000")
                _set.add("a5||50000")
            }

            val value = "a3"

            val rr = prefs.map {
                val keySet = it.splitKey()
                val key = keySet.first
                val time = keySet.second

                if (key == value) {
                    "${value}||1000000000"
                } else {
                    "${key}||${time}"
                }
            }

            println("probe :: rr : ${rr}")


        }

        println("[TEST] probe : ==========================================================================================================================================")
    }


    companion object {
        fun String.splitKey(): Pair<String, Long> {
            return try {
                val keyArr = this.split("||")
                keyArr[0] to keyArr[1].toLong()
            } catch (e: IndexOutOfBoundsException) {
                "" to 0
            }
        }
    }

}