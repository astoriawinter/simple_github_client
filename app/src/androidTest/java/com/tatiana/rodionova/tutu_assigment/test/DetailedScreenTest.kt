package com.tatiana.rodionova.tutu_assigment.test

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.tatiana.rodionova.presentation.main.MainActivity
import com.tatiana.rodionova.tutu_assigment.scenario.Scenarios
import com.tatiana.rodionova.tutu_assigment.screen.DetailedScreen
import com.tatiana.rodionova.tutu_assigment.screen.ListScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class DetailedScreenTest : TestCase() {
    @get:Rule
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testFlutterRepositoryHasGitattributes() {
        run {
            scenario(Scenarios.ClickOnFlutterRepositoryScenario())

            DetailedScreen.list {
                isVisible()
                childAt<DetailedScreen.TreeItem>(2) {
                    fileName.hasText(".gitattributes")
                }
            }
        }
    }
}

