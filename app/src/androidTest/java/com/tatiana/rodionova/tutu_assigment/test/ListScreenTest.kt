package com.tatiana.rodionova.tutu_assigment.test

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.tatiana.rodionova.presentation.main.MainActivity
import com.tatiana.rodionova.tutu_assigment.scenario.Scenarios
import com.tatiana.rodionova.tutu_assigment.screen.ListScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class ListScreenTest : TestCase() {
    @get:Rule
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testGithubRepositoryListIsLoaded() {
        run {
            scenario(Scenarios.LoadListScenario())
            ListScreen.list {
                childAt<ListScreen.GithubItem>(0) {
                    name.hasText("flutter/flutter")
                }
            }
        }
    }
}

