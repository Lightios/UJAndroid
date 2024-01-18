package com.example.testy

import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.hasNoClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.testy.MainContent
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CalculatorTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        composeTestRule.setContent {
            MainContent()
        }
    }

    @After
    fun clear() {
        click("C")
    }

    private fun click(text: String) {
        composeTestRule.onNodeWithText(text).performClick()
    }

    fun assertNodeExistsAndNotClickable(text: String) {

        composeTestRule.onNode(
            hasText(text)
                    ,
//            and
//            hasNoClickAction(),
            useUnmergedTree = true
        ).apply {
            assertExists()
            assertHasNoClickAction()
            assertIsEnabled()
            assertIsDisplayed()
        }
    }

    @Test
    fun testNumber() {
        click("1")
        click("2")
        click("3")

        assertNodeExistsAndNotClickable("123.0")
    }

    @Test
    fun testAdd() {
        click("1")
        click("+")
        click("2")
        click("0")
        click("=")

        assertNodeExistsAndNotClickable("21.0")
    }

    @Test
    fun testSubtract() {
        click("1")
        click("-")
        click("2")
        click("=")

        assertNodeExistsAndNotClickable("-1.0")
    }

    @Test
    fun testMultiply() {
        click("2")
        click("*")
        click("3")
        click("=")

        assertNodeExistsAndNotClickable("6")
    }

    @Test
    fun testDivide() {
        click("6")
        click("/")
        click("2")
        click("=")

        assertNodeExistsAndNotClickable("3")
    }

    @Test
    fun testLog() {
        click("1")
        click("0")
        click("log")

        assertNodeExistsAndNotClickable("1")
    }

    @Test
    fun testPlusMinus() {
        click("1")
        click("0")
        click("+-")

        assertNodeExistsAndNotClickable("-10.0")
    }

    @Test
    fun testClear() {
        click("1")
        click("0")
        click("C")
        click("1")
        click("0")

        assertNodeExistsAndNotClickable("10.0")
    }


    @Test
    fun testSquare() {
        click("6")
        click("square")

        assertNodeExistsAndNotClickable("36.0")
    }

    @Test
    fun testPercent() {
        click("1")
        click("0")
        click("%")

        assertNodeExistsAndNotClickable("0.1")
    }

    @Test
    fun testHardMath() {
        click("2")
        click("+")
        click("2")
        click("=")

        assertNodeExistsAndNotClickable("4.0")

        click("*")
        click("2")
        click("=")

        assertNodeExistsAndNotClickable("8.0")

        click("/")
        click("2")
        click("=")

        assertNodeExistsAndNotClickable("4.0")

        click("-")
        click("2")
        click("=")

        assertNodeExistsAndNotClickable("2.0")

        click("square")

        assertNodeExistsAndNotClickable("4.0")

        click("*")
        click("5")
        click("=")

        assertNodeExistsAndNotClickable("20.0")

        click("%")

        assertNodeExistsAndNotClickable("0.2")

        click("*")
        click("5")
        click("0")
        click("=")

        assertNodeExistsAndNotClickable("10.0")

        click("log")
        assertNodeExistsAndNotClickable("1.0")
    }
}