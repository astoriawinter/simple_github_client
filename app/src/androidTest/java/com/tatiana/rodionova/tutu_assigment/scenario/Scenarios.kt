package com.tatiana.rodionova.tutu_assigment.scenario

import com.kaspersky.kaspresso.testcases.api.scenario.Scenario
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import com.tatiana.rodionova.tutu_assigment.screen.ListScreen

object Scenarios {
    class LoadListScenario : Scenario() {
        override val steps: TestContext<Unit>.() -> Unit
            get() = {
                step("Load list") {
                    ListScreen.list {
                        isVisible()
                    }
                }
            }
    }

    class ClickOnFlutterRepositoryScenario : Scenario() {
        override val steps: TestContext<Unit>.() -> Unit
            get() = {
                step("Click on flutter repo") {
                    ListScreen.list {
                        isVisible()
                        childAt<ListScreen.GithubItem>(0) {
                            name.click()
                        }
                    }
                }
            }
    }
}